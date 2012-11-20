/**
 * 
 */
package com.coreleo.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.coreleo.SimpleException;

/**
 * 
 * @author Leon Samaroo
 * 
 * <br/>
 *         Contains methods which make invoking methods (and other reflection functions) a breeze.
 * 
 */
public final class ReflectionUtil
{
	private final static Map<String, Method> methodCache = new ConcurrentHashMap<String, Method>();
	private final static Map<String, Constructor<?>> constructorCache = new ConcurrentHashMap<String, Constructor<?>>();

	/**
	 * Returns a list of method names for the given object.
	 * 
	 * @see getClass().getMethods
	 * @param obj
	 *            - the object to retrieve a method list
	 * @return - an array of names
	 */
	public static final String[] getMethodNames(Object obj)
	{
		if (obj == null)
		{
			return new String[0];
		}

		final Method[] methods = obj.getClass().getMethods();
		final String[] methodNames = new String[methods.length];
		for (int i = 0; i < methods.length; i++)
		{
			methodNames[i] = methods[i].getName();
		}

		return methodNames;
	}

	/**
	 * 
	 * Verifies if the given object is an instance of the provided className
	 * 
	 */
	public static final boolean isInstance(String className, Object obj)
	{
		try
		{
			final Class<?> clazz = forName(className);
			return clazz.isInstance(obj);
		}
		catch (final Exception e)
		{
			return false;
		}
	}

	public static final Class<?> forName(String className) throws SimpleException
	{
		try
		{
			return Class.forName(className);
		}
		catch (final Exception e)
		{
			throw new SimpleException(e);
		}
	}

	/**
	 * Calls the static method on the provided class.
	 */
	public static final Object invokeStatic(String className, String methodName, Object... params) throws SimpleException
	{
		try
		{
			final Class<?> clazz = forName(className);
			return invokeStatic(clazz, methodName, params);

		}
		catch (final Exception e)
		{
			throw new SimpleException(e);
		}
	}

	/**
	 * Calls the static method on the provided class.
	 */
	public static final Object invokeStatic(Class<?> clazz, String methodName, Object... params) throws SimpleException
	{
		try
		{
			final Method method = findMethod(true, clazz, methodName, params);
			return method.invoke(null, params);
		}
		catch (final Exception e)
		{
			throw new SimpleException(e);
		}
	}

	/**
	 * 
	 * Creates a new instance of the given className.
	 * 
	 * @param className
	 *            - the fully qualified name of the class.
	 * @return a new Instance of the class specified by the string or null if it is unable to create a new instance of the class.
	 * @throws IllegalArgumentException
	 */
	public static final Object newInstance(String className, Object... constructorParams) throws IllegalArgumentException
	{
		if (StringUtil.isEmpty(className))
		{
			throw new IllegalArgumentException("the class name is empty or null");
		}

		try
		{
			final Class<?> clazz = forName(className);
			return newInstance(clazz, constructorParams);
		}
		catch (final Exception e)
		{
			LogUtil.error(e);
			return null;
		}
	}

	public static final Object newInstance(Class<?> clazz, Object... constructorParams) throws IllegalArgumentException
	{
		try
		{
			final Constructor<?> constructor = findConstructor(true, clazz, constructorParams);
			return constructor.newInstance(constructorParams);
		}
		catch (final Exception e)
		{
			LogUtil.error(e);
			return null;
		}
	}

	public static final boolean setFieldValue(Object obj, String fieldName, Object value) throws SimpleException
	{
		if (StringUtil.isEmpty(fieldName))
		{
			throw new IllegalArgumentException("Field name is empty or null");
		}

		if (obj == null)
		{
			throw new IllegalArgumentException("Object is null");
		}

		try
		{
			final Field field = obj.getClass().getField(fieldName);
			field.set(obj, value);
		}
		catch (final Exception e)
		{
			throw new SimpleException(e);
		}

		return true;
	}

	public static final Object getFieldValue(Object obj, String fieldName) throws SimpleException
	{
		if (StringUtil.isEmpty(fieldName))
		{
			throw new IllegalArgumentException("Field name is empty or null");
		}

		if (obj == null)
		{
			throw new IllegalArgumentException("Object is null");
		}

		try
		{
			final Field field = obj.getClass().getField(fieldName);
			return field.get(obj);
		}
		catch (final Exception e)
		{
			throw new SimpleException(e);
		}

	}

	/**
	 * 
	 * @param object
	 *            - the object to invoke the method on. Can be null if and only if the method is static.
	 * @param methodName
	 *            - the name of the method to invoke.
	 * @param params
	 *            - list of parameter values to pass to the method.
	 * @return - the Object returned from invoking the method or null if the method return type is void.
	 * @throws - SimpleException
	 */
	public static final Object invoke(Object object, String methodName, List<?> params) throws SimpleException
	{
		return invoke(object, methodName, params.toArray());
	}

	/**
	 * 
	 * @param object
	 *            - the object to invoke the method on.
	 * @param methodName
	 *            - the name of the method to invoke.
	 * @param params
	 *            - the parameter values to pass to the method. This is an optional parameter.
	 * @return - the Object returned from invoking the method or null if the method return type is void.
	 * @throws - SimpleException
	 * 
	 */
	public static final Object invoke(Object object, String methodName, Object... params) throws SimpleException
	{
		try
		{
			if (StringUtil.isEmpty(methodName))
			{
				throw new IllegalArgumentException("Method name is empty or null");
			}

			if (object == null)
			{
				throw new IllegalArgumentException("Object is null");
			}

			final Method method = findMethod(true, object.getClass(), methodName, params);
			if (method == null)
			{
				throw new SimpleException("The method for the given method name does not exist in the class given.");
			}

			return invoke(object, method, params);
		}
		catch (final Exception e)
		{
			throw new SimpleException(e);
		}
	}

	public static void setAccessible(Method m, boolean flag)
	{
		if (m != null)
		{
			m.setAccessible(flag);
		}
	}

	public static void setAccessible(Constructor<?> c, boolean flag)
	{
		if (c != null)
		{
			c.setAccessible(flag);
		}
	}

	// -------------------------------------------------------------------------------------------
	// Private helper methods
	// -------------------------------------------------------------------------------------------

	private static final Constructor<?> findConstructor(boolean matchSuperClassOrInterfaceOfParams, Class<?> clazz, Object... params)
	{
		final Class<?>[] parameterTypes = getParameterTypes(params);
		final String key = getUniqueKeyForConstructor(clazz, parameterTypes);

		Constructor<?> c = constructorCache.get(key);
		if (c != null)
		{
			return c;
		}

		c = findDeclaredAndPublicConstructors(clazz, parameterTypes);

		if (c == null && matchSuperClassOrInterfaceOfParams)
		{
			c = findConstructorsMatchOnParamsSuperClassOrInterface(clazz, parameterTypes);
		}

		constructorCache.put(key, c);

		if (c == null)
		{
			throw new SimpleException("Constructor with params= " + ArrayUtil.toCommaDelimitedString(params) + " not found in class " + clazz + "");
		}
		else
		{
			LogUtil.debug("Constructor found=" + c);
			return c;
		}
	}

	private static final Constructor<?> findDeclaredAndPublicConstructors(Class<?> clazz, Class<?>... parameterTypes)
	{
		Constructor<?> c = null;

		try
		{
			c = clazz.getConstructor(parameterTypes);
		}
		catch (final Exception e)
		{
		} // ignore

		try
		{
			if (c == null)
			{
				c = clazz.getDeclaredConstructor(parameterTypes);
				setAccessible(c, true);
			}
		}
		catch (final Exception e)
		{
		} // ignore

		if (c == null)
		{
			LogUtil.debug("Failed to find match for " + clazz + " contructor with parameterTypes="
					+ ArrayUtil.toCommaDelimitedString((Object[]) parameterTypes));
		}
		else
		{
			LogUtil.debug("Found match for " + clazz + " contructor with parameterTypes=" + ArrayUtil.toCommaDelimitedString((Object[]) parameterTypes));
		}

		return c;
	}

	private static final Constructor<?> findConstructorsMatchOnParamsSuperClassOrInterface(Class<?> clazz, Class<?>... parameterTypes)
	{
		Constructor<?>[] contructors = clazz.getConstructors();
		for (final Constructor<?> contructor : contructors)
		{
			if (isMatch(contructor, parameterTypes))
			{
				return contructor;
			}
		}

		contructors = clazz.getDeclaredConstructors();
		for (final Constructor<?> contructor : contructors)
		{
			if (isMatch(contructor, parameterTypes))
			{
				return contructor;
			}
		}

		return null;
	}

	private static final Object invoke(Object object, Method method, Object... params) throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException
	{
		if (method == null)
		{
			throw new IllegalArgumentException("Method is null");
		}

		return method.invoke(object, params != null ? params : new Object[0]);
	}

	private static final Method findMethod(boolean matchSuperClassOrInterfaceOfParams, Class<?> clazz, String methodName, Object... params)
	{
		final Class<?>[] parameterTypes = getParameterTypes(params);
		final String key = getUniqueKeyForMethod(clazz, methodName, parameterTypes);

		Method m = methodCache.get(key);
		if (m != null)
		{
			return m;
		}

		m = findDeclaredAndPublicMethods(clazz, methodName, parameterTypes);

		if (m == null && matchSuperClassOrInterfaceOfParams)
		{
			m = findMethodsMatchParametersSuperClassOrInterfaces(clazz, methodName, parameterTypes);
		}

		if (m == null)
		{
			throw new SimpleException("Method=" + methodName + " params= " + ArrayUtil.toCommaDelimitedString(params) + " not found in class " + clazz + "");
		}
		else
		{
			LogUtil.debug("Method " + m + " found for " + clazz + " methodName=" + methodName + " parameterTypes="
					+ ArrayUtil.toCommaDelimitedString((Object[]) parameterTypes));

			methodCache.put(key, m);
			return m;
		}
	}

	private static final Method findMethodsMatchParametersSuperClassOrInterfaces(Class<?> clazz, String methodName, Class<?>... parameterTypes)
	{
		Method[] methods = clazz.getMethods();
		for (final Method method : methods)
		{
			if (isMatch(method, methodName, parameterTypes))
			{
				return method;
			}
		}

		methods = clazz.getDeclaredMethods();
		for (final Method method : methods)
		{
			if (isMatch(method, methodName, parameterTypes))
			{
				return method;
			}
		}

		return null;
	}

	private static final Method findDeclaredAndPublicMethods(Class<?> clazz, String methodName, Class<?>... parameterTypes)
	{
		Method m = null;

		try
		{
			m = clazz.getMethod(methodName, parameterTypes);
		}
		catch (final Exception e)
		{
		} // ignore

		try
		{
			if (m == null)
			{
				m = clazz.getDeclaredMethod(methodName, parameterTypes);
				setAccessible(m, true);
			}
		}
		catch (final Exception e)
		{
		} // ignore

		return m;
	}

	private static final Class<?>[] getParameterTypes(Object... params) throws IllegalArgumentException
	{
		Class<?>[] paramTypes = null;
		if (params != null)
		{
			paramTypes = new Class<?>[params.length];
			for (int i = 0; i < params.length; i++)
			{
				if (params[i] == null)
				{
					System.err.println("An element of the parameter array is null.  The parameter array cannot contain null elements.");
					throw new IllegalArgumentException("An element of the parameter array is null.  The parameter array cannot contain null elements.");
				}
				paramTypes[i] = params[i].getClass();
			}
		}
		else
		{
			paramTypes = new Class<?>[0];
		}

		return paramTypes;
	}

	private static boolean isMatch(Method m, String methodName, Class<?>... parameterTypes)
	{
		if (m == null)
		{
			return false;
		}

		if (StringUtil.equals(m.getName(), methodName))
		{
			final Class<?>[] types = m.getParameterTypes();
			if (types.length != parameterTypes.length)
			{
				return false;
			}

			for (int i = 0; i < types.length; i++)
			{
				final Class<?> type = types[i];
				if (!type.isAssignableFrom(parameterTypes[i]))
				{
					if (!isAutoBoxingMismatch(type, parameterTypes[i]))
					{
						return false;
					}
				}
			}

			return true;
		}

		return false;
	}

	private static boolean isMatch(Constructor<?> c, Class<?>... parameterTypes)
	{
		if (c == null)
		{
			return false;
		}

		final Class<?>[] types = c.getParameterTypes();
		if (types.length != parameterTypes.length)
		{
			return false;
		}

		for (int i = 0; i < types.length; i++)
		{
			final Class<?> type = types[i];
			if (!type.isAssignableFrom(parameterTypes[i]))
			{
				if (!isAutoBoxingMismatch(type, parameterTypes[i]))
				{
					return false;
				}
			}
		}

		return true;
	}

	private static final boolean isAutoBoxingMismatch(Class<?> methodParamType, Class<?> paramType)
	{
		if (!methodParamType.isPrimitive())
		{
			return false;
		}

		if (methodParamType == int.class && paramType == Integer.class)
		{
			return true;
		}
		else if (methodParamType == boolean.class && paramType == Boolean.class)
		{
			return true;
		}
		else if (methodParamType == char.class && paramType == Character.class)
		{
			return true;
		}
		else if (methodParamType == double.class && paramType == Double.class)
		{
			return true;
		}
		else if (methodParamType == long.class && paramType == Long.class)
		{
			return true;
		}
		else if (methodParamType == float.class && paramType == Float.class)
		{
			return true;
		}
		else if (methodParamType == short.class && paramType == Short.class)
		{
			return true;
		}
		else if (methodParamType == byte.class && paramType == Byte.class)
		{
			return true;
		}

		return false;
	}

	private static final String getUniqueKeyForMethod(Class<?> clazz, String methodName, Class<?>[] parameterTypes)
	{
		final StringBuffer sb = new StringBuffer(clazz.getName());
		sb.append(":").append(methodName).append("-");

		if (parameterTypes != null)
		{
			for (final Class<?> parameterType : parameterTypes)
			{
				sb.append(parameterType.getName()).append("#");
			}
		}

		return sb.toString();
	}

	private static final String getUniqueKeyForConstructor(Class<?> clazz, Class<?>[] parameterTypes)
	{
		final StringBuffer sb = new StringBuffer(clazz.getName());
		sb.append(":").append("constructor").append("-");

		if (parameterTypes != null)
		{
			for (final Class<?> parameterType : parameterTypes)
			{
				sb.append(parameterType.getName()).append("#");
			}
		}

		return sb.toString();
	}

}
