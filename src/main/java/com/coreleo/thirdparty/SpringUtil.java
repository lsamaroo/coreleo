package com.coreleo.thirdparty;

import javax.servlet.ServletContext;

import com.coreleo.util.ReflectionUtil;

/**
 *
 * @author Leon Samaroo Uses reflection to avoid compile dependencies on the
 *         spring library. However if this utility is used, the spring jar is
 *         required.
 */
@Deprecated
public class SpringUtil {
	private final static String WEB_APPLICATION_CONTEXT_UTILS = "org.springframework.web.context.support.WebApplicationContextUtils";
	private final static String GET_REQUIRED_WEB_APPLICATION_CONTEXT = "getRequiredWebApplicationContext";
	private final static String GET_BEAN = "getBean";
	private final static String APPLICATION_CONTEXT = "org.springframework.context.ApplicationContext";

	public static final Object getApplicationContext(final ServletContext sc) {
		return ReflectionUtil.invokeStatic(WEB_APPLICATION_CONTEXT_UTILS, GET_REQUIRED_WEB_APPLICATION_CONTEXT, sc);
	}

	public static final Object getBean(final ServletContext sc, final String beanId) {
		final Object wac = ReflectionUtil.invokeStatic(WEB_APPLICATION_CONTEXT_UTILS,
				GET_REQUIRED_WEB_APPLICATION_CONTEXT, sc);
		return ReflectionUtil.invoke(wac, GET_BEAN, beanId);
	}

	public static final Object getBean(final Object context, final String beanId) {
		if (ReflectionUtil.isInstance(APPLICATION_CONTEXT, context))
		{
			return ReflectionUtil.invoke(context, GET_BEAN, beanId);
		}
		return null;
	}

}
