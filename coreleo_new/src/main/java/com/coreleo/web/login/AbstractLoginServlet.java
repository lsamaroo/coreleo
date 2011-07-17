/**
 * 
 */
package com.coreleo.web.login;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.coreleo.util.*;

/**
 * @author Leon Samaroo
 * 
 */
public abstract class AbstractLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected static final int FAILED_AUTHENTICATION = 0;
	protected static final int INVALID_USER = 1;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doBeforeLogin( request, response );
		
		if( !userExists(request, response) ){
			WebUtil.redirect(request, response, getFailedView(INVALID_USER));
			WebUtil.invalidateSession(request.getSession(false));
			return;
		}
		
		if (!isValidLogin( request, response )) {
			WebUtil.redirect(request, response, getFailedView(FAILED_AUTHENTICATION));
			WebUtil.invalidateSession(request.getSession(false));
			return;

		} 

		// create a session
		request.getSession(true);	
		LoginHelper.setUserAsLoggedIn(request);
		doAfterLogin( request, response );
		WebUtil.redirect(request, response, getSuccessView());
	}
	
	
	protected abstract boolean userExists(HttpServletRequest request, HttpServletResponse response);
	
	protected abstract boolean isValidLogin(HttpServletRequest request, HttpServletResponse response);
	
	protected abstract void doBeforeLogin(HttpServletRequest request, HttpServletResponse response);

	protected abstract void doAfterLogin(HttpServletRequest request, HttpServletResponse response);

	protected abstract String getSuccessView();

	protected abstract String getFailedView( int reason );
}
