package com.coreleo.util.sql.parser;

import java.util.TimeZone;

public class UTCRowAsBean extends RowAsBean {

	@SuppressWarnings("rawtypes")
	public UTCRowAsBean(Class clazz) {
		super(clazz, TimeZone.getTimeZone("UTC"));
	}

	public UTCRowAsBean(Object bean) {
		super(bean, TimeZone.getTimeZone("UTC"));
	}

}
