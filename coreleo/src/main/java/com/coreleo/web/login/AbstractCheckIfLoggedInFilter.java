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

/**
 * @author Leon Samaroo
 * 
 */
public abstract class AbstractCheckIfLoggedInFilter extends AbstractFilter
{

	@Override
	protected void onDoFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException
	{
		final HttpServletRequest request = (HttpServletRequest) req;
		final HttpServletResponse response = (HttpServletResponse) res;

		// exempt check
		if (isRequestedPageExempt(request))
		{
			chain.doFilter(request, response);
			return;
		}

		if (request == null)
		{
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			return;
		}

		// check session
		if (request.getSession(false) == null)
		{
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			return;
		}

		if (!LoginHelper.isUserLoggedIn(request))
		{
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			return;
		}

		chain.doFilter(request, response);
	}

	protected abstract boolean isRequestedPageExempt(HttpServletRequest request);

}
