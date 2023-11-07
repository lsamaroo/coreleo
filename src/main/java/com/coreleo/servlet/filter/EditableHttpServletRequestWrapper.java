package com.coreleo.servlet.filter;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import com.coreleo.util.ArrayUtil;
import com.coreleo.util.StringUtil;

/**
 * A wrapper around HttpServletRequestWrapper which allows you to add and
 * replace parameters. An example usage would be to use this in your first
 * javax.servlet.Filter to dynamically modify request parameters and then pass
 * this request wrapper along to the chain of filters (chain.doFilter)
 *
 * @author lsamaroo
 *
 */
public class EditableHttpServletRequestWrapper extends HttpServletRequestWrapper {
	private final HashMap<String, String[]> customParams = new HashMap<>();

	public EditableHttpServletRequestWrapper(final HttpServletRequest request) {
		super(request);
	}

	@Override
	public Map<String, String[]> getParameterMap() {
		final Map<String, String[]> map = new HashMap<>();
		map.putAll(super.getParameterMap());
		map.putAll(customParams);
		return Collections.unmodifiableMap(map);
	}

	@Override
	public String getParameter(final String name) {
		// if we added one, return that one
		if (customParams.get(name) != null) {
			return customParams.get(name)[0];
		}
		return super.getParameter(name);
	}

	@Override
	public String[] getParameterValues(final String name) {
		// if we added one, return that one
		if (customParams.get(name) != null) {
			return customParams.get(name);
		}
		return super.getParameterValues(name);
	}

	public void setParameter(final String name, final String... values) {
		if (StringUtil.isEmpty(name) || ArrayUtil.isEmpty(values)) {
			return;
		}
		customParams.put(name, values);
	}
}
