package com.coreleo.util.sql.parser;

import java.util.TimeZone;

public class UTCRowAsMap extends RowAsMap {

	public UTCRowAsMap(final boolean useLowerCaseColumnNames) {
		super(useLowerCaseColumnNames, TimeZone.getTimeZone("UTC"));
	}

	public UTCRowAsMap() {
		super(TimeZone.getTimeZone("UTC"));
	}

}
