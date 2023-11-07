/**
 *
 */
package com.coreleo.servlet.filter;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

/**
 * @author Leon Samaroo
 *
 */
public abstract class AbstractServletResponseWrapper extends HttpServletResponseWrapper {
    protected HttpServletResponse origResponse = null;
    protected ServletOutputStream stream = null;
    protected PrintWriter writer = null;

    protected AbstractServletResponseWrapper(final HttpServletResponse response) {
        super(response);
        this.origResponse = response;
    }

    public abstract ServletOutputStream createOutputStream() throws IOException;

    @Override
    public void flushBuffer() throws IOException {
        this.stream.flush();
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        if (this.writer != null) {
            throw new IllegalStateException("getWriter() has already been called!");
        }

        if (this.stream == null) {
            this.stream = createOutputStream();
        }
        return (this.stream);

    }

    @Override
    public PrintWriter getWriter() throws IOException {
        if (this.writer != null) {
            return (this.writer);
        }

        if (this.stream != null) {
            throw new IllegalStateException("getOutputStream() has already been called!");
        }

        this.stream = createOutputStream();
        this.writer = new PrintWriter(new OutputStreamWriter(this.stream, this.origResponse.getCharacterEncoding()));
        return (this.writer);
    }

    public void finishResponse() {
        try {
            if (this.writer != null) {
                this.writer.close();
            } else if (this.stream != null) {
                this.stream.close();
            }
        } catch (final Exception e) {
            // ignore
        }
    }

}
