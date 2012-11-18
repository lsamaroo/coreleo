package com.coreleo.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.coreleo.SimpleException;

public class JsonUtil
{

	private JsonUtil()
	{
		super();
	}

	public static final JSONObject toJson(Object bean)
	{
		final Map<String, Object> map = BeanUtil.toMap(bean);
		return toJson(map);
	}

	public static final JSONObject toJson(Map<String, Object> map)
	{
		final JSONObject json = new JSONObject();

		return json;
	}

	public static final JSONObject put(JSONObject jsonObj, String key, Object x)
	{
		if (jsonObj == null)
		{
			return null;
		}
		try
		{
			return jsonObj.put(key, x);
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
			return jsonObj.put(key, new Float(x));
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

	public static final JSONArray toJsonArray(Collection x, boolean returnEmptyArrayOnNull)
	{
		if (x == null)
		{
			return returnEmptyArrayOnNull ? new JSONArray() : null;
		}

		return new JSONArray(x);
	}

	public static final JSONArray toJsonArray(Collection x)
	{
		return toJsonArray(x, false);
	}

	public static final Map toMap(String jsonString)
	{
		if (jsonString == null)
		{
			return null;
		}

		try
		{
			toMap(new JSONObject(jsonString));
		}
		catch (final JSONException jsone)
		{
			return null;
		}

		return new HashMap();
	}

	public static final Map toMap(JSONObject jsonObject)
	{
		if (jsonObject == null)
		{
			return null;
		}

		final Map map = new LinkedHashMap();

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
