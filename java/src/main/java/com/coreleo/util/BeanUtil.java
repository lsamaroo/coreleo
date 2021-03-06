package com.coreleo.util;

import java.util.HashMap;
import java.util.Map;

/**
 * This utility class allows you to map properties back and forth between Maps
 * and java classes which conform to the JavaBeans naming patterns for property
 * getters and setters.
 *
 * @author Leon Samaroo
 *
 */
public class BeanUtil {
	private static String GET = "get";
	private static String IS = "is";
	private static String SET = "set";

	private BeanUtil() {
	};

	public static Map<String, Object> toMap(final Object bean) {
		final Map<String, Object> map = new HashMap<String, Object>();
		final String[] names = ReflectionUtil.getMethodNames(bean);

		for (final String name : names) {
			if (name.startsWith(GET)) {
				final Object returnValue = ReflectionUtil.invoke(bean, name);
				final String key = StringUtil.toLowerCaseCharAt(name.substring(3), 0);
				map.put(key, returnValue);
			}
			else if (name.startsWith(IS)) {
				final Object returnValue = ReflectionUtil.invoke(bean, name);
				final String key = StringUtil.toLowerCaseCharAt(name.substring(2), 0);
				map.put(key, returnValue);
			}
		}

		return map;
	}

	public static <T> T populateBean(final T bean, final Map<String, Object> data) {
		return populateBean(bean, data, false);
	}

	public static <T> T populateBean(final T bean, final Map<String, Object> data,
	        final boolean convertUnderscoreKeysToCamelCase) {
		if (bean == null) {
			return null;
		}

		if (data == null) {
			return bean;
		}

		for (final Map.Entry<String, Object> entry : data.entrySet()) {
			final String key = StringUtil.capitalize(convertUnderscoreKeysToCamelCase
			        ? StringUtil.underScoreToCamelCase(entry.getKey(), true) : entry.getKey());
			final Object value = entry.getValue();

			try {
				if (value != null) {
					ReflectionUtil.invoke(bean, SET + key, value);
				}
				else {
					ReflectionUtil.invoke(bean, SET + key, (Object[]) value);
				}

			}
			catch (final Exception e) {
				LogUtil.debug(e.getMessage());
				// ignore and continue
			}

		}
		return bean;
	}

	@SuppressWarnings("unchecked")
	public static <T> T populateBean(final String qualifiedClassName, final Map<String, Object> data) {
		final T bean = (T) ReflectionUtil.newInstance(qualifiedClassName);
		return populateBean(bean, data, false);
	}

	@SuppressWarnings("unchecked")
	public static <T> T populateBean(final Class<T> clazz, final Map<String, Object> data) {
		final T bean = (T) ReflectionUtil.newInstance(clazz);
		return populateBean(bean, data, false);
	}

}
