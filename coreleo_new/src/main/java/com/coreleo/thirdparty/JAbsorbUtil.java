/**
 * Utility for handling javascript object notation.  It makes use of org.json library.
 * 
 * 
 */
package com.coreleo.thirdparty;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.coreleo.util.LogUtil;
import com.coreleo.util.ReflectionUtil;

/**
 * @author Leon Samaroo
 * 
 *         This utility is for use with the JAbsorb library. It requires the jabsorb jar.
 * 
 */
public class JAbsorbUtil
{
	private final static String JSONRPCBRIDGE_KEY = "JSONRPCBridge";
	private final static String JSONRPCBRIDGE = "org.jabsorb.JSONRPCBridge";
	private final static String LOOKUP_OBJECT = "lookupObject";
	private final static String REGISTER_OBJECT = "registerObject";
	private final static String LOOKUP_CLASS = "lookupClass";
	private final static String REGISTER_CLASS = "registerClass";

	/**
	 * To export all instance methods of an object to all clients
	 */
	public static final boolean registerObject(HttpServletRequest request, String name, String fullyQualifiedClassName)
	{
		if (request == null)
		{
			return false;
		}

		return registerObject(request.getSession(false), name, fullyQualifiedClassName);
	}

	public static final boolean registerObject(HttpSession session, String name, String fullyQualifiedClassName)
	{
		if (session == null)
		{
			return false;
		}

		Object bridge = session.getAttribute(JSONRPCBRIDGE_KEY);
		if (bridge == null)
		{
			bridge = ReflectionUtil.newInstance(JSONRPCBRIDGE);
			session.setAttribute(JSONRPCBRIDGE_KEY, bridge);
		}

		Object obj = ReflectionUtil.invoke(bridge, LOOKUP_OBJECT, name);
		if (obj == null)
		{
			obj = ReflectionUtil.newInstance(fullyQualifiedClassName);
			ReflectionUtil.invoke(bridge, REGISTER_OBJECT, name, obj);
			LogUtil.debug("JAbsorbUtil:registerObject name=" + name);
		}

		return true;
	}

	/**
	 * @deprecated - use registerStaticClass instead
	 * 
	 */
	@Deprecated
	public static final boolean registerClass(HttpServletRequest request, String name, String fullyQualifiedClassName)
	{
		return registerStaticClass(request, name, fullyQualifiedClassName);
	}

	/**
	 * @deprecated - use registerStaticClass instead
	 * 
	 */
	@Deprecated
	public static final boolean registerClass(HttpSession session, String name, String fullyQualifiedClassName)
	{
		return registerStaticClass(session, name, fullyQualifiedClassName);
	}

	/**
	 * 
	 * Export all static methods of a class to all clients
	 */
	public static final boolean registerStaticClass(HttpServletRequest request, String name, String fullyQualifiedClassName)
	{
		if (request == null)
		{
			return false;
		}

		return registerStaticClass(request.getSession(false), name, fullyQualifiedClassName);
	}

	/**
	 * 
	 * Export all static methods of a class to all clients
	 */
	public static final boolean registerStaticClass(HttpSession session, String name, String fullyQualifiedClassName)
	{
		if (session == null)
		{
			return false;
		}

		Object bridge = session.getAttribute(JSONRPCBRIDGE_KEY);
		if (bridge == null)
		{
			bridge = ReflectionUtil.newInstance(JSONRPCBRIDGE);
			session.setAttribute(JSONRPCBRIDGE_KEY, bridge);
		}

		Class clazz = (Class) ReflectionUtil.invoke(bridge, LOOKUP_CLASS, name);
		if (clazz == null)
		{
			clazz = ReflectionUtil.forName(fullyQualifiedClassName);
			ReflectionUtil.invoke(bridge, REGISTER_CLASS, name, clazz);
			LogUtil.debug("JAbsorbUtil:registerClass name=" + name);
		}

		return true;
	}

}
