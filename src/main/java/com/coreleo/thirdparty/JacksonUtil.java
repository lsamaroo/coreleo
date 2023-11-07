package com.coreleo.thirdparty;

import java.util.HashMap;
import java.util.Map;

import com.coreleo.util.LogUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JacksonUtil {

	public static final String toJsonString(final Object obj) {
		try
		{
			return new ObjectMapper().writeValueAsString(obj);
		}
		catch (final Exception e)
		{
			LogUtil.error(e);
			return null;
		}
	}

	public static final Map<String, String> fromJson(final String json) {
		try
		{
			final ObjectMapper mapper = new ObjectMapper();
			return mapper.readValue(json, new TypeReference<HashMap<String, String>>() {
			});
		}
		catch (final Exception e)
		{
			LogUtil.error(e);
			return null;
		}

	}
}
