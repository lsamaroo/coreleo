/**
 *
 */
package com.coreleo.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * LogHelper is a convenience class that extends the "ease of use" of the slf4j
 * API. It simply wraps the API in static calls so that the call to the logger
 * becomes 1 line of code. E.G.
 *
 * LogUtil.debug( this, "Some msg" );
 *
 *
 * @author Leon Samaroo
 *
 */
public class LogUtil {

	private LogUtil() {
		super();
	}
	/* ******************************************************************** */
	/* ***************************DEBUG METHODS *************************** */
	/* ******************************************************************** */

	public static final void debug(final String msg) {
		final Logger logger = getLogger(null);
		logger.debug(msg);
	}

	public static final void debug(final Object msg) {
		final Logger logger = getLogger(null);
		logger.debug(StringUtil.toString(msg));
	}

	public static final void debug(final Throwable t) {
		final Logger logger = getLogger(null);
		logger.debug("", t);
	}

	public static final void debug(final String msg, final Throwable t) {
		final Logger logger = getLogger(null);
		logger.debug(msg, t);
	}

	public static final void debug(final String format, final Object... args) {
		final Logger logger = getLogger(null);
		logger.debug(format, args);
	}

	public static final void debug(final Object src, final String msg) {
		final Logger logger = getLogger(src);
		logger.debug(msg);
	}

	public static final void debug(final Object src, final Object msg) {
		final Logger logger = getLogger(src);
		logger.debug(StringUtil.toString(msg));
	}

	public static final void debug(final Object src, final Throwable t) {
		final Logger logger = getLogger(src);
		logger.debug("", t);
	}

	public static final void debug(final Object src, final String msg, final Throwable t) {
		final Logger logger = getLogger(src);
		logger.debug(msg, t);
	}

	public static final void debug(final Object src, final Object msg, final Throwable t) {
		final Logger logger = getLogger(src);
		logger.debug(StringUtil.toString(msg), t);
	}

	public static final void debug(final Object src, final String format, final Object... args) {
		final Logger logger = getLogger(src);
		logger.debug(format, args);
	}

	/* ******************************************************************** */
	/* ***************************INFO METHODS **************************** */
	/* ******************************************************************** */

	public static final void info(final String msg) {
		final Logger logger = getLogger(null);
		logger.info(msg);
	}

	public static final void info(final Object msg) {
		final Logger logger = getLogger(null);
		logger.info(StringUtil.toString(msg));
	}

	public static final void info(final Throwable t) {
		final Logger logger = getLogger(null);
		logger.info("", t);
	}

	public static final void info(final String msg, final Throwable t) {
		final Logger logger = getLogger(null);
		logger.info(msg, t);
	}

	public static final void info(final String format, final Object... args) {
		final Logger logger = getLogger(null);
		logger.info(format, args);
	}

	public static final void info(final Object src, final String msg) {
		final Logger logger = getLogger(src);
		logger.info(msg);
	}

	public static final void info(final Object src, final Object msg) {
		final Logger logger = getLogger(src);
		logger.info(StringUtil.toString(msg));
	}

	public static final void info(final Object src, final Throwable t) {
		final Logger logger = getLogger(src);
		logger.info("", t);
	}

	public static final void info(final Object src, final String msg, final Throwable t) {
		final Logger logger = getLogger(src);
		logger.info(msg, t);
	}

	public static final void info(final Object src, final Object msg, final Throwable t) {
		final Logger logger = getLogger(src);
		logger.info(StringUtil.toString(msg), t);
	}

	public static final void info(final Object src, final String format, final Object... args) {
		final Logger logger = getLogger(src);
		logger.info(format, args);
	}

	/* ******************************************************************** */
	/* ***************************ERROR METHODS *************************** */
	/* ******************************************************************** */

	public static final void error(final String msg) {
		final Logger logger = getLogger(null);
		logger.error(msg);
	}

	public static final void error(final Object msg) {
		final Logger logger = getLogger(null);
		logger.error(StringUtil.toString(msg));
	}

	public static final void error(final Throwable t) {
		final Logger logger = getLogger(null);
		logger.error("", t);
	}

	public static final void error(final String msg, final Throwable t) {
		final Logger logger = getLogger(null);
		logger.error(msg, t);
	}

	public static final void error(final String format, final Object... args) {
		final Logger logger = getLogger(null);
		logger.error(format, args);
	}

	public static final void error(final Object src, final String msg) {
		final Logger logger = getLogger(src);
		logger.error(msg);
	}

	public static final void error(final Object src, final Object msg) {
		final Logger logger = getLogger(src);
		logger.error(StringUtil.toString(msg));
	}

	public static final void error(final Object src, final Throwable t) {
		final Logger logger = getLogger(src);
		logger.error("", t);
	}

	public static final void error(final Object src, final String msg, final Throwable t) {
		final Logger logger = getLogger(src);
		logger.error(msg, t);
	}

	public static final void error(final Object src, final Object msg, final Throwable t) {
		final Logger logger = getLogger(src);
		logger.error(StringUtil.toString(msg), t);
	}

	public static final void error(final Object src, final String format, final Object... args) {
		final Logger logger = getLogger(src);
		logger.error(format, args);
	}

	/* ******************************************************************** */
	/* ***************************FATAL METHODS *************************** */
	/* ******************************************************************** */

	@Deprecated
	public static final void fatal(final Object msg) {
		final Logger logger = getLogger(null);
		logger.error(StringUtil.toString(msg));
	}

	@Deprecated
	public static final void fatal(final Throwable t) {
		final Logger logger = getLogger(null);
		logger.error("", t);
	}

	@Deprecated
	public static final void fatal(final Object src, final Object msg) {
		final Logger logger = getLogger(src);
		logger.error(StringUtil.toString(msg));
	}

	@Deprecated
	public static final void fatal(final Object src, final Throwable t) {
		final Logger logger = getLogger(src);
		logger.error("", t);
	}

	@Deprecated
	public static final void fatal(final Object src, final Object msg, final Throwable t) {
		final Logger logger = getLogger(src);
		logger.error(StringUtil.toString(msg), t);
	}

	/* ******************************************************************** */
	/* ***************************TRACE METHODS *************************** */
	/* ******************************************************************** */

	public static final void trace(final String msg) {
		final Logger logger = getLogger(null);
		logger.trace(msg);
	}

	public static final void trace(final Object msg) {
		final Logger logger = getLogger(null);
		logger.trace(StringUtil.toString(msg));
	}

	public static final void trace(final Throwable t) {
		final Logger logger = getLogger(null);
		logger.trace("", t);
	}

	public static final void trace(final String msg, final Throwable t) {
		final Logger logger = getLogger(null);
		logger.trace(msg, t);
	}

	public static final void trace(final String format, final Object... args) {
		final Logger logger = getLogger(null);
		logger.trace(format, args);
	}

	public static final void trace(final Object src, final String msg) {
		final Logger logger = getLogger(src);
		logger.trace(msg);
	}

	public static final void trace(final Object src, final Object msg) {
		final Logger logger = getLogger(src);
		logger.trace(StringUtil.toString(msg));
	}

	public static final void trace(final Object src, final Throwable t) {
		final Logger logger = getLogger(src);
		logger.trace("", t);
	}

	public static final void trace(final Object src, final String msg, final Throwable t) {
		final Logger logger = getLogger(src);
		logger.trace(msg, t);
	}

	public static final void trace(final Object src, final Object msg, final Throwable t) {
		final Logger logger = getLogger(src);
		logger.trace(StringUtil.toString(msg), t);
	}

	public static final void trace(final Object src, final String format, final Object... args) {
		final Logger logger = getLogger(src);
		logger.trace(format, args);
	}

	/* ******************************************************************** */
	/* ***************************WARN METHODS **************************** */
	/* ******************************************************************** */

	public static final void warn(final String msg) {
		final Logger logger = getLogger(null);
		logger.warn(msg);
	}

	public static final void warn(final Object msg) {
		final Logger logger = getLogger(null);
		logger.warn(StringUtil.toString(msg));
	}

	public static final void warn(final Throwable t) {
		final Logger logger = getLogger(null);
		logger.warn("", t);
	}

	public static final void warn(final String msg, final Throwable t) {
		final Logger logger = getLogger(null);
		logger.warn(msg, t);
	}

	public static final void warn(final String format, final Object... args) {
		final Logger logger = getLogger(null);
		logger.warn(format, args);
	}

	public static final void warn(final Object src, final String msg) {
		final Logger logger = getLogger(src);
		logger.warn(msg);
	}

	public static final void warn(final Object src, final Object msg) {
		final Logger logger = getLogger(src);
		logger.warn(StringUtil.toString(msg));
	}

	public static final void warn(final Object src, final Throwable t) {
		final Logger logger = getLogger(src);
		logger.warn("", t);
	}

	public static final void warn(final Object src, final String msg, final Throwable t) {
		final Logger logger = getLogger(src);
		logger.warn(msg, t);
	}

	public static final void warn(final Object src, final Object msg, final Throwable t) {
		final Logger logger = getLogger(src);
		logger.warn(StringUtil.toString(msg), t);
	}

	public static final void warn(final Object src, final String format, final Object... args) {
		final Logger logger = getLogger(src);
		logger.warn(format, args);
	}

	private static final Logger getLogger(final Object src) {
		if (src == null) {
			return LoggerFactory.getLogger(LogUtil.class);
		}
		if (src instanceof Class) {
			return LoggerFactory.getLogger((Class<?>) src);
		}
		else {
			return LoggerFactory.getLogger(src.getClass());
		}
	}

}
