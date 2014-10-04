/**
 * 
 */
package com.coreleo.web.login;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.coreleo.servlet.filter.AbstractFilter;
import com.coreleo.util.LogUtil;

/**
 * @author Leon Samaroo
 * 
 */
public abstract class AbstractCheckIfLoggedInFilter extends AbstractFilter {
	private final static String JSON_ERROR_UNAUTHORIZED = "{\"error\":\"UNAUTHORIZED\"}";

	@Override
	protected void onDoFilter(final ServletRequest req, final ServletResponse res, final FilterChain chain) throws IOException, ServletException {
		final HttpServletRequest request = (HttpServletRequest) req;
		final HttpServletResponse response = (HttpServletResponse) res;

		// exempt check
		if (isRequestedPageExempt(request)) {
			chain.doFilter(request, response);
			return;
		}

		if (request == null) {
			LogUtil.debug(this, "null request");
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			response.getOutputStream().write(JSON_ERROR_UNAUTHORIZED.getBytes());
			return;
		}

		// check session
		if (request.getSession(false) == null) {
			LogUtil.debug(this, "null session");
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			response.getOutputStream().write(JSON_ERROR_UNAUTHORIZED.getBytes());
			return;
		}

		if (!LoginUtil.isUserLoggedIn(request)) {
			LogUtil.debug(this, "User not logged in");
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			response.getOutputStream().write(JSON_ERROR_UNAUTHORIZED.getBytes());
			return;
		}

		chain.doFilter(request, response);
	}

	protected abstract boolean isRequestedPageExempt(HttpServletRequest request);

}
