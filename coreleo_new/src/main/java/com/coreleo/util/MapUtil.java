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
public final class MapUtil
{

	private MapUtil()
	{
		super();
	}

	/**
	 * Sorts the map according to the key's natural ordering.
	 */
	@SuppressWarnings("unchecked")
	public static final Map<?, ?> sort(Map<?, ?> map)
	{
		return new TreeMap(map);
	}

	public static final void clear(Map<?, ?> map)
	{
		if (map != null)
		{
			map.clear();
		}
	}

	public static final boolean isEmpty(Object x)
	{
		if (x == null)
		{
			return true;
		}

		if (x instanceof Map)
		{
			return ((Map) x).size() <= 0;
		}

		return true;
	}

	public static final boolean isNotEmpty(Object x)
	{
		return !isEmpty(x);
	}

	public static final boolean containsKey(Map map, Object key)
	{
		if (map != null)
		{
			return map.containsKey(key);
		}

		return false;
	}

	public static final boolean containsValue(Map map, Object value)
	{
		if (map != null)
		{
			return map.containsValue(value);
		}

		return false;
	}

	/**
	 * @deprecated - renamed to getValueAsObjectArray
	 */
	@Deprecated
	public static final Object[] getObjectArray(Map map, Object key)
	{
		return getValueAsObjectArray(map, key);
	}

	/**
	 * 
	 * @return - the value as an Object[], or null if the value does not exist.
	 * 
	 */
	public static final Object[] getValueAsObjectArray(Map map, Object key)
	{
		final Object obj = map.get(key);

		if (obj instanceof String)
		{
			return new String[] { (String) obj };
		}
		if (obj instanceof String[])
		{
			return (String[]) obj;
		}
		else if (obj instanceof Object[])
		{
			return (Object[]) obj;
		}
		else if (obj instanceof Collection && obj != null)
		{
			return ((Collection) obj).toArray();
		}
		else if (obj != null)
		{
			return new Object[] { obj };
		}
		else
		{
			return null;
		}
	}

	/**
	 * 
	 * An object[] containing your parameters or null if the parameter does not exist.
	 * 
	 * @deprecated - use get getValueAsObjectArray instead.
	 * 
	 */
	@Deprecated
	public static final Object[] getParameters(String key, Map map)
	{
		return getValueAsObjectArray(map, key);
	}

	/**
	 * @deprecated - renamed to getFirstValue
	 */
	@Deprecated
	public static final Object getFirstObject(Map map, Object key)
	{
		return getFirstValue(map, key);
	}

	/**
	 * Convenience method for maps whose values are a collection or array of some type. If the map values are single item, then it
	 * returns that item. or array, it returns that value.
	 * 
	 * @param map
	 * @param key
	 * 
	 * @return
	 */
	public static final Object getFirstValue(Map<?, ?> map, Object key)
	{
		if (map == null || key == null)
		{
			return null;
		}

		final Object obj = map.get(key);

		if (obj instanceof Object[])
		{
			final Object[] paramArray = (Object[]) obj;
			if (paramArray != null && paramArray.length > 0)
			{
				return paramArray[0] == null ? null : paramArray[0];
			}
		}
		else if (obj instanceof List)
		{
			final List list = (List) obj;
			if (list != null && list.size() > 0)
			{
				return list.get(0) == null ? null : list.get(0);
			}
		}
		else if (obj instanceof Collection)
		{
			final Collection c = (Collection) obj;
			if (!c.isEmpty())
			{
				return c.iterator().next();
			}
		}
		else
		{
			return (obj == null) ? null : obj;
		}

		return null;
	}

	/**
	 * 
	 * @deprecated - use getObject instead
	 * @param key
	 *            the name of the parameter to find.
	 * @param a
	 *            map
	 * 
	 * @return the paramater or null if the parameter cannot be found.
	 */
	@Deprecated
	public static final Object getParameter(String key, Map map)
	{
		return getFirstValue(map, key);
	}

	public static final String getString(Map map, Object key)
	{
		return getString(map, key, null);
	}

	/**
	 * 
	 * @return - the value as an String[], or null if the value does not exist.
	 * 
	 */
	public static final String[] getStringArray(Map map, Object key)
	{
		final Object[] values = getValueAsObjectArray(map, key);

		if (values instanceof String[])
		{
			return (String[]) values;
		}
		else
		{
			return ArrayUtil.toStringArray(values);
		}
	}

	/**
	 * 
	 * @return - the value as an int[], or null if the value does not exist.
	 * 
	 */
	public static final int[] getIntegerArray(Map map, Object key)
	{
		final Object[] values = getValueAsObjectArray(map, key);

		if (values instanceof String[])
		{
			return ArrayUtil.toIntegerArray(values);
		}
		else if (values instanceof Integer[])
		{
			return ArrayUtil.toIntegerArray(values);
		}
		else
		{
			return ArrayUtil.toIntegerArray(values);
		}
	}

	/**
	 * 
	 * @return - the value as an in[], or null if the value does not exist.
	 * 
	 */
	public static final int[] getIntegerArray(Map map, Object key, int[] defaultValues)
	{
		try
		{
			return getIntegerArray(map, key);
		}
		catch (final Exception e)
		{
			return defaultValues;
		}
	}

	/**
	 * 
	 * @return - the value as an Integer[], or null if the value does not exist.
	 * 
	 */
	public static final Integer[] getIntegerObjectArray(Map map, Object key)
	{
		final Object[] values = getValueAsObjectArray(map, key);

		if (values instanceof String[])
		{
			return ArrayUtil.toIntegerObjectArray(values);
		}
		else if (values instanceof Integer[])
		{
			return (Integer[]) values;
		}
		else
		{
			return ArrayUtil.toIntegerObjectArray(values);
		}
	}

	/**
	 * 
	 * @return - the value as an Integer[], or null if the value does not exist.
	 * 
	 */
	public static final Integer[] getIntegerObjectArray(Map map, Object key, Integer[] defaultValues)
	{
		try
		{
			return getIntegerObjectArray(map, key);
		}
		catch (final Exception e)
		{
			return defaultValues;
		}
	}

	/**
	 * 
	 * @return - the value as a String. If the value was a list of items, then it returns the first item in that list as a String.
	 */
	public static final String getString(Map map, Object key, Object defaultValue)
	{
		final Object value = getFirstValue(map, key);
		if (value != null)
		{
			return value.toString();
		}
		else
		{
			return String.valueOf(defaultValue);
		}
	}

	/**
	 * 
	 * @return - the value as a int. If the value was a list of items, then it returns the first item in that list as an int.
	 */
	public static final int getInteger(Map map, Object key)
	{
		final Object value = getFirstValue(map, key);
		return NumberUtil.toInteger(value);
	}

	public static final int getInteger(Map map, Object key, int defaultValue)
	{
		final Object value = getFirstValue(map, key);
		return NumberUtil.toInteger(value, defaultValue);
	}

	public static final int getInteger(Map map, Object key, Integer defaultValue)
	{
		final Object value = getFirstValue(map, key);
		return NumberUtil.toInteger(value, defaultValue);
	}

	/**
	 * 
	 * @return - the value as a Integer object. If the value was a list of items, then it returns the first item in that list as
	 *         an Integer.
	 */
	public static final Integer getIntegerObject(Map map, Object key)
	{
		return getIntegerObject(map, key, null);
	}

	public static final Integer getIntegerObject(Map map, Object key, Integer defaultValue)
	{
		final Object value = getFirstValue(map, key);
		return NumberUtil.toIntegerObject(value, defaultValue);
	}

	public static final Integer getIntegerObject(Map map, Object key, int defaultValue)
	{
		final Object value = getFirstValue(map, key);
		return NumberUtil.toIntegerObject(value, defaultValue);
	}

	/**
	 * 
	 * @return - the value as a boolean. If the value was a list of items, then it returns the first item in that list as an
	 *         boolean.
	 */
	public static final boolean getBoolean(Map map, Object key, boolean defaultValue)
	{
		final Object value = getFirstValue(map, key);
		return BooleanUtil.toBoolean(value, defaultValue);
	}

	public static final Boolean getBooleanObject(Map map, Object key, boolean defaultValue)
	{
		final Object value = getFirstValue(map, key);
		return BooleanUtil.toBooleanObject(value, defaultValue);
	}

	public static final Boolean getBooleanObject(Map map, Object key, Boolean defaultValue)
	{
		final Object value = getFirstValue(map, key);
		return BooleanUtil.toBooleanObject(value, defaultValue);
	}

	public static final double getDouble(Map map, Object key, double defaultValue)
	{
		final Object value = getFirstValue(map, key);
		return NumberUtil.toDouble(value, defaultValue);
	}

	public static final Double getDoubleObject(Map map, Object key, Double defaultValue)
	{
		final Object value = getFirstValue(map, key);
		return NumberUtil.toDoubleObject(value, defaultValue);
	}

	public static final Double getDoubleObject(Map map, Object key, double defaultValue)
	{
		final Object value = getFirstValue(map, key);
		return NumberUtil.toDoubleObject(value, defaultValue);
	}

	public static final long getLong(Map map, Object key, long defaultValue)
	{
		final Object value = getFirstValue(map, key);
		return NumberUtil.toLong(value, defaultValue);
	}

	public static final Long getLongObject(Map map, Object key, Long defaultValue)
	{
		final Object value = getFirstValue(map, key);
		return NumberUtil.toLongObject(value, defaultValue);
	}

	public static final Long getLongObject(Map map, Object key, long defaultValue)
	{
		final Object value = getFirstValue(map, key);
		return NumberUtil.toLongObject(value, defaultValue);
	}

	public static final Map<String, Object[]> queryStringtoMap(Object obj)
	{
		String queryString = StringUtil.toString(obj, "");

		try
		{
			queryString = URLDecoder.decode(queryString, Constants.UTF8);
		}
		catch (final Exception e)
		{
			// ignore
		}

		final HashMap<String, Object[]> map = new HashMap<String, Object[]>();

		if (queryString == null)
		{
			return map;
		}

		final Enumeration enumerator = new StringTokenizer(queryString, "&");
		while (enumerator.hasMoreElements())
		{
			final String[] array = StringUtil.split(enumerator.nextElement(), "=");
			final String key = StringUtil.toString(ArrayUtil.get(array, 0));
			final String value = StringUtil.toString(ArrayUtil.get(array, 1));

			final Object previousValue = map.get(key);
			if (previousValue == null)
			{
				map.put(key, new String[] { value });
			}
			else
			{
				if (previousValue instanceof Object[])
				{
					map.put(key, ArrayUtil.add((Object[]) previousValue, value));
				}
				else
				{
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
	public static final Map<String, ?> commaDelimitedStringToMap(String x)
	{
		return delimitedStringToMap(x, ",");
	}

	/**
	 * 
	 * @return a Map containing the delimited values as the keys of the map.
	 */
	public static final Map<String, ?> delimitedStringToMap(String x, String delimiter)
	{
		final HashMap<String, ?> map = new HashMap();

		if (x == null || delimiter == null)
		{
			return map;
		}

		final Enumeration enumerator = new StringTokenizer(x, delimiter);
		while (enumerator.hasMoreElements())
		{
			map.put(StringUtil.toString(enumerator.nextElement()), null);
		}

		return map;
	}

}
