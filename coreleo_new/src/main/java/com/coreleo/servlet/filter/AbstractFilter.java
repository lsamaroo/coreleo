/* 
 *  BaseFilter.java
 *
 *  Created on September 30, 2005, 11:47 AM
 *
 */

package com.coreleo.servlet.filter;

import javax.servlet.*;
import com.coreleo.util.*;

/**
 * 
 * @author Leon Samaroo
 * 
 * Base filter serves as a common superclass for all filters. Common features
 * can be encapsulated in the base filter and shared among all filters.
 * AbstractFilter is a good place to include default behavior for the container
 * callback methods.
 * 
 */
public abstract class AbstractFilter implements Filter {
    protected FilterConfig filterConfig;
    protected ServletContext context;


    /** Constructor */
    public AbstractFilter() {
        super();
    }


    /**
     * J2EE 1.4 - Servlet 2.3 Specification Called by the web container to
     * indicate to a filter that it is being placed into service. The servlet
     * container calls the init method exactly once after instantiating the
     * filter. The init method must complete successfully before the filter is
     * asked to do any filtering work.
     */
    public void init(FilterConfig fc) throws ServletException {
        setFilterConfig(fc);
        context = fc.getServletContext();
    }


    /**
     * J2EE 1.4 - Servlet 2.3 Specification Called by the web container to
     * indicate to a filter that it is being taken out of service. This method
     * is only called once all threads within the filter's doFilter method have
     * exited or after a timeout period has passed. After the web container
     * calls this method, it will not call the doFilter method again on this
     * instance of the filter.
     * 
     * This method gives the filter an opportunity to clean up any resources
     * that are being held (for example, memory, file handles, threads) and make
     * sure that any persistent state is synchronized with the filter's current
     * state in memory.
     * 
     * Override as needed - default behavior is to free resources being held by
     * FilterConfig Object
     */
    public void destroy() {
        filterConfig = null;
        context = null;
    }


    /**
     * Set the filter configuration object for this filter.
     * 
     * @param servletConfig
     *            The filter configuration object
     */
    public void setFilterConfig(FilterConfig fc) {
        filterConfig = fc;
    }


    /**
     * @return the filter configuration object for this filter.
     */
    public FilterConfig getFilterConfig() {
        return filterConfig;
    }


    /**
     * Checks to see if the response matches type text/html.
     * 
     * @param response -
     *            a ServletResponse object that contains the response sent to
     *            the client
     * 
     * @see <code>HttpServletResponse.getContentType()</code>
     * @return true if MIME type is equal to "text/html", false otherwise.
     */
    public boolean isHTML(ServletResponse response) {
        if (response.getContentType() == null) {
            return false;
        }

        // pos int if string contains "text/html". -1 if not.
        return response.getContentType().toLowerCase().indexOf(Constants.MIME_HTML) != -1;
    }


}