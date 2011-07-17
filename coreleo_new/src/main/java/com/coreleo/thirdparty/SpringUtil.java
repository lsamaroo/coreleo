package com.coreleo.thirdparty;

import javax.servlet.ServletContext;
import com.coreleo.util.ReflectionUtil;

/**
 * 
 * @author Leon Samaroo
 * Uses reflection to avoid compile dependencies on the spring library.  However if this utility is used, the spring jar is required.
 */
public class SpringUtil {
	private final static String WEB_APPLICATION_CONTEXT_UTILS = "org.springframework.web.context.support.WebApplicationContextUtils";
	private final static String GET_REQUIRED_WEB_APPLICATION_CONTEXT = "getRequiredWebApplicationContext";
	private final static String GET_BEAN = "getBean";
	private final static String APPLICATION_CONTEXT = "org.springframework.context.ApplicationContext";
	

	public static final Object getApplicationContext(ServletContext sc){
		return ReflectionUtil.staticInvoke( WEB_APPLICATION_CONTEXT_UTILS, GET_REQUIRED_WEB_APPLICATION_CONTEXT, sc);
	}
	
	
	public static final Object getBean(ServletContext sc, String beanId ){
		Object wac  = ReflectionUtil.staticInvoke( WEB_APPLICATION_CONTEXT_UTILS, GET_REQUIRED_WEB_APPLICATION_CONTEXT, sc);
		return ReflectionUtil.invoke( wac, GET_BEAN, beanId );
	}
	
	
	public static final Object getBean( Object context, String beanId ){
		if( ReflectionUtil.isInstance(APPLICATION_CONTEXT, context)){
			return ReflectionUtil.invoke( context, GET_BEAN, beanId );
		}
		return null;
	}
	

}
