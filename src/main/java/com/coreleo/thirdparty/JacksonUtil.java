package com.coreleo.thirdparty;


import com.coreleo.util.LogUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JacksonUtil {

	public static final String toJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} 
		catch (final Exception e) {
			LogUtil.error(e);
			return null;
		}
	}

}
