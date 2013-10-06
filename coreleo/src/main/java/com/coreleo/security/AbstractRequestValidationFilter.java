package com.coreleo.security;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.coreleo.servlet.filter.AbstractFilter;

/**
 * 
 * 
 * @author Leon Samaroo
 * 
 * 
 */
public abstract class AbstractRequestValidationFilter extends AbstractFilter
{

	@SuppressWarnings("rawtypes")
	@Override
	protected void onDoFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException
	{

		final String url = ((HttpServletRequest) req).getRequestURL().toString();
		if (!isValidUrl(url))
		{
			((HttpServletResponse) res).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			((HttpServletResponse) res).getOutputStream().write("{}".getBytes());
			return;
		}

		for (final Enumeration e = req.getParameterNames(); e.hasMoreElements();)
		{
			final String key = String.valueOf(e.nextElement());
			final String value = req.getParameter(key);
			if (!isValidParam(key, value))
			{
				((HttpServletResponse) res).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				((HttpServletResponse) res).getOutputStream().write("{}".getBytes());
				return;
			}
		}

		chain.doFilter(req, res);
	}

	protected abstract boolean isValidParam(String key, String value);

	protected abstract boolean isValidUrl(String url);


}
