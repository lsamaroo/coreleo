package com.coreleo.util.sql;

import java.util.Date;
import java.util.SimpleTimeZone;

public final class UTCDate extends DateAndTimeZone {

	public UTCDate(Date date) {
		super(date, new SimpleTimeZone(0,"UTC"));
	}

	public UTCDate() {
		super(new Date(),  new SimpleTimeZone(0,"UTC"));
	}

}
