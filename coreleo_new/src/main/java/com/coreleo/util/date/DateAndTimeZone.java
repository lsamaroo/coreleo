package com.coreleo.util.date;

import java.util.Calendar;
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
		Calendar c = DateUtil.toCalendar(date);
		c.setTimeZone(timezone);
		return c.getTime();
	}

	public TimeZone getTimezone() {
		return timezone;
	}

}
