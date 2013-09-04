package com.coreleo.thirdparty;


import org.codehaus.jackson.map.ObjectMapper;

public class JacksonUtil {

	public static final String toJsonString(Object obj) {
		final ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.writeValueAsString(obj);
		} 
		catch (Exception e) {
			return "";
		}
	}
}
