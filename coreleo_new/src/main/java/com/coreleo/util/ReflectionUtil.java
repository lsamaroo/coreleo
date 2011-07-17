/**
 * 
 */
package com.coreleo.util;

import java.lang.reflect.*;
import java.util.*;

import com.coreleo.SimpleException;

/**
 * 
 * @author Leon Samaroo
 * 
 * <br/> Contains methods which make invoking methods (and other reflection
 * functions) a breeze.
 * 
 */
public final class ReflectionUtil {
    private final static Map methodCache = new HashMap();
    private final static Map constructorCache = new HashMap();  
    
    public static final boolean isInstance( String className, Object obj ){
    	try{
    		Class clazz = forName(className);
    		return clazz.isInstance(obj);
    	}
    	catch( Exception e ){
    		return false;
    	}
    }
    
    
    public static final Class forName(String className) throws SimpleException {
		try {
			return Class.forName(className);
		} 
		catch (Exception e) {
			throw new SimpleException(e);
		}
	}
    
    
    public static final Object staticInvoke(String className, String methodName, Object paramValue) throws SimpleException {
    	return staticInvoke(className, methodName, new Object[]{paramValue});
    }
    

    public static final Object staticInvoke(String className, String methodName, Object[] params) throws SimpleException {
    	try {
    		Class clazz = forName(className);
    		Method method = findMethod(clazz, methodName, params, true );
			return method.invoke(null, params );
		} 
    	catch (Exception e) {
    		throw new SimpleException(e);
		}
    }
    
    
    
    /**
     * 
     * @param className -
     *            the fully qualified name of the class.
     * @return a new Instance of the class specified by the string or null if it
     *         is unable to create a new instance of the class.
     * @throws IllegalArgumentException
     *             if the parameter is null or empty.
     */
    public static final Object newInstance(String className) throws IllegalArgumentException {
        if (StringUtil.isEmpty(className)) {
            throw new IllegalArgumentException("the class name is empty or null");
        }

        try {
            Class clazz = forName(className);
            return clazz.newInstance();
        }
        catch (Exception e) {
            LogUtil.error( e );
            return null;
        }
    }


    public static final Object newInstance(String className, Object constructorParams) throws IllegalArgumentException {
        if (StringUtil.isEmpty(className)) {
            throw new IllegalArgumentException("the class name is empty or null");
        }

        try {
            Class clazz = forName(className);
            return newInstance(clazz, constructorParams);
        }
        catch (Exception e) {
            LogUtil.error( e );
            return null;
        }
    }


    public static final Object newInstance(Class clazz) throws IllegalArgumentException {
        if ( clazz == null ) {
            throw new IllegalArgumentException("the class argument is null");
        }

        try {
            return clazz.newInstance();
        }
        catch (Exception e) {
            LogUtil.error( e );
            return null;
        }
    }
    
    public static final Object newInstance(Class clazz, Object constructorParams) throws IllegalArgumentException {
        Object[] newConstructorParam = null;

        if (constructorParams == null) {
            newConstructorParam = new Object[0];
        }
        else if (constructorParams instanceof Object[]) {
            newConstructorParam = (Object[]) constructorParams;
        }
        else {
            newConstructorParam = new Object[] { constructorParams };
        }

        return newInstance(clazz, newConstructorParam);
    }


    public static final Object newInstance(Class clazz, Object[] constructorParams ) throws IllegalArgumentException {
        try {
            Constructor constructor = findConstructor(clazz, constructorParams, true);
            return constructor.newInstance(constructorParams);
        }
        catch (Exception e) {
            LogUtil.error( e );
            return null;
        }
    }
    
    
    
    public static final boolean setFieldValue( Object obj, String fieldName, Object value ) throws SimpleException {
    	if (StringUtil.isEmpty(fieldName)) {
            throw new IllegalArgumentException("Field name is empty or null");
        }
    	
    	if( obj == null ){
    		throw new IllegalArgumentException("Object is null");
    	}
    	
    	try{
    		Field field = obj.getClass().getField(fieldName);
    		field.set(obj, value);
    	}
    	catch (Exception e) {
			throw new SimpleException(e);
		}
    	
    	return true;
    }
    
    
    public static final Object getFieldValue( Object obj, String fieldName ) throws SimpleException {
    	if (StringUtil.isEmpty(fieldName)) {
            throw new IllegalArgumentException("Field name is empty or null");
        }
    	
    	if( obj == null ){
    		throw new IllegalArgumentException("Object is null");
    	}
    	
    	try{
    		Field field = obj.getClass().getField(fieldName);
    		return field.get(obj);
    	}
    	catch (Exception e) {
			throw new SimpleException(e);
		}
    	
    }
    

    
    /**
     * Overloaded (underloaded) version of invoke, use this when calling a
     * method which does not take any parameters.
     * 
     * @param object -
     *            the object to invoke the method on. Can be null if and only if
     *            the method is static.
     * @param methodName -
     *            the name of the method to invoke.
     * @return - the Object returned from invoking the method or null if the
     *         method return type is void.
     * @throws -
     *             SimpleException
     */
    public static final Object invoke(Object object, String methodName) throws SimpleException {
        return invoke(object, methodName, new Object[0]);
    }


    /**
     * 
     * @param object -
     *            the object to invoke the method on. Can be null if and only if
     *            the method is static.
     * @param methodName -
     *            the name of the method to invoke.
     * @param params -
     *            the parameter value to pass to the method. May be null if no
     *            arguments are needed.
     * @return - the Object returned from invoking the method or null if the
     *         method return type is void.
     * @throws -
     *             SimpleException
     */
    public static final Object invoke(Object object, String methodName, Object params) throws SimpleException {
        Object[] newParams = null;

        if (params == null) {
            newParams = new Object[0];
        }
        else if (params instanceof Object[]) {
            newParams = (Object[]) params;
        }
        else if ( params instanceof List ){
            newParams = ((List) params).toArray();
        }
        else {
            newParams = new Object[] { params };
        }
        return invoke(object, methodName, newParams);
    }




    /**
     * 
     * @param object -
     *            the object to invoke the method on. Can be null if and only if
     *            the method is static.
     * @param methodName -
     *            the name of the method to invoke.
     * @param params -
     *            the parameter values to pass to the method. May be null or of
     *            length 0 if no arguments are needed.
     * @return - the Object returned from invoking the method or null if the
     *         method return type is void.
     * @throws -
     *             SimpleException
     * 
     */
    public static final Object invoke(Object object, String methodName, Object[] params) throws SimpleException {
      try{
    	if (StringUtil.isEmpty(methodName)) {
            throw new IllegalArgumentException("Method name is empty or null");
        }
    	
    	if( object == null ){
    		throw new IllegalArgumentException("Object is null");
    	}

        Method method = findMethod(object.getClass(), methodName, params, true );
        if (method == null) {
            throw new SimpleException("The method for the given method name does not exist in the class given.");
        }

        return invoke(object, method, params);
      }
      catch( Exception e ){
    	  throw new SimpleException( e );
      }
    }




    
    public static void setAccessible( Method m, boolean flag ){
        if( m != null ){
            m.setAccessible(flag);
        }
    }
    
    
    public static void setAccessible( Constructor c, boolean flag ){
        if( c != null ){
            c.setAccessible(flag);
        }
    }
    
    

    
    

    // -------------------------------------------------------------------------------------------
    // Private helper methods
    // -------------------------------------------------------------------------------------------
    
    

    private static final Constructor findConstructor(Class clazz, Object[] params, boolean tryMatchSuperClassInterfacesOfConstructorParams) {
        Class[] parameterTypes = getParameterTypes(params);
        String key = getUniqueKeyForConstructor(clazz, parameterTypes);

        Constructor c = (Constructor) constructorCache.get(key);
        if( c != null ) {
            return c;
        }
        
        c = searchDeclaredAndPublicConstructors(clazz, parameterTypes );

        if( c == null && tryMatchSuperClassInterfacesOfConstructorParams ){
        	c = searchConstructorsMatchParametersSuperClassOrInterfaces(clazz, parameterTypes );
        }

        constructorCache.put(key, c);
        
        if( c == null ){
        	throw new SimpleException( "Constructor with params= " + ArrayUtil.toCommaDelimitedString(params) + " not found in class " + clazz + "" );
        }
        else{
        	LogUtil.debug("Constructor found=" + c );
        	return c;
        }
    }
    
    
    private static final Constructor searchDeclaredAndPublicConstructors(Class clazz, Class[] parameterTypes) {
    	Constructor c = null;

        try {
            c = clazz.getConstructor( parameterTypes);
        }
        catch (Exception e) {
        } // ignore

        try {
            if (c == null) {
                c = clazz.getDeclaredConstructor(parameterTypes);
                setAccessible(c, true);
            }
        }
        catch (Exception e) {
        } // ignore
        
        if( c == null ) {
            LogUtil.debug( "Failed to find match for " + clazz + " contructor with parameterTypes=" + ArrayUtil.toCommaDelimitedString(parameterTypes) );
        }
        else {
            LogUtil.debug( "Found match for " + clazz + " contructor with parameterTypes=" + ArrayUtil.toCommaDelimitedString(parameterTypes) );
        }

        return c;
    }
    
    
    private static final Constructor searchConstructorsMatchParametersSuperClassOrInterfaces( Class clazz, Class[] parameterTypes){
    	Constructor[] contructors = clazz.getConstructors();
    	for( int i = 0; i < contructors.length; i++ ){
    		if( isMatch( contructors[i], parameterTypes ) ){
    			return contructors[i];
    		}
    	}
    	
    	contructors = clazz.getDeclaredConstructors();
    	for( int i = 0; i < contructors.length; i++ ){
    		if( isMatch( contructors[i], parameterTypes ) ){
    			return contructors[i];
    		}
    	}

    	return null;
    }
    
    
    private static boolean isMatch( Constructor c, Class[] parameterTypes){
    	if (c == null) {
			return false;
		}

		Class[] types = c.getParameterTypes();
		if (types.length != parameterTypes.length) {
			return false;
		}

		for (int i = 0; i < types.length; i++) {
			Class type = types[i];
			if (!type.isAssignableFrom(parameterTypes[i])) {
				return false;
			}
		}

		return true;
    }
    

    private static final Object invoke(Object object, Method method, Object[] params) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (method == null) {
            throw new IllegalArgumentException("Method is null");
        }

        return method.invoke(object, params);
    }

    
    

    private static final Method findMethod(Class clazz, String methodName, Object[] params, boolean tryMatchSuperClassInterfacesOfParams) {
        Class[] parameterTypes = getParameterTypes(params);
        String key = getUniqueKeyForMethod(clazz, methodName, parameterTypes);

        Method m = (Method) methodCache.get(key);
        if( m != null ) {
            return m;
        }
        
        m = searchDeclaredAndPublicMethods(clazz, methodName, parameterTypes );

        if( m == null && tryMatchSuperClassInterfacesOfParams ){
        	m = searchMethodsMatchParametersSuperClassOrInterfaces(clazz, methodName, parameterTypes );
        }

        methodCache.put(key, m);
        
        if( m == null ){
        	throw new SimpleException( "Method=" + methodName +  " params= " + ArrayUtil.toCommaDelimitedString(params) + " not found in class " + clazz + "" );
        }
        else{
        	LogUtil.debug("Method found=" + m );
        	return m;
        }
    }
    
    private static final Method searchMethodsMatchParametersSuperClassOrInterfaces( Class clazz, String methodName, Class[] parameterTypes){
    	Method[] methods = clazz.getMethods();
    	for( int i = 0; i < methods.length; i++ ){
    		if( isMatch( methods[i], methodName, parameterTypes ) ){
    			return methods[i];
    		}
    	}
    	
    	methods = clazz.getDeclaredMethods();
    	for( int i = 0; i < methods.length; i++ ){
    		if( isMatch( methods[i], methodName, parameterTypes ) ){
    			return methods[i];
    		}
    	}

    	return null;
    }
    
    private static boolean isMatch( Method m, String methodName, Class[] parameterTypes){
    	if( m == null ){
    		return false;
    	}

    	if( StringUtil.equals( m.getName(), methodName ) ){
    		Class[] types = m.getParameterTypes();
    		if( types.length != parameterTypes.length ){
    			return false;
    		}
    		
    		for( int i =0; i < types.length; i++  ){
    			Class type = types[i];
    			if( !type.isAssignableFrom(parameterTypes[i]) ){
    				return false;
    			}
    		}
    		
    		return true;
    	}
    	
    	return false;
    }
    
    private static final Method searchDeclaredAndPublicMethods(Class clazz, String methodName, Class[] parameterTypes) {
        Method m = null;

        try {
            m = clazz.getMethod(methodName, parameterTypes);
        }
        catch (Exception e) {
        } // ignore

        try {
            if (m == null) {
                m = clazz.getDeclaredMethod(methodName, parameterTypes);
                setAccessible(m, true);
            }
        }
        catch (Exception e) {
        } // ignore
        
        if( m == null ) {
            LogUtil.debug( "Failed to find match for " + clazz + " methodName=" + methodName + " parameterTypes=" + ArrayUtil.toCommaDelimitedString(parameterTypes) );
        }
        else {
            LogUtil.debug( "Found match for " + clazz + " methodName=" + methodName + " parameterTypes=" + ArrayUtil.toCommaDelimitedString(parameterTypes) );
        }

        return m;
    }

    
    private static final Class[] getParameterTypes(Object[] params) throws IllegalArgumentException {
        Class[] paramTypes = null;
        if (params != null) {
            paramTypes = new Class[params.length];
            for (int i = 0; i < params.length; i++) {
                if (params[i] == null) {
                    System.err.println("An element of the parameter array is null.  The parameter array cannot contain null elements.");
                    throw new IllegalArgumentException("An element of the parameter array is null.  The parameter array cannot contain null elements.");
                }
                paramTypes[i] = params[i].getClass();
            }
        }

        return paramTypes;
    }
    
    


    private static final String getUniqueKeyForMethod(Class clazz, String methodName, Class[] parameterTypes) {
        StringBuffer sb = new StringBuffer(clazz.getName());
        sb.append(":").append(methodName).append("-");

        if (parameterTypes != null) {
            for (int i = 0; i < parameterTypes.length; i++) {
                sb.append(parameterTypes[i].getName()).append("#");
            }
        }

        return sb.toString();
    }
    
    
    
    private static final String getUniqueKeyForConstructor(Class clazz, Class[] parameterTypes) {
        StringBuffer sb = new StringBuffer(clazz.getName());
        sb.append(":").append("constructor").append("-");

        if (parameterTypes != null) {
            for (int i = 0; i < parameterTypes.length; i++) {
                sb.append(parameterTypes[i].getName()).append("#");
            }
        }

        return sb.toString();
    }
    
}
