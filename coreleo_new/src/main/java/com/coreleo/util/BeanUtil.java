package com.coreleo.util;

import java.util.HashMap;
import java.util.Map;

/**
 * This utility class allows you to map properties back and forth between Maps and java classes which conform to the JavaBeans
 * naming patterns for property getters and setters.
 * 
 * @author Leon Samaroo
 * 
 */
public class BeanUtil
{
	private static String GET = "get";
	private static String IS = "is";
	private static String SET = "set";

	private BeanUtil()
	{
	};

	public static Map<String, Object> toMap(Object bean)
	{
		final Map<String, Object> map = new HashMap<String, Object>();
		final String[] names = ReflectionUtil.getMethodNames(bean);

		for (final String name : names)
		{
			if (name.startsWith(GET))
			{
				final Object returnValue = ReflectionUtil.invoke(bean, name);
				final String key = StringUtil.toLowerCaseFirstCharacter(name.substring(3));
				map.put(key, returnValue);
			}
			else if (name.startsWith(IS))
			{
				final Object returnValue = ReflectionUtil.invoke(bean, name);
				final String key = StringUtil.toLowerCaseFirstCharacter(name.substring(2));
				map.put(key, returnValue);
			}
		}

		return map;
	}

	public static Object populateBean(Object bean, Map<String, Object> map)
	{
		if (bean == null)
		{
			return null;
		}

		if (map == null)
		{
			return bean;
		}

		for (final Map.Entry<String, Object> entry : map.entrySet())
		{
			final String key = StringUtil.toUpperCaseFirstCharacter(entry.getKey());
			final Object value = entry.getValue();
			ReflectionUtil.invoke(bean, SET + key, value);
		}

		return bean;
	}
}
