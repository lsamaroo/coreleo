package com.coreleo.util;

import com.coreleo.thirdparty.JacksonUtil;

public final class JsonUtil {

	private JsonUtil() {
		super();
	}

	public static final String toJsonString(final Object obj) {
		return JacksonUtil.toJsonString(obj);
	}
	
}
