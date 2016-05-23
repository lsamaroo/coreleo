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
import com.coreleo.util.LogUtil;

/**
 *
 *
 * @author Leon Samaroo
 *
 *
 */
public abstract class AbstractRequestValidationFilter extends AbstractFilter {
	private static final String JSON_ERROR_BAD_REQUEST = "{\"error\":\"Bad Request\"}";
	private String responseString = JSON_ERROR_BAD_REQUEST;

	@SuppressWarnings("rawtypes")
	@Override
	protected void onDoFilter(final ServletRequest req, final ServletResponse res, final FilterChain chain)
	        throws IOException, ServletException {

		final String url = ((HttpServletRequest) req).getRequestURL().toString();
		if (!isValidUrl(url)) {
			LogUtil.info(this, "HTTP Status {}", HttpServletResponse.SC_BAD_REQUEST);
			LogUtil.info(this, "Invalid Url", url);
			((HttpServletResponse) res).setStatus(HttpServletResponse.SC_BAD_REQUEST);
			((HttpServletResponse) res).getOutputStream().write(responseString.getBytes());
			return;
		}

		for (final Enumeration e = req.getParameterNames(); e.hasMoreElements();) {
			final String key = String.valueOf(e.nextElement());
			final String[] values = req.getParameterValues(key);
			if (!isValidParam(key, values)) {
				LogUtil.info(this, "HTTP Status {}", HttpServletResponse.SC_BAD_REQUEST);
				LogUtil.info(this, "Invalid params {} and/or key {}", values, key);
				((HttpServletResponse) res).setStatus(HttpServletResponse.SC_BAD_REQUEST);
				((HttpServletResponse) res).getOutputStream().write(responseString.getBytes());
				return;
			}
		}

		chain.doFilter(req, res);
	}

	protected abstract boolean isValidParam(String key, String... value);

	protected abstract boolean isValidUrl(String url);

	public String getResponseString() {
		return responseString;
	}

	public void setResponseString(final String response) {
		this.responseString = response;
	}

}
