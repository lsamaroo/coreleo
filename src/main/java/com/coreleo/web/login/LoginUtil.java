/**
 * 
 */
package com.coreleo.web.login;

import javax.servlet.http.HttpServletRequest;

import com.coreleo.util.WebUtil;

/**
 * @author Leon Samaroo
 * 
 */
public class LoginUtil
{
	private static String VALID_SESSION_KEY = "123abcdefg456@#!%^&*lmnopq";

	public static boolean isUserLoggedIn(HttpServletRequest request)
	{
		return WebUtil.getAttributeFromSession(request, VALID_SESSION_KEY) != null;
	}

	public static void setUserAsLoggedIn(HttpServletRequest request)
	{
		WebUtil.setAttributeInSession(request, VALID_SESSION_KEY, Boolean.TRUE, true);
	}
}
