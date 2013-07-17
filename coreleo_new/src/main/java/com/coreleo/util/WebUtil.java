package com.coreleo.util;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@SuppressWarnings({ "rawtypes" })
public final class WebUtil
{

	private WebUtil()
	{
	}

	public static Cookie getCookie(HttpServletRequest request, String name)
	{
		final Cookie[] cookies = request.getCookies();
		for (int i = 0; i < ArrayUtil.size(cookies); i++)
		{
			final Cookie c = cookies[i];
			if (StringUtil.equals(c.getName(), name))
			{
				return c;
			}
		}
		return null;
	}

	/**
	 * maxAge is defaulted to 365 * 24 * 60 * 60
	 */
	public static void setCookie(HttpServletResponse response, String name, String value)
	{
		setCookie(response, name, value, 365 * 24 * 60 * 60);
	}

	public static void setCookie(HttpServletResponse response, String name, String value, int maxAge)
	{
		final Cookie cookie = new Cookie(name, value);
		cookie.setMaxAge(maxAge);
		response.addCookie(cookie);
	}

	public static final void invalidateSession(HttpSession session)
	{
		if (session != null)
		{
			session.invalidate();
		}
	}

	public static final void invalidateSession(HttpServletRequest request)
	{
		if (request != null)
		{
			invalidateSession(request.getSession(false));
		}
	}

	public static final Properties stripPrefixedRequestParamater(String prefix, HttpServletRequest request)
	{
		return stripPrefixedRequestParameter(prefix, request.getParameterMap());
	}

	public static final Properties stripPrefixedRequestParameter(String prefix, Map parameterMap)
	{
		final Properties properties = new Properties();

		for (final Iterator i = parameterMap.keySet().iterator(); i.hasNext();)
		{
			final String key = String.valueOf(i.next());
			if (key.startsWith(prefix))
			{
				final Object value = MapUtil.getFirstValue(parameterMap, key);

				LogUtil.debug("WebUtil:stripPrefixedRequestParameter - key=" + key.substring(prefix.length(), key.length()));
				properties.put(key.substring(prefix.length(), key.length()), value);
			}
		}

		return properties;
	}

	/**
	 * @return the query string or null if there are no parameters or an error is encountered.
	 */
	public static final String toQueryString(ServletRequest request)
	{
		return toQueryString(request.getParameterMap());
	}

	/**
	 * @return the query string or null if there are no parameters or an error is encountered. The return String is formatted as
	 *         key1=value1&key2=value2&key3=value3
	 */
	public static final String toQueryString(Map map)
	{
		final StringBuffer buff = new StringBuffer();

		try
		{
			for (final Iterator i = map.entrySet().iterator(); i.hasNext();)
			{
				final Map.Entry entry = (Map.Entry) i.next();

				final Object key = entry.getKey();
				final Object value = entry.getValue();

				if (value != null)
				{
					if (value instanceof Object[])
					{
						final Object[] valueArray = (Object[]) value;
						if (valueArray != null && valueArray.length > 0)
						{
							for (final Object obj : valueArray)
							{
								if (obj != null)
								{
									buff.append(URLEncoder.encode(String.valueOf(key), Constants.UTF8));
									buff.append(Constants.EQUAL);
									buff.append(URLEncoder.encode(String.valueOf(obj), Constants.UTF8));
								}
							}
						}
						else
						{
							buff.append(URLEncoder.encode(String.valueOf(key), Constants.UTF8));
							buff.append(Constants.EQUAL);
							buff.append(URLEncoder.encode(String.valueOf(value), Constants.UTF8));
						}
					}
				}

				if (i.hasNext())
				{
					buff.append(Constants.AMPERSAND);
				}
			}
		}
		catch (final Exception e)
		{
			LogUtil.error("Error while creating query string", e);
			return null;
		}

		return (buff.length() != 0) ? buff.toString() : null;

	}

	/** replace metacharacters in HTML with their html values. */
	public static final String[] replaceHtmlMetaCharacters(String[] x)
	{
		if (x == null)
		{
			return null;
		}

		for (int i = 0; i < x.length; i++)
		{
			x[i] = replaceHtmlMetaCharacters(x[i]);
		}

		return x;
	}

	public static final String replaceHtmlMetaCharacters(Object x)
	{
		if (x == null)
		{
			return null;
		}

		return replaceHtmlMetaCharacters(x.toString());
	}

	/** replace metacharacters in HTML with their html values. */
	public static final String replaceHtmlMetaCharacters(String x)
	{
		return HtmlUtil.toHtmlEntityString(x);
		//        if (x == null) {
		//            return null;
		//        }
		//        else {
		//            // deal with ampersands first so we can ignore the ones we add later
		//            x = StringUtil.replace(x, "&", "&amp;");
		//            x = StringUtil.replace(x, "\"", "&quot;");
		//            x = StringUtil.replace(x, "<", "&lt;");
		//            x = StringUtil.replace(x, ">", "&gt;");
		//            return x;
		//        }
	}

	/**
	 * Method used for debugging.
	 */
	public static final void displayRequestParameters(HttpServletRequest request)
	{
		LogUtil.trace("WebUtil:displayRequestParamaters - request=" + request);
		final Object[] names = request.getParameterMap().keySet().toArray();
		Arrays.sort(names);
		for (final Object name : names)
		{
			LogUtil.info(name + " " + request.getParameter(name.toString()));
		}
	}

	/**
	 * Method used for debugging.
	 */
	public static final void displayRequestAttributes(HttpServletRequest request)
	{
		LogUtil.trace("WebUtil:displayRequestAttributes - request=" + request);
		final Enumeration names = request.getAttributeNames();
		while (names.hasMoreElements())
		{
			final Object name = names.nextElement();
			final Object value = request.getAttribute(String.valueOf(name));
			LogUtil.info("Request attributes: name=" + name + " value=" + value);
		}
	}

	/**
	 * Method used for debugging.
	 */
	public static final void displayRequestHeaders(HttpServletRequest request)
	{
		LogUtil.trace("Displaying the request object headers.");
		for (final Enumeration i = request.getHeaderNames(); i.hasMoreElements();)
		{
			final String param = (String) i.nextElement();
			LogUtil.info(param + " " + request.getHeader(param));
		}
	}

	public static final String convertRequestParametersToHiddenHtmlInputs(HttpServletRequest request)
	{
		final Map map = request.getParameterMap();
		final StringBuffer buff = new StringBuffer();
		final Set set = map.entrySet();

		for (final Iterator i = set.iterator(); i.hasNext();)
		{
			final Map.Entry entry = (Map.Entry) i.next();
			final Object key = entry.getKey();
			final String[] value = (String[]) entry.getValue();
			if (value != null)
			{
				for (final String element : value)
				{
					buff.append("<input type=\"hidden\" name=\"");
					buff.append(key);
					buff.append("\" value=\"");
					buff.append(element);
					buff.append("\" /> ");
				}
			}
		}

		return buff.toString();
	}

	public static final String toHtmlCheckedUnchecked(boolean checked)
	{
		if (checked)
		{
			return "checked=\"checked\"";
		}

		return "";
	}

	public static final boolean forward(HttpServletRequest request, HttpServletResponse response, String page)
	{
		try
		{
			if (page != null)
			{
				if (request.getRequestDispatcher(page) != null)
				{
					request.getRequestDispatcher(page).forward(request, response);
					return true;
				}
			}
		}
		catch (final ServletException se)
		{
			return false;
		}
		catch (final IOException ioe)
		{
			return false;
		}

		return false;
	}

	public static final boolean redirect(HttpServletRequest request, HttpServletResponse response, String page)
	{
		try
		{
			if (page != null)
			{
				response.sendRedirect(page);
				return true;
			}
		}
		catch (final IOException ioe)
		{
			return false;
		}

		return false;
	}

	/**
	 * 
	 * @return a String formatted as key=value
	 */
	public static final String toUrlParameter(Object key, Object value)
	{
		return replaceHtmlMetaCharacters(key) + Constants.EQUAL + replaceHtmlMetaCharacters(value);
	}

	public static final String toUrlParameter(Object key, int value)
	{
		return replaceHtmlMetaCharacters(key) + Constants.EQUAL + value;
	}

	public static final String toUrlParameter(Object key, boolean value)
	{
		return replaceHtmlMetaCharacters(key) + Constants.EQUAL + value;
	}

	/**
	 * 
	 * @return a String formatted as key=value1&key=value2&key=value3
	 */
	public static final String toUrlParameter(Object key, Object[] values)
	{
		final StringBuffer buff = new StringBuffer();
		for (int i = 0; i < values.length; i++)
		{
			buff.append(replaceHtmlMetaCharacters(key));
			buff.append(Constants.EQUAL);
			buff.append(replaceHtmlMetaCharacters(values[i]));
			if (i < values.length - 1)
			{
				buff.append(Constants.AMPERSAND);
			}
		}

		return buff.toString();
	}

	/**
	 * 
	 * @return a String formatted as key=value1&key=value2&key=value3
	 */
	public static final String toUrlParameter(Object key, Collection values)
	{
		final StringBuffer buff = new StringBuffer();
		for (final Iterator i = values.iterator(); i.hasNext();)
		{
			buff.append(replaceHtmlMetaCharacters(key));
			buff.append(Constants.EQUAL);
			buff.append(replaceHtmlMetaCharacters(i.next()));
			if (i.hasNext())
			{
				buff.append(Constants.AMPERSAND);
			}
		}

		return buff.toString();
	}

	/**
	 * 
	 * @return a String formatted as key1=value1&key2=value2&key3=value3
	 */
	public static final String toUrlParameter(Object[] keys, Object[] values)
	{
		final StringBuffer buff = new StringBuffer();
		for (int i = 0; i < keys.length; i++)
		{
			buff.append(replaceHtmlMetaCharacters(keys[i]));
			buff.append(Constants.EQUAL);
			buff.append(replaceHtmlMetaCharacters(values[i]));
			if (i < values.length - 1)
			{
				buff.append(Constants.AMPERSAND);
			}
		}

		return buff.toString();
	}

	public static final String appendParameterToUrl(String url, String key, Object[] values)
	{
		if (url == null)
		{
			return null;
		}

		if (!StringUtil.containsAny(url, "?"))
		{
			return url + Constants.QUESTIONMARK + toUrlParameter(key, values);
		}
		else
		{
			return url + Constants.AMPERSAND + toUrlParameter(key, values);
		}
	}

	public static final String appendParameterToUrl(String url, String key, boolean value)
	{
		if (url == null)
		{
			return null;
		}

		if (!StringUtil.containsAny(url, "?"))
		{
			return url + Constants.QUESTIONMARK + toUrlParameter(key, value);
		}
		else
		{
			return url + Constants.AMPERSAND + toUrlParameter(key, value);
		}
	}

	public static final String appendParameterToUrl(String url, String key, int value)
	{
		if (url == null)
		{
			return null;
		}

		if (!StringUtil.containsAny(url, "?"))
		{
			return url + Constants.QUESTIONMARK + toUrlParameter(key, value);
		}
		else
		{
			return url + Constants.AMPERSAND + toUrlParameter(key, value);
		}
	}

	public static final String appendParameterToUrl(String url, String key, Object value)
	{
		if (url == null)
		{
			return null;
		}

		if (!StringUtil.containsAny(url, "?"))
		{
			return url + Constants.QUESTIONMARK + toUrlParameter(key, value);
		}
		else
		{
			return url + Constants.AMPERSAND + toUrlParameter(key, value);
		}
	}

	public static final String appendParameterToUrl(String url, String[] keys, Object[] values)
	{
		if (url == null)
		{
			return null;
		}

		if (!StringUtil.containsAny(url, "?"))
		{
			return url + Constants.QUESTIONMARK + toUrlParameter(keys, values);
		}
		else
		{
			return url + Constants.AMPERSAND + toUrlParameter(keys, values);
		}
	}

	public static final String appendQueryStringToUrl(String url, String queryString)
	{
		if (url == null)
		{
			return null;
		}

		if (queryString == null)
		{
			return url;
		}

		if (!StringUtil.containsAny(url, "?"))
		{
			return url + Constants.QUESTIONMARK + queryString;
		}
		else
		{
			return url + Constants.AMPERSAND + queryString;
		}
	}

	public static final String appendAnchorToUrl(String url, String anchor)
	{
		if (url == null)
		{
			return null;
		}

		if (StringUtil.containsAny(url, "?"))
		{
			// fix for IE, when the anchor comes after the params, IE assumes
			// its part of the preceding param,
			// so we insert a fake useless param
			return url + "&fakeparam=ie-fix" + Constants.POUND + replaceHtmlMetaCharacters(anchor);
		}
		else
		{
			return url + Constants.POUND + replaceHtmlMetaCharacters(anchor);
		}
	}

	public static boolean isHTMLResponse(ServletResponse response)
	{
		if (response.getContentType() == null)
		{
			return false;
		}

		// pos int if string contains "text/html". -1 if not.
		return StringUtil.toLowerCase(response.getContentType()).indexOf(Constants.MIME_HTML) != -1;
	}

	public static Object getAttributeFromSession(HttpServletRequest request, String key)
	{
		return getAttribute(request.getSession(false), key);
	}

	public static Object getAttribute(HttpSession session, String key)
	{
		if (session == null)
		{
			return null;
		}
		else
		{
			return session.getAttribute(key);
		}

	}

	public static Object getAttribute(HttpServletRequest request, String key)
	{
		if (request == null)
		{
			return null;
		}
		else
		{
			return request.getAttribute(key);
		}

	}

	public static void setAttributeInSession(HttpServletRequest request, String key, Object value)
	{
		setAttribute(request.getSession(false), key, value);
	}

	public static void setAttributeInSession(HttpServletRequest request, String key, Object value, boolean createSession)
	{
		setAttribute(request.getSession(createSession), key, value);
	}

	public static void setAttribute(HttpSession session, String key, Object value)
	{
		if (session != null)
		{
			session.setAttribute(key, value);
		}
	}

	/**
	 * 
	 * @param a
	 *            url
	 * @return - the portion of the url representing the page name, e.g. http://foo.com/foo1/foo2/index.jsp would return index.jsp
	 */
	public static String getRequestedPageName(String url)
	{
		if (url == null)
		{
			return "";
		}

		if (!url.endsWith("/"))
		{
			int index = url.lastIndexOf("/");

			if (index == -1)
			{
				return "";
			}

			if (index++ <= url.length())
			{
				return url.substring(index, url.length());
			}
		}
		else
		{
			int index = url.substring(0, url.length() - 1).lastIndexOf("/");
			if (index == -1)
			{
				return "";
			}

			if (index++ <= url.length())
			{
				return url.substring(index, url.length());
			}
		}

		return "";
	}

	public static String getRequestedPageFileExtension(String url)
	{
		return url.contains(".") ? url.substring(url.indexOf(".")) : "";
	}

}
