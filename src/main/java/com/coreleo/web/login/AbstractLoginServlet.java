/**
 *
 */
package com.coreleo.web.login;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.coreleo.util.WebUtil;

/**
 * @author Leon Samaroo
 *
 */
public abstract class AbstractLoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    protected static final int FAILED_AUTHENTICATION = 0;
    protected static final int INVALID_USER = 1;
    protected static final int SUCCESS = -100;
    protected static final int USER_LOCKED = 2;

    @Override
    protected void doPost(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException {
        doBeforeLogin(request, response);

        if (!userExists(request, response)) {
            WebUtil.redirect(request, response, getFailedView(INVALID_USER));
            WebUtil.invalidateSession(request);
            return;
        }

        final var result = isValidLogin(request, response);
        if (result != SUCCESS) {
            WebUtil.redirect(request, response, getFailedView(result));
            WebUtil.invalidateSession(request);
            return;

        }

        LoginUtil.setUserAsLoggedIn(request);
        doAfterLogin(request, response);
        WebUtil.redirect(request, response, getSuccessView());
    }

    protected abstract boolean userExists(HttpServletRequest request, HttpServletResponse response);

    protected abstract int isValidLogin(HttpServletRequest request, HttpServletResponse response);

    protected abstract void doBeforeLogin(HttpServletRequest request, HttpServletResponse response);

    protected abstract void doAfterLogin(HttpServletRequest request, HttpServletResponse response);

    protected abstract String getSuccessView();

    protected abstract String getFailedView(int reason);
}
