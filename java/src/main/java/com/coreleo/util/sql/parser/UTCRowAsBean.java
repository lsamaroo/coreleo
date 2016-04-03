package com.coreleo.util.sql.parser;

import java.util.TimeZone;

public class UTCRowAsBean<T> extends RowAsBean<T> {

	@SuppressWarnings("rawtypes")
	public UTCRowAsBean(final Class clazz) {
		super(clazz, TimeZone.getTimeZone("UTC"));
	}

	public UTCRowAsBean(final T bean) {
		super(bean, TimeZone.getTimeZone("UTC"));
	}

}
