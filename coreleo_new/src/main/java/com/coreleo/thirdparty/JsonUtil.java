package com.coreleo.thirdparty;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.coreleo.SimpleException;
import com.coreleo.util.BeanUtil;
import com.coreleo.util.NumberUtil;

public class JsonUtil
{

	private JsonUtil()
	{
		super();
	}

	/**
	 * Converts the list to a JSONArray.  Objects in the list are converted to JSONObjects.
	 */
	public static String toArrayOfJsonObjects(List<?> list){	
			final JSONArray array = new JSONArray();
			for (Object obj : list) {
				array.put( toJson(obj) );
			}
			
			return array.toString();
	}
	
	public static final JSONObject toJson(Object bean)
	{
		final Map<String, Object> map = BeanUtil.toMap(bean);
		return toJson(map);
	}

	public static final JSONObject toJson(Map<String, Object> map)
	{
		return new JSONObject(map);
	}

	@SuppressWarnings("rawtypes")
	public static final JSONObject put(JSONObject jsonObj, String key, Object x)
	{
		if (jsonObj == null)
		{
			return null;
		}

		try
		{
			if (x instanceof Collection)
			{
				return jsonObj.put(key, (Collection) x);
			}
			else if (x instanceof Object[])
			{
				return jsonObj.put(key, toJsonArray((Object[]) x, true));
			}
			else
			{
				return jsonObj.put(key, x);
			}

		}
		catch (final JSONException jsone)
		{
			return jsonObj;
		}
	}

	public static final JSONObject put(JSONObject jsonObj, String key, char x)
	{
		if (jsonObj == null)
		{
			return null;
		}
		try
		{
			return jsonObj.put(key, Character.valueOf(x));
		}
		catch (final JSONException jsone)
		{
			return jsonObj;
		}
	}

	public static final JSONObject put(JSONObject jsonObj, String key, int x)
	{
		if (jsonObj == null)
		{
			return null;
		}
		try
		{
			return jsonObj.put(key, NumberUtil.toIntegerObject(x));
		}
		catch (final JSONException jsone)
		{
			return jsonObj;
		}
	}

	public static final JSONObject put(JSONObject jsonObj, String key, double x)
	{
		if (jsonObj == null)
		{
			return null;
		}
		try
		{
			return jsonObj.put(key, new Double(x));
		}
		catch (final JSONException jsone)
		{
			return jsonObj;
		}
	}

	public static final JSONObject put(JSONObject jsonObj, String key, float x)
	{
		if (jsonObj == null)
		{
			return null;
		}
		try
		{
			return jsonObj.put(key, Float.valueOf(x));
		}
		catch (final JSONException jsone)
		{
			return jsonObj;
		}
	}

	public static final JSONObject put(JSONObject jsonObj, String key, long x)
	{
		if (jsonObj == null)
		{
			return null;
		}
		try
		{
			return jsonObj.put(key, NumberUtil.toLongObject(x));
		}
		catch (final JSONException jsone)
		{
			return jsonObj;
		}
	}

	public static final JSONObject put(JSONObject jsonObj, String key, boolean x)
	{
		if (jsonObj == null)
		{
			return null;
		}
		try
		{
			return jsonObj.put(key, Boolean.valueOf(x));
		}
		catch (final JSONException jsone)
		{
			return jsonObj;
		}
	}

	public static final JSONArray toJsonArray(Object[] x, boolean returnEmptyArrayOnNull)
	{
		if (x == null)
		{
			return returnEmptyArrayOnNull ? new JSONArray() : null;
		}

		try
		{
			return new JSONArray(x);
		}
		catch (final JSONException e)
		{
			return returnEmptyArrayOnNull ? new JSONArray() : null;
		}
	}

	public static final JSONArray toJsonArray(Collection<?> x, boolean returnEmptyArrayOnNull)
	{
		if (x == null)
		{
			return returnEmptyArrayOnNull ? new JSONArray() : null;
		}

		return new JSONArray(x);
	}

	public static final JSONArray toJsonArray(Collection<?> x)
	{
		return toJsonArray(x, false);
	}

	public static final Map<String, Object> toMap(String jsonString)
	{
		if (jsonString == null)
		{
			return null;
		}

		try
		{
			return toMap(new JSONObject(jsonString));
		}
		catch (final JSONException jsone)
		{
			return new HashMap<String, Object>();
		}

	}

	public static final Map<String, Object> toMap(JSONObject jsonObject)
	{
		if (jsonObject == null)
		{
			return null;
		}

		final Map<String, Object> map = new LinkedHashMap<String, Object>();

		final JSONArray names = jsonObject.names();
		for (int i = 0; i < names.length(); i++)
		{
			try
			{
				final String key = names.getString(i);
				map.put(key, jsonObject.get(key));
			}
			catch (final JSONException jsone)
			{
				throw new SimpleException(jsone);
			}
		}

		return map;
	}
}
