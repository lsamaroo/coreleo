package com.coreleo.servlet.filter;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.coreleo.util.BooleanUtil;
import com.coreleo.util.LogUtil;
import com.coreleo.util.WebUtil;

/**
 * 
 * 
 * @author Leon Samaroo
 * 
 * 
 */
public abstract class AbstractRequestValidationFilter extends AbstractFilter
{
	private boolean isOn = false;

	@Override
	public void init(FilterConfig fc) throws ServletException
	{
		super.init(fc);
		if (fc != null)
		{
			isOn = BooleanUtil.toBoolean(fc.getInitParameter("isOn"), false);
		}
		LogUtil.info(this, "WhiteListFilter: isOn=" + isOn);
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException
	{

		if (isOn)
		{
			final String url = ((HttpServletRequest) res).getRequestURL().toString();
			for (final Enumeration e = req.getParameterNames(); e.hasMoreElements();)
			{
				final String key = String.valueOf(e.nextElement());
				final String value = req.getParameter(key);
				if (!isValid(key, value, url))
				{
					WebUtil.forward((HttpServletRequest) req, (HttpServletResponse) res, getErrorPage());
					return;
				}
			}
		}

		chain.doFilter(req, res);
	}

	protected abstract boolean isValid(String key, String value, String url);

	protected abstract String getErrorPage();

}
