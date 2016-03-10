/**
 *
 */
package com.coreleo.util;

import java.net.URLDecoder;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;

/**
 * @author Leon Samaroo
 *
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public final class MapUtil {

	private MapUtil() {
		super();
	}

	/**
	 * Sorts the map according to the key's natural ordering.
	 */
	public static final Map<?, ?> sort(final Map<?, ?> map) {
		return new TreeMap(map);
	}

	public static final void clear(final Map<?, ?> map) {
		if (map != null) {
			map.clear();
		}
	}

	public static final boolean isEmpty(final Object x) {
		if (x == null) {
			return true;
		}

		if (x instanceof Map) {
			return ((Map) x).isEmpty();
		}

		return true;
	}

	public static final boolean isNotEmpty(final Object x) {
		return !isEmpty(x);
	}

	public static final boolean containsKey(final Map map, final Object key) {
		if (map != null) {
			return map.containsKey(key);
		}

		return false;
	}

	public static final boolean containsValue(final Map map, final Object value) {
		if (map != null) {
			return map.containsValue(value);
		}

		return false;
	}

	/**
	 *
	 * @return - the value as an Object[], or null if the value does not exist.
	 *
	 */
	public static final Object[] getValueAsObjectArray(final Map map, final Object key) {
		final Object obj = map.get(key);

		if (obj instanceof String) {
			return new String[] { (String) obj };
		}
		if (obj instanceof String[]) {
			return (String[]) obj;
		}
		else if (obj instanceof Object[]) {
			return (Object[]) obj;
		}
		else if (obj instanceof Collection && obj != null) {
			return ((Collection) obj).toArray();
		}
		else if (obj != null) {
			return new Object[] { obj };
		}
		else {
			return null;
		}
	}

	/**
	 * Convenience method for maps whose values are a collection or array of
	 * some type. If the map values are single item, then it returns that item.
	 * If the value is a collection it returns the first item in the collection.
	 *
	 */
	public static final Object getValue(final Map<?, ?> map, final Object key) {
		if (map == null || key == null) {
			return null;
		}

		final Object obj = map.get(key);

		if (obj instanceof Object[]) {
			return ArrayUtil.get((Object[]) obj, 0, null);
		}
		else if (obj instanceof List) {
			return CollectionUtil.getFirst((List) obj);
		}
		else if (obj instanceof Collection) {
			return CollectionUtil.getFirst((Collection) obj);
		}
		else {
			return obj;
		}
	}

	/**
	 *
	 * @return - the value as an String[], or null if the value does not exist.
	 *
	 */
	public static final String[] getStringArray(final Map map, final Object key) {
		final Object[] values = getValueAsObjectArray(map, key);

		if (values instanceof String[]) {
			return (String[]) values;
		}
		else {
			return ArrayUtil.toStringArray(values);
		}
	}

	/**
	 *
	 * @return - the value as an int[], or null if the value does not exist.
	 *
	 */
	public static final int[] getIntegerArray(final Map map, final Object key) {
		final Object[] values = getValueAsObjectArray(map, key);

		if (values instanceof String[]) {
			return ArrayUtil.toIntegerArray(values);
		}
		else if (values instanceof Integer[]) {
			return ArrayUtil.toIntegerArray(values);
		}
		else {
			return ArrayUtil.toIntegerArray(values);
		}
	}

	/**
	 *
	 * @return - the value as an in[], or null if the value does not exist.
	 *
	 */
	public static final int[] getIntegerArray(final Map map, final Object key, final int[] defaultValues) {
		try {
			return getIntegerArray(map, key);
		}
		catch (final Exception e) {
			return defaultValues;
		}
	}

	/**
	 *
	 * @return - the value as an Integer[], or null if the value does not exist.
	 *
	 */
	public static final Integer[] getIntegerObjectArray(final Map map, final Object key) {
		final Object[] values = getValueAsObjectArray(map, key);

		if (values instanceof String[]) {
			return ArrayUtil.toIntegerObjectArray(values);
		}
		else if (values instanceof Integer[]) {
			return (Integer[]) values;
		}
		else {
			return ArrayUtil.toIntegerObjectArray(values);
		}
	}

	/**
	 *
	 * @return - the value as an Integer[], or null if the value does not exist.
	 *
	 */
	public static final Integer[] getIntegerObjectArray(final Map map, final Object key,
	        final Integer[] defaultValues) {
		try {
			return getIntegerObjectArray(map, key);
		}
		catch (final Exception e) {
			return defaultValues;
		}
	}

	public static final Map<String, Object[]> queryStringtoMap(final Object obj) {
		String queryString = StringUtil.toString(obj, "");

		try {
			queryString = URLDecoder.decode(queryString, Constants.UTF8);
		}
		catch (final Exception e) {
			LogUtil.warn(e);
		}

		final HashMap<String, Object[]> map = new HashMap<String, Object[]>();

		if (queryString == null) {
			return map;
		}

		final Enumeration enumerator = new StringTokenizer(queryString, "&");
		while (enumerator.hasMoreElements()) {
			final String[] array = StringUtil.split(enumerator.nextElement(), "=");
			final String key = StringUtil.toString(ArrayUtil.get(array, 0));
			final String value = StringUtil.toString(ArrayUtil.get(array, 1));

			final Object previousValue = map.get(key);
			if (previousValue == null) {
				map.put(key, new String[] { value });
			}
			else {
				if (previousValue instanceof Object[]) {
					map.put(key, ArrayUtil.add((Object[]) previousValue, value));
				}
				else {
					map.put(key, new String[] { StringUtil.toString(previousValue), value });
				}
			}
		}

		return map;
	}

	/**
	 *
	 * @return a Map containing the delimited values as the keys of the map.
	 */
	public static final Map<String, ?> commaDelimitedStringToMapKeys(final String x) {
		return delimitedStringToMapKeys(x, ",");
	}

	/**
	 *
	 * @return a Map containing the delimited values as the keys of the map.
	 */
	public static final Map<String, ?> delimitedStringToMapKeys(final String x, final String delimiter) {
		final Map<String, ?> map = new HashMap();

		if (x == null || delimiter == null) {
			return map;
		}

		final Enumeration enumerator = new StringTokenizer(x, delimiter);
		while (enumerator.hasMoreElements()) {
			map.put(StringUtil.toString(enumerator.nextElement()), null);
		}

		return map;
	}

}
