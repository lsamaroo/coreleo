/**
 * 
 */
package com.coreleo.web.login;

import javax.servlet.http.*;
import com.coreleo.util.*;

/**
 * @author Leon Samaroo
 * 
 */
public class LoginHelper {
	private static String VALID_SESSION_KEY = "123abcdefg456@#!%^&*lmnopq";

	public static boolean isUserLoggedIn(HttpServletRequest request) {
		return WebUtil.getAttributeFromSession(request, VALID_SESSION_KEY) != null;
	}

	public static void setUserAsLoggedIn(HttpServletRequest request) {
		WebUtil.setAttributeInSession(request, VALID_SESSION_KEY, Boolean.TRUE);
	}
}
