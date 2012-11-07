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
public abstract class AbstractWhiteListFilter extends AbstractFilter
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
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException
	{
		if (isOn)
		{
			for (final Enumeration e = request.getParameterNames(); e.hasMoreElements();)
			{
				final String key = String.valueOf(e.nextElement());
				final String value = request.getParameter(key);
				if (!isValidParameterName(key) || !isValidParameterValue(value))
				{
					WebUtil.forward((HttpServletRequest) request, (HttpServletResponse) response, getRedirectOnFailUrl());
					return;
				}
			}
		}

		chain.doFilter(request, response);
	}

	protected abstract boolean isValidParameterValue(String str);

	protected abstract boolean isValidParameterName(String str);

	protected abstract String getRedirectOnFailUrl();

}
