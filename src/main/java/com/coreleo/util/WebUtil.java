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
public final class WebUtil {

    private WebUtil() {
    }

    public static boolean isMobileClient(final HttpServletRequest request) {
        return StringUtil.containsSubStringIgnoreCase(request.getHeader("user-agent"), "Mobile");
    }

    public static Cookie getCookie(final HttpServletRequest request, final String name) {
        final var cookies = request.getCookies();
        for (var i = 0; i < ArrayUtil.size(cookies); i++) {
            final var c = cookies[i];
            if (StringUtil.equals(c.getName(), name)) {
                return c;
            }
        }
        return null;
    }

    /**
     * maxAge is defaulted to 365 * 24 * 60 * 60
     */
    public static void setCookie(final HttpServletResponse response, final String name, final String value) {
        setCookie(response, name, value, 365 * 24 * 60 * 60);
    }

    public static void setCookie(final HttpServletResponse response, final String name, final String value,
            final int maxAge) {
        final var cookie = new Cookie(name, value);
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }

    public static void invalidateSession(final HttpSession session) {
        if (session != null) {
            session.invalidate();
        }
    }

    public static void invalidateSession(final HttpServletRequest request) {
        if (request != null) {
            invalidateSession(request.getSession(false));
        }
    }

    public static Properties stripPrefixedRequestParamater(final String prefix, final HttpServletRequest request) {
        return stripPrefixedRequestParameter(prefix, request.getParameterMap());
    }

    public static Properties stripPrefixedRequestParameter(final String prefix, final Map parameterMap) {
        final var properties = new Properties();

        for (final Object element : parameterMap.keySet()) {
            final var key = String.valueOf(element);
            if (key.startsWith(prefix)) {
                final var value = MapUtil.getValue(parameterMap, key);

                LogUtil.debug(
                        "WebUtil:stripPrefixedRequestParameter - key=" + key.substring(prefix.length()));
                properties.put(key.substring(prefix.length()), value);
            }
        }

        return properties;
    }

    /**
     * @return the query string or null if there are no parameters or an error is
     *         encountered.
     */
    public static String toQueryString(final ServletRequest request) {
        return toQueryString(request.getParameterMap());
    }

    /**
     * @return the query string or null if there are no parameters or an error is
     *         encountered. The return String is formatted as
     *         key1=value1&key2=value2&key3=value3
     */
    public static String toQueryString(final Map map) {
        final var buff = new StringBuilder();

        try {
            for (final var i = map.entrySet().iterator(); i.hasNext();) {
                final var entry = (Map.Entry) i.next();

                final var key = entry.getKey();
                final var value = entry.getValue();

                if ((value != null) && (value instanceof Object[])) {
                    final var valueArray = (Object[]) value;
                    if (valueArray != null && valueArray.length > 0) {
                        for (final Object obj : valueArray) {
                            if (obj != null) {
                                buff.append(URLEncoder.encode(String.valueOf(key), Constants.UTF8));
                                buff.append(Constants.EQUAL);
                                buff.append(URLEncoder.encode(String.valueOf(obj), Constants.UTF8));
                            }
                        }
                    } else {
                        buff.append(URLEncoder.encode(String.valueOf(key), Constants.UTF8));
                        buff.append(Constants.EQUAL);
                        buff.append(URLEncoder.encode(String.valueOf(value), Constants.UTF8));
                    }
                }

                if (i.hasNext()) {
                    buff.append(Constants.AMPERSAND);
                }
            }
        } catch (final Exception e) {
            LogUtil.error("Error while creating query string", e);
            return null;
        }

        return (buff.length() != 0) ? buff.toString() : null;

    }

    /** replace metacharacters in HTML with their html values. */
    public static String[] replaceHtmlMetaCharacters(final String[] x) {
        if (x == null) {
            return null;
        }

        for (var i = 0; i < x.length; i++) {
            x[i] = replaceHtmlMetaCharacters(x[i]);
        }

        return x;
    }

    public static String replaceHtmlMetaCharacters(final Object x) {
        if (x == null) {
            return null;
        }

        return replaceHtmlMetaCharacters(x.toString());
    }

    /** replace metacharacters in HTML with their html values. */
    public static String replaceHtmlMetaCharacters(final String x) {
        return HtmlUtil.toHtmlEntityString(x);
    }

    /**
     * Method used for debugging.
     */
    public static void displayRequestParameters(final HttpServletRequest request) {
        LogUtil.trace("WebUtil:displayRequestParamaters - request=" + request);
        final var names = request.getParameterMap().keySet().toArray();
        Arrays.sort(names);
        for (final Object name : names) {
            LogUtil.info(name + " " + request.getParameter(name.toString()));
        }
    }

    /**
     * Method used for debugging.
     */
    public static void displayRequestAttributes(final HttpServletRequest request) {
        LogUtil.trace("WebUtil:displayRequestAttributes - request=" + request);
        final Enumeration names = request.getAttributeNames();
        while (names.hasMoreElements()) {
            final var name = names.nextElement();
            final var value = request.getAttribute(String.valueOf(name));
            LogUtil.info("Request attributes: name=" + name + " value=" + value);
        }
    }

    /**
     * Method used for debugging.
     */
    public static void displayRequestHeaders(final HttpServletRequest request) {
        LogUtil.trace("Displaying the request object headers.");
        for (final Enumeration i = request.getHeaderNames(); i.hasMoreElements();) {
            final var param = (String) i.nextElement();
            LogUtil.info(param + " " + request.getHeader(param));
        }
    }

    public static String convertRequestParametersToHiddenHtmlInputs(final HttpServletRequest request) {
        final Map map = request.getParameterMap();
        final var buff = new StringBuilder();
        final var set = map.entrySet();

        for (final Object element2 : set) {
            final var entry = (Map.Entry) element2;
            final var key = entry.getKey();
            final var value = (String[]) entry.getValue();
            if (value != null) {
                for (final String element : value) {
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

    public static String toHtmlCheckedUnchecked(final boolean checked) {
        if (checked) {
            return "checked=\"checked\"";
        }

        return "";
    }

    public static boolean forward(final HttpServletRequest request, final HttpServletResponse response,
            final String page) {
        try {
            if ((page != null) && (request.getRequestDispatcher(page) != null)) {
                request.getRequestDispatcher(page).forward(request, response);
                return true;
            }
        } catch (final ServletException | IOException ioe) {
        }

        return false;
    }

    public static boolean redirect(final HttpServletRequest request, final HttpServletResponse response,
            final String page) {
        try {
            if (page != null) {
                response.sendRedirect(page);
                return true;
            }
        } catch (final IOException ioe) {
        }

        return false;
    }

    /**
     * 
     * @return a String formatted as key=value
     */
    public static String toUrlParameter(final Object key, final Object value) {
        return replaceHtmlMetaCharacters(key) + Constants.EQUAL + replaceHtmlMetaCharacters(value);
    }

    public static String toUrlParameter(final Object key, final int value) {
        return replaceHtmlMetaCharacters(key) + Constants.EQUAL + value;
    }

    public static String toUrlParameter(final Object key, final boolean value) {
        return replaceHtmlMetaCharacters(key) + Constants.EQUAL + value;
    }

    /**
     * 
     * @return a String formatted as key=value1&key=value2&key=value3
     */
    public static String toUrlParameter(final Object key, final Object[] values) {
        final var buff = new StringBuilder();
        for (var i = 0; i < values.length; i++) {
            buff.append(replaceHtmlMetaCharacters(key));
            buff.append(Constants.EQUAL);
            buff.append(replaceHtmlMetaCharacters(values[i]));
            if (i < values.length - 1) {
                buff.append(Constants.AMPERSAND);
            }
        }

        return buff.toString();
    }

    /**
     * 
     * @return a String formatted as key=value1&key=value2&key=value3
     */
    public static String toUrlParameter(final Object key, final Collection values) {
        final var buff = new StringBuilder();
        for (final var i = values.iterator(); i.hasNext();) {
            buff.append(replaceHtmlMetaCharacters(key));
            buff.append(Constants.EQUAL);
            buff.append(replaceHtmlMetaCharacters(i.next()));
            if (i.hasNext()) {
                buff.append(Constants.AMPERSAND);
            }
        }

        return buff.toString();
    }

    /**
     * 
     * @return a String formatted as key1=value1&key2=value2&key3=value3
     */
    public static String toUrlParameter(final Object[] keys, final Object[] values) {
        final var buff = new StringBuilder();
        for (var i = 0; i < keys.length; i++) {
            buff.append(replaceHtmlMetaCharacters(keys[i]));
            buff.append(Constants.EQUAL);
            buff.append(replaceHtmlMetaCharacters(values[i]));
            if (i < values.length - 1) {
                buff.append(Constants.AMPERSAND);
            }
        }

        return buff.toString();
    }

    public static String appendParameterToUrl(final String url, final String key, final Object[] values) {
        if (url == null) {
            return null;
        }

        if (!StringUtil.containsAnyCharacters(url, "?")) {
            return url + Constants.QUESTIONMARK + toUrlParameter(key, values);
        }
        return url + Constants.AMPERSAND + toUrlParameter(key, values);
    }

    public static String appendParameterToUrl(final String url, final String key, final boolean value) {
        if (url == null) {
            return null;
        }

        if (!StringUtil.containsAnyCharacters(url, "?")) {
            return url + Constants.QUESTIONMARK + toUrlParameter(key, value);
        }
        return url + Constants.AMPERSAND + toUrlParameter(key, value);
    }

    public static String appendParameterToUrl(final String url, final String key, final int value) {
        if (url == null) {
            return null;
        }

        if (!StringUtil.containsAnyCharacters(url, "?")) {
            return url + Constants.QUESTIONMARK + toUrlParameter(key, value);
        }
        return url + Constants.AMPERSAND + toUrlParameter(key, value);
    }

    public static String appendParameterToUrl(final String url, final String key, final Object value) {
        if (url == null) {
            return null;
        }

        if (!StringUtil.containsAnyCharacters(url, "?")) {
            return url + Constants.QUESTIONMARK + toUrlParameter(key, value);
        }
        return url + Constants.AMPERSAND + toUrlParameter(key, value);
    }

    public static String appendParameterToUrl(final String url, final String[] keys, final Object[] values) {
        if (url == null) {
            return null;
        }

        if (!StringUtil.containsAnyCharacters(url, "?")) {
            return url + Constants.QUESTIONMARK + toUrlParameter(keys, values);
        }
        return url + Constants.AMPERSAND + toUrlParameter(keys, values);
    }

    public static String appendQueryStringToUrl(final String url, final String queryString) {
        if (url == null) {
            return null;
        }

        if (queryString == null) {
            return url;
        }

        if (!StringUtil.containsAnyCharacters(url, "?")) {
            return url + Constants.QUESTIONMARK + queryString;
        }
        return url + Constants.AMPERSAND + queryString;
    }

    public static String appendAnchorToUrl(final String url, final String anchor) {
        if (url == null) {
            return null;
        }

        if (StringUtil.containsAnyCharacters(url, "?")) {
            // fix for IE, when the anchor comes after the params, IE assumes
            // its part of the preceding param,
            // so we insert a fake useless param
            return url + "&fakeparam=ie-fix" + Constants.POUND + replaceHtmlMetaCharacters(anchor);
        }
        return url + Constants.POUND + replaceHtmlMetaCharacters(anchor);
    }

    public static boolean isHTMLResponse(final ServletResponse response) {
        if (response.getContentType() == null) {
            return false;
        }

        // pos int if string contains "text/html". -1 if not.
        return StringUtil.toLowerCase(response.getContentType()).indexOf(Constants.MIME_HTML) != -1;
    }

    public static Object getAttributeFromSession(final HttpServletRequest request, final String key) {
        return getAttribute(request.getSession(false), key);
    }

    public static Object getAttribute(final HttpSession session, final String key) {
        if (session == null) {
            return null;
        }
        return session.getAttribute(key);

    }

    public static Object getAttribute(final HttpServletRequest request, final String key) {
        if (request == null) {
            return null;
        }
        return request.getAttribute(key);

    }

    public static void setAttributeInSession(final HttpServletRequest request, final String key, final Object value) {
        setAttribute(request.getSession(false), key, value);
    }

    public static void setAttributeInSession(final HttpServletRequest request, final String key, final Object value,
            final boolean createSession) {
        setAttribute(request.getSession(createSession), key, value);
    }

    public static void setAttribute(final HttpSession session, final String key, final Object value) {
        if (session != null) {
            session.setAttribute(key, value);
        }
    }

    /**
     * 
     * @param a url
     * @return - the portion of the url representing the page name, e.g.
     *         http://foo.com/foo1/foo2/index.jsp would return index.jsp
     */
    public static String getRequestedPageName(final String url) {
        if (url == null) {
            return "";
        }

        if (!url.endsWith("/")) {
            var index = url.lastIndexOf("/");

            if (index == -1) {
                return "";
            }

            if (index++ <= url.length()) {
                return url.substring(index);
            }
        } else {
            var index = url.substring(0, url.length() - 1).lastIndexOf("/");
            if (index == -1) {
                return "";
            }

            if (index++ <= url.length()) {
                return url.substring(index);
            }
        }

        return "";
    }

    public static String getContextPath(final HttpServletRequest request) {
        var context = request.getContextPath();
        context = StringUtil.replace(context, "/", "");
        return context + "/";
    }

    public static String getRequestedPageFileExtension(final String url) {
        return url.contains(".") ? url.substring(url.indexOf(".")) : "";
    }

}
