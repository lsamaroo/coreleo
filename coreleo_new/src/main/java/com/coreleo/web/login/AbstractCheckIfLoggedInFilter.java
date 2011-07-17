/**
 * 
 */
package com.coreleo.web.login;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import com.coreleo.servlet.filter.AbstractFilter;
import com.coreleo.util.WebUtil;

/**
 * @author Leon Samaroo
 * 
 */
public abstract class AbstractCheckIfLoggedInFilter extends AbstractFilter {
	protected int INVALID_REQUEST  = -1;
	protected int INVALID_SESSION  = -2;
	protected int NOT_LOGGED_IN  = -3;


	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		final HttpServletRequest request = (HttpServletRequest) req;
		final HttpServletResponse response = (HttpServletResponse) res;

		// exempt check
		if (isRequestedPageExemptFromCheck(request)) {
			chain.doFilter(request, response);
			return;
		}
		
		
		if (request == null ) {
			WebUtil.redirect(request, response, getErrorPage( INVALID_REQUEST ));
			return;			
		}

		// check session
		if ( request.getSession(false) == null ) {
			WebUtil.redirect(request, response, getErrorPage( INVALID_SESSION ));
			return;			
		}

		if (!LoginHelper.isUserLoggedIn(request)) {
			WebUtil.redirect(request, response, getErrorPage( NOT_LOGGED_IN ));
			return;
		} 
		
		chain.doFilter(request, response);
		return;
	}

	protected abstract boolean isRequestedPageExemptFromCheck(HttpServletRequest request);

	protected abstract String getErrorPage( int errorCode );

}
