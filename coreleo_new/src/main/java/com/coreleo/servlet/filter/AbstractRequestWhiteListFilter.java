package com.coreleo.servlet.filter;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.coreleo.util.*;

/**
 * 
 * 
 * @author Leon Samaroo <br>
 * 
 * Add the following to web.xml
 * 
 * <filter> <filter-name>WhiteListFilter</filter-name> <filter-class>
 * com.coreleo.servlet.filter.WhiteListFilter </filter-class> <init-param>
 * <param-name>isOn</param-name> <param-value>false</param-value>
 * </init-param> </filter>
 * 
 */
public abstract class AbstractRequestWhiteListFilter extends AbstractFilter {
    private boolean isOn = false;


    public void init(FilterConfig fc) throws ServletException {
        super.init(fc);
        if (fc != null) {
            isOn = BooleanUtil.toBoolean(fc.getInitParameter("isOn"), false);
        }
        LogUtil.info(this, "WhiteListFilter: isOn=" + isOn);
    }


    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (isOn) {
            for (Enumeration e = request.getParameterNames(); e.hasMoreElements();) {
                String key = String.valueOf(e.nextElement());
                String value = request.getParameter(key);
                if ( !isValidParameterName(key) || !isValidParameterValue(value)) {
                    WebUtil.forward((HttpServletRequest) request, (HttpServletResponse) response, getRedirectOnFailUrl() );
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
