/**
 * 
 */
package com.coreleo.servlet.filter;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

/**
 * @author Leon Samaroo
 * 
 */
public abstract class AbstractServletResponseWrapper extends HttpServletResponseWrapper {
	protected HttpServletResponse origResponse = null;
	protected ServletOutputStream stream = null;
	protected PrintWriter writer = null;


	public AbstractServletResponseWrapper(HttpServletResponse response) {
		super(response);
		origResponse = response;
	}


	public abstract ServletOutputStream createOutputStream() throws IOException;


	public void flushBuffer() throws IOException {
		stream.flush();
	}


	public ServletOutputStream getOutputStream() throws IOException {
		if (writer != null) {
			throw new IllegalStateException("getWriter() has already been called!");
		}

		if (stream == null)
			stream = createOutputStream();
		return (stream);

	}


	public PrintWriter getWriter() throws IOException {
		if (writer != null) {
			return (writer);
		}

		if (stream != null) {
			throw new IllegalStateException(
					"getOutputStream() has already been called!");
		}

		stream = createOutputStream();
		writer = new PrintWriter(new OutputStreamWriter(stream, origResponse.getCharacterEncoding()));
		return (writer);
	}


	public void finishResponse() {
		try {
			if (writer != null) {
				writer.close();
			}
			else {
				if (stream != null) {
					stream.close();
				}
			}
		}
		catch (IOException e) {
			// ignore
		}
		catch (Exception e) {
			// ignore
		}
	}

}
