package com.coreleo.util.sql;

import java.util.Date;
import java.util.TimeZone;

public class DateAndTimeZone {
	private Date date;
	private TimeZone timezone;


	public DateAndTimeZone(Date date, TimeZone timezone) {
		super();
		this.date = date;
		this.timezone = timezone;
	}

	public Date getDate() {
		return date;
	}

	public TimeZone getTimezone() {
		return timezone;
	}

}
