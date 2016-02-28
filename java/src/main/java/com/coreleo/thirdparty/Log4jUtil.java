package com.coreleo.thirdparty;

import com.coreleo.util.ReflectionUtil;

public class Log4jUtil
{
	private final static String LOG4JLOGGER = "org.apache.log4j.Logger";
	private final static String LOG4JLEVEL = "org.apache.log4j.Level";
	private final static String TOLEVEL = "toLevel";
	private final static String SETLEVEL = "setLevel";
	private final static String GETLEVEL = "getLevel";

	public static final void setLogLevel(Object logger, String levelName)
	{
		if (ReflectionUtil.isInstance(LOG4JLOGGER, logger))
		{
			final Object level = ReflectionUtil.invokeStatic(LOG4JLEVEL, TOLEVEL, levelName);
			ReflectionUtil.invoke(logger, SETLEVEL, level);
		}
	}

	public static final Object getLogLevel(Object logger)
	{
		if (ReflectionUtil.isInstance(LOG4JLOGGER, logger))
		{
			return ReflectionUtil.invoke(logger, GETLEVEL);
		}
		else
		{
			return null;
		}
	}
}
