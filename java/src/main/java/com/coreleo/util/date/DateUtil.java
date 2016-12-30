/**
 *
 */
package com.coreleo.util.date;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.apache.commons.lang.time.FastDateFormat;

import com.coreleo.SimpleException;
import com.coreleo.util.Constants;

public final class DateUtil {

	/** MM/dd/yyyy - Only use this to format a date, parsing is not supported */
	public static final FastDateFormat MMddyyyyForwardSlash = FastDateFormat.getInstance("MM/dd/yyyy");

	/** ww - Only use this to format a date, parsing is not supported */
	public static final FastDateFormat ww = FastDateFormat.getInstance("ww");

	/** yyyy - Only use this to format a date, parsing is not supported */
	public static final FastDateFormat yyyy = FastDateFormat.getInstance("yyyy");

	/** yyyy-MM-dd - Only use this to format a date, parsing is not supported */
	public static final FastDateFormat yyyyMMddHyphened = FastDateFormat.getInstance("yyyy-MM-dd");

	/** MM-yyyy - Only use this to format a date, parsing is not supported */
	public static final FastDateFormat MMyyyyHyphened = FastDateFormat.getInstance("MM-yyyy");

	/** ww-yyyy - Only use this to format a date, parsing is not supported */
	public static final FastDateFormat wwyyyyHyphened = FastDateFormat.getInstance("ww-yyyy");

	/** MM-dd-yyyy - Only use this to format a date, parsing is not supported */
	public static final FastDateFormat MMddyyyyHyphened = FastDateFormat.getInstance("MM-dd-yyyy");

	/** MM-dd-yy - Only use this to format a date, parsing is not supported */
	public static final FastDateFormat MMddyyHyphened = FastDateFormat.getInstance("MM-dd-yy");

	/**
	 * yyyy-MM-dd HH:mm:ss - Only use this to format a date, parsing is not
	 * supported
	 */
	public static final FastDateFormat yyyyMMddHyphened_HHmmssColon = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss");

	/**
	 * MM/dd/yyyy HH:mm:ss - Only use this to format a date, parsing is not
	 * supported
	 */
	public static final FastDateFormat MMddyyyyForwardSlash_HHmmssColon = FastDateFormat
	        .getInstance("MM/dd/yyyy HH:mm:ss");

	/**
	 * MM-dd-yyyy HH:mm:ss - Only use this to format a date, parsing is not
	 * supported
	 */
	public static final FastDateFormat MMddyyyyHyphened_HHmmssColon = FastDateFormat.getInstance("MM-dd-yyyy HH:mm:ss");

	/** MMM dd, yyyy */
	public static final FastDateFormat MMM_dd_Coma_yyyy = FastDateFormat.getInstance("MMM dd, yyyy");

	/** hh:mm aa - Only use this to format a date, parsing is not supported */
	public static final FastDateFormat hhmmColon_aa = FastDateFormat.getInstance("hh:mm aa");

	/** h:mm aa - Only use this to format a date, parsing is not supported */
	public static final FastDateFormat hmmColon_aa = FastDateFormat.getInstance("h:mm aa");

	/** yyyy-MM-dd HH:mm aa */
	public static final FastDateFormat yyyyMMddHyphened_hhmmColon_aa = FastDateFormat
	        .getInstance("yyyy-MM-dd hh:mm aa");

	public static final FastDateFormat ISO8601DateTimeFormat = FastDateFormat.getInstance("yyyy-MM-dd'T'HH:mmZ");

	public static final FastDateFormat yyyyMMddHyphened_T_HHmmssColon = FastDateFormat
	        .getInstance("yyyy-MM-dd'T'HH:mm:ss");

	private DateUtil() {
	}

	/**
	 *
	 * Tests if x1 date is before the specified x2 date.
	 *
	 * @param x1
	 *            a date object
	 * @param x2
	 *            another date object
	 * @return true if x1 is before x2
	 */
	public static boolean before(final Date x1, final Date x2) {
		if (x1 == null || x2 == null) {
			return false;
		}
		return x1.before(x2);
	}

	/**
	 *
	 * Tests if x1 date is after the specified x2 date.
	 *
	 * @param x1
	 *            a date object
	 * @param x2
	 *            another date object
	 * @return true if x1 is after x2
	 */
	public static boolean after(final Date x1, final Date x2) {
		if (x1 == null || x2 == null) {
			return false;
		}
		return x1.after(x2);
	}

	/**
	 *
	 *
	 * @param x1
	 *            - Date object
	 * @param x2
	 *            - Date object
	 * @return - the value 0 if the argument x2 is equal to x1; a value less
	 *         than 0 if x1 is before x2 ; and a value greater than 0 if x1 is
	 *         after x2.
	 */
	public static int compare(final Date x1, final Date x2) {
		if ((x1 == null) && (x2 == null)) {
			return 0;
		}

		if (x1 == null) {
			return -1;
		}

		if (x2 == null) {
			return 1;
		}

		return x1.compareTo(x2);
	}

	/**
	 *
	 *
	 * @param x1
	 *            - A date or calendar object
	 * @param x2
	 *            - A date or calendar object
	 * @return - the value 0 if the argument x2 is equal to x1; a value less
	 *         than 0 if x1 is before x2 ; and a value greater than 0 if x1 is
	 *         after x2.
	 */
	public static int compare(final Object x1, final Object x2) {
		if ((x1 == null) && (x2 == null)) {
			return 0;
		}

		if (x1 == null) {
			return -1;
		}

		if (x2 == null) {
			return 1;
		}

		return toCalendar(x1).compareTo(toCalendar(x2));
	}

	public static Calendar utcTo(final Date date, final TimeZone tz) {
		final Calendar c = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		c.setTime(date);
		c.setTimeZone(tz);
		return c;
	}

	public static long getUnixTimestamp(final Date date) {
		return date.getTime() / 1000L;
	}

	public static final Calendar clone(final Calendar c) {
		return (Calendar) c.clone();
	}

	/**
	 * sets minimal day in first week to 4 sets first day of week to Monday
	 *
	 * @return a new Calendar object
	 */
	public static final Calendar toISO(final Calendar c) {
		final Calendar cal = clone(c);
		cal.setMinimalDaysInFirstWeek(4);
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		return cal;
	}

	/**
	 * sets minimal day in first week to 4 sets first day of week to Monday
	 *
	 * @return a new Calendar object
	 */
	public static final Calendar toISO(final Date date) {
		final Calendar cal = toCalendar(date);
		cal.setMinimalDaysInFirstWeek(4);
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		return cal;
	}

	public static String format(final Calendar x, final String pattern) {
		return FastDateFormat.getInstance(pattern, x.getTimeZone()).format(x);
	}

	/**
	 *
	 * @param date
	 *            - the date string to parse
	 * @param pattern
	 *            - the pattern the date string should be
	 * @return - true if it is a valid date, false otherwise
	 */
	public static Date parse(final String date, final String pattern) {
		try {
			return new SimpleDateFormat(pattern).parse(date);
		}
		catch (final ParseException e) {
			throw new SimpleException(e);
		}
	}

	/**
	 *
	 * @param date
	 *            - the date string to parse
	 * @param pattern
	 *            - the pattern the date string should be
	 * @param lenient
	 *            - Specify whether or not date/time parsing is to be lenient.
	 * @return - Date object if parsed successfully
	 * @throws SimpleException
	 *             if it fails to parse the date
	 */
	public static Date parse(final String date, final String pattern, final boolean lenient) {
		try {
			final SimpleDateFormat df = new SimpleDateFormat(pattern);
			df.setLenient(lenient);
			return df.parse(date);
		}
		catch (final ParseException e) {
			throw new SimpleException(e);
		}
	}

	public static Date parse(final String date, final String pattern, final TimeZone tz) {
		try {
			final SimpleDateFormat sf = new SimpleDateFormat(pattern);
			sf.setTimeZone(tz);
			return sf.parse(date);
		}
		catch (final ParseException e) {
			throw new SimpleException(e);
		}
	}

	/**
	 *
	 */
	public static Date parse(final DateFormat format, final String date) {
		try {
			return format.parse(date);
		}
		catch (final ParseException e) {
			return null;
		}
	}

	public static Timestamp toTimestamp(final Calendar cal) {
		if (cal == null) {
			throw new IllegalArgumentException("Calendar is null");
		}

		return new Timestamp(cal.getTime().getTime());
	}

	public static Timestamp toTimestamp(final Date date) {
		if (date == null) {
			throw new IllegalArgumentException("Date is null");
		}

		return new Timestamp(date.getTime());
	}

	public static final Calendar yesterday() {
		return addDays(Calendar.getInstance(), -1);
	}

	public static final Calendar tomorrow() {
		return addDays(Calendar.getInstance(), 1);
	}

	public static final Calendar today() {
		return Calendar.getInstance();
	}

	/**
	 *
	 * @return a new calendar
	 */
	public static Calendar setTime(final Calendar c, final int hour, final int minute, final int second,
	        final int millisecond) {
		final Calendar cal = clone(c);
		cal.set(Calendar.HOUR_OF_DAY, hour);
		cal.set(Calendar.MINUTE, minute);
		cal.set(Calendar.SECOND, second);
		cal.set(Calendar.MILLISECOND, millisecond);
		return cal;
	}

	public static Date setTime(final Date date, final int hour, final int minute, final int second,
	        final int millisecond) {
		if (date == null) {
			return null;
		}
		final Calendar cal = toCalendar(date);
		cal.set(Calendar.HOUR_OF_DAY, hour);
		cal.set(Calendar.MINUTE, minute);
		cal.set(Calendar.SECOND, second);
		cal.set(Calendar.MILLISECOND, millisecond);
		return cal.getTime();
	}

	/**
	 *
	 * sets hour, minute, second, millisecond to zero
	 */
	public static Date zeroOutTime(final Date date) {
		return setTime(date, 0, 0, 0, 0);
	}

	public static Calendar zeroOutTime(final Calendar cal) {
		return setTime(cal, 0, 0, 0, 0);
	}

	public static Calendar toCalendar(final Object date) {
		if (date == null) {
			throw new IllegalArgumentException("Date is null");
		}

		if (date instanceof Date) {
			return toCalendar((Date) date);
		}

		if (date instanceof Calendar) {
			return (Calendar) date;
		}

		throw new IllegalArgumentException("Date must be an instance of java.util.Date or java.util.Calendar");
	}

	public static Calendar toCalendar(final Date date) {
		if (date == null) {
			throw new IllegalArgumentException("Date is null");
		}
		final Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal;
	}

	public static Calendar toCalendar(final Date date, final int hourOfDay, final int minute, final int second,
	        final int millisecond) {
		final Calendar cal = toCalendar(date);
		cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
		cal.set(Calendar.MINUTE, minute);
		cal.set(Calendar.SECOND, second);
		cal.set(Calendar.MILLISECOND, millisecond);
		return cal;
	}

	public static Calendar toCalendar(final Date date, final String hourOfDay, final String minute,
	        final String second) {
		final Calendar cal = toCalendar(date);
		cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hourOfDay));
		cal.set(Calendar.MINUTE, Integer.parseInt(minute));
		cal.set(Calendar.SECOND, Integer.parseInt(second));
		return cal;
	}

	/**
	 *
	 * @return a lenient Calendar set to the given values
	 */
	public static Calendar toCalendar(final int month, final int day) {
		final Calendar cal = Calendar.getInstance();
		cal.set(Calendar.MONTH, month);
		cal.set(Calendar.DAY_OF_MONTH, day);
		return cal;
	}

	/**
	 *
	 * @return a lenient Calendar set to the given values
	 */
	public static Calendar toCalendar(final String month, final String day) {
		final Calendar cal = Calendar.getInstance();
		cal.set(Calendar.MONTH, Integer.parseInt(month));
		cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(day));
		return cal;
	}

	/**
	 *
	 * @return a lenient Calendar set to the given values
	 */
	public static Calendar toCalendar(final int year, final int month, final int day) {
		final Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month);
		cal.set(Calendar.DAY_OF_MONTH, day);
		return cal;
	}

	/**
	 *
	 * @return a lenient Calendar set to the given values
	 */
	public static Calendar toCalendar(final String year, final String month, final String day) {
		final Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, Integer.parseInt(year));
		cal.set(Calendar.MONTH, Integer.parseInt(month));
		cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(day));
		return cal;
	}

	/**
	 *
	 * @return a lenient Calendar set to the given values
	 */
	public static Calendar toCalendar(final String year, final String month, final String day, final String hourOfDay,
	        final String minute, final String second) {
		final Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, Integer.parseInt(year));
		cal.set(Calendar.MONTH, Integer.parseInt(month));
		cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(day));
		cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hourOfDay));
		cal.set(Calendar.MINUTE, Integer.parseInt(minute));
		cal.set(Calendar.SECOND, Integer.parseInt(second));
		return cal;
	}

	public static Calendar toCalendar(final String year, final String month, final String day, final String hourOfDay,
	        final String minute, final String second, final String millisecond) {
		final Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, Integer.parseInt(year));
		cal.set(Calendar.MONTH, Integer.parseInt(month));
		cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(day));
		cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hourOfDay));
		cal.set(Calendar.MINUTE, Integer.parseInt(minute));
		cal.set(Calendar.SECOND, Integer.parseInt(second));
		cal.set(Calendar.MILLISECOND, Integer.parseInt(millisecond));
		return cal;
	}

	/**
	 *
	 * @return a lenient Calendar set to the given values
	 */
	public static Calendar toCalendar(final int year, final int month, final int day, final int hourOfDay,
	        final int minute, final int second) {
		final Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month);
		cal.set(Calendar.DAY_OF_MONTH, day);
		cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
		cal.set(Calendar.MINUTE, minute);
		cal.set(Calendar.SECOND, second);
		return cal;
	}

	/**
	 *
	 * @return a lenient Calendar set to the given values
	 */
	public static Calendar toCalendar(final int year, final int month, final int day, final int hourOfDay,
	        final int minute, final int second, final int millisecond) {
		final Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month);
		cal.set(Calendar.DAY_OF_MONTH, day);
		cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
		cal.set(Calendar.MINUTE, minute);
		cal.set(Calendar.SECOND, second);
		cal.set(Calendar.MILLISECOND, millisecond);
		return cal;
	}

	public static Date toDate(final Calendar cal) {
		return cal.getTime();
	}

	public static Date toDate(final int month, final int day) {
		return toCalendar(month, day).getTime();
	}

	public static Date toDate(final String month, final String day) {
		return toCalendar(month, day).getTime();
	}

	public static Date toDate(final int year, final int month, final int day) {
		return toCalendar(year, month, day).getTime();
	}

	public static Date toDate(final String year, final String month, final String day) {
		return toCalendar(year, month, day).getTime();
	}

	public static Date toDate(final int year, final int month, final int day, final int hourOfDay, final int minute,
	        final int second) {
		return toCalendar(year, month, day, hourOfDay, minute, second).getTime();
	}

	public static Date toDate(final String year, final String month, final String day, final String hourOfDay,
	        final String minute, final String second) {
		return toCalendar(year, month, day, hourOfDay, minute, second).getTime();
	}

	public static Date toDate(final int year, final int month, final int day, final int hourOfDay, final int minute,
	        final int second, final int millisecond) {
		return toCalendar(year, month, day, hourOfDay, minute, second, millisecond).getTime();
	}

	public static Date toDate(final String year, final String month, final String day, final String hourOfDay,
	        final String minute, final String second, final String millisecond) {
		return toCalendar(year, month, day, hourOfDay, minute, second, millisecond).getTime();
	}

	/**
	 *
	 * @param date
	 *            - the date string to parse
	 * @param pattern
	 *            - the pattern the date string should be
	 *
	 * @return - true if it is a valid date, false otherwise
	 */
	public static final boolean isValidDate(final String date, final String pattern) {
		try {
			parse(date, pattern, false);
			return true;
		}
		catch (final Exception e) {
			return false;
		}
	}

	public static final boolean isValidDate(final int month, final int day, final int year) {
		try {
			final Calendar cal = Calendar.getInstance();
			cal.setLenient(false); // must do this
			cal.set(Calendar.YEAR, year);
			cal.set(Calendar.MONTH, month);
			cal.set(Calendar.DAY_OF_MONTH, day);
			cal.getTime(); // exception thrown here
		}
		catch (final Exception e) {
			return false;
		}

		return true;
	}

	public static final boolean isValidDate(final String month, final String day, final String year) {
		try {
			return isValidDate(Integer.parseInt(month), Integer.parseInt(day), Integer.parseInt(year));
		}
		catch (final Exception e) {
			return false;
		}
	}

	public static final int getDay(final Calendar cal) {
		return cal.get(Calendar.DAY_OF_MONTH);
	}

	public static final int getDay(final Date date) {
		final Calendar cal = toCalendar(date);
		return cal.get(Calendar.DAY_OF_MONTH);
	}

	public static int getYear(final Date date) {
		final Calendar cal = toCalendar(date);
		return cal.get(Calendar.YEAR);
	}

	public static int getYear(final Calendar cal) {
		return cal.get(Calendar.YEAR);
	}

	public static final int getMonth(final Date date) {
		final Calendar cal = toCalendar(date);
		return cal.get(Calendar.MONTH);
	}

	public static final int getMonth(final Calendar cal) {
		return cal.get(Calendar.MONTH);
	}

	public static final int getHourOfDay(final Date date) {
		final Calendar cal = toCalendar(date);
		return cal.get(Calendar.HOUR_OF_DAY);
	}

	public static final int getHourOfDay(final Calendar cal) {
		return cal.get(Calendar.HOUR_OF_DAY);
	}

	public static final int getMinute(final Date date) {
		final Calendar cal = toCalendar(date);
		return cal.get(Calendar.MINUTE);
	}

	public static final int getMinute(final Calendar cal) {
		return cal.get(Calendar.MINUTE);
	}

	public static final int getSecond(final Date date) {
		final Calendar cal = toCalendar(date);
		return cal.get(Calendar.SECOND);
	}

	public static final int getSecond(final Calendar cal) {
		return cal.get(Calendar.SECOND);
	}

	public static final Date set(final Date date, final int field, final int value) {
		final Calendar c = toCalendar(date);
		c.set(field, value);
		return c.getTime();
	}

	public static final Calendar set(final Calendar c, final int field, final int value) {
		if (c == null) {
			throw new IllegalArgumentException("Calendar is null");
		}

		final Calendar cal = clone(c);
		cal.set(field, value);
		return cal;
	}

	public static final int getActualMaximum(final Date date, final int field) {
		final Calendar c = toCalendar(date);
		return c.getActualMaximum(field);
	}

	public static final int getActualMinimum(final Date date, final int field) {
		final Calendar c = toCalendar(date);
		return c.getActualMinimum(field);
	}

	public static final Date setToLastDayOfMonth(final Date date) {
		final Calendar c = toCalendar(date);
		c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
		return c.getTime();
	}

	public static final Calendar setToLastDayOfMonth(final Calendar c) {
		final Calendar cal = clone(c);
		cal.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
		return cal;
	}

	// -------------------------------------------------------------------------------------------
	// Days
	// -------------------------------------------------------------------------------------------

	/**
	 *
	 * Returns the number of days between two dates, excluding the end date.
	 *
	 */
	public static final int getNumberOfDaysBetween(final Calendar start, final Calendar end,
	        final boolean includeTimeInCalculation) {
		final long time = subtract(end, start, includeTimeInCalculation);
		return (int) Math.round(((time) / Constants.ONE_DAY));
	}

	/**
	 *
	 * Returns the number of days between two dates, excluding the end date.
	 *
	 */
	public static final int getNumberOfDaysBetween(final Date start, final Date end,
	        final boolean includeTimeInCalculation) {
		final Calendar cal1 = toCalendar(start);
		final Calendar cal2 = toCalendar(end);
		return getNumberOfDaysBetween(cal1, cal2, includeTimeInCalculation);
	}

	/**
	 *
	 * @deprecated - use getDaysBetween instead
	 */
	@Deprecated
	public static Date[] getDailyDatesBetween(final Date start, final Date end, final boolean inclusive) {
		return getDaysBetween(start, end, inclusive);
	}

	public static Calendar[] getDaysBetween(final Calendar start, final Calendar end, final boolean includeEndDate) {
		final int numberOfDaysBetween = getNumberOfDaysBetween(start, end, false);

		if (numberOfDaysBetween < 0) {
			return null;
		}

		Calendar[] dates = null;

		if (includeEndDate) {
			dates = new Calendar[numberOfDaysBetween == 1 ? 2 : numberOfDaysBetween + 1];
			for (int i = 0; i < dates.length; i++) {
				dates[i] = addDays(start, i);
			}
		}
		else {
			dates = new Calendar[numberOfDaysBetween < 1 ? 0 : numberOfDaysBetween - 1];
			for (int i = 0; i < dates.length; i++) {
				dates[i] = addDays(start, i + 1);
			}
		}

		return dates;
	}

	public static Date[] getDaysBetween(final Date start, final Date end, final boolean includeEndDate) {
		final int numberOfDaysBetween = getNumberOfDaysBetween(start, end, false);

		if (numberOfDaysBetween < 0) {
			return null;
		}

		Date[] dates = null;

		if (includeEndDate) {
			dates = new Date[numberOfDaysBetween == 1 ? 2 : numberOfDaysBetween + 1];
			for (int i = 0; i < dates.length; i++) {
				dates[i] = addDays(start, i);
			}
		}
		else {
			dates = new Date[numberOfDaysBetween < 1 ? 0 : numberOfDaysBetween - 1];
			for (int i = 0; i < dates.length; i++) {
				dates[i] = addDays(start, i + 1);
			}
		}

		return dates;
	}

	public static String[] getFormattedDaysBetween(final Calendar start, final Calendar end, final boolean inclusive,
	        final Format format) {
		final int numberOfDaysBetween = getNumberOfDaysBetween(start, end, false);

		if (numberOfDaysBetween < 0) {
			return null;
		}

		String[] dates = null;

		if (inclusive) {
			dates = new String[numberOfDaysBetween == 1 ? 2 : numberOfDaysBetween + 1];
			for (int i = 0; i < dates.length; i++) {
				dates[i] = format.format(addDays(start, i));
			}
		}
		else {
			dates = new String[numberOfDaysBetween < 1 ? 0 : numberOfDaysBetween - 1];
			for (int i = 0; i < dates.length; i++) {
				dates[i] = format.format(addDays(start, i + 1));
			}
		}

		return dates;
	}

	/**
	 * @return a String array with dates formatted with the given date format
	 */
	public static String[] getFormattedDaysBetween(final Date start, final Date end, final boolean inclusive,
	        final Format format) {
		final int numberOfDaysBetween = getNumberOfDaysBetween(start, end, false);

		if (numberOfDaysBetween < 0) {
			return null;
		}

		String[] dates = null;

		if (inclusive) {
			dates = new String[numberOfDaysBetween == 1 ? 2 : numberOfDaysBetween + 1];
			for (int i = 0; i < dates.length; i++) {
				dates[i] = format.format(addDays(start, i));
			}
		}
		else {
			dates = new String[numberOfDaysBetween < 1 ? 0 : numberOfDaysBetween - 1];
			for (int i = 0; i < dates.length; i++) {
				dates[i] = format.format(addDays(start, i + 1));
			}
		}

		return dates;
	}

	// -------------------------------------------------------------------------------------------
	// Week
	// -------------------------------------------------------------------------------------------

	public static final Calendar changeDayOfWeek(final Calendar theWeek, final int newDayOfWeek) {
		final Calendar cal = clone(theWeek);
		cal.set(Calendar.DAY_OF_WEEK, newDayOfWeek);
		return cal;
	}

	public static final Date changeDayOfWeek(final Date theWeek, final int newDayOfWeek) {
		final Calendar cal = toCalendar(theWeek);
		cal.set(Calendar.DAY_OF_WEEK, newDayOfWeek);
		return cal.getTime();
	}

	/**
	 *
	 * Returns the number of weeks between two dates, not including the start or
	 * end .
	 *
	 */
	public static final int getNumberOfWeeksBetween(final Calendar start, final Calendar end) {
		final int weekOffset = (end.get(Calendar.DAY_OF_WEEK) - start.get(Calendar.DAY_OF_WEEK)) < 0 ? 1 : 0;
		final int numberOfDaysBetween = getNumberOfDaysBetween(start, end, false);

		final int numberOfWeeksBetween = numberOfDaysBetween / 7;
		return Math.round(numberOfWeeksBetween + weekOffset);
	}

	public static final int getNumberOfWeeksBetween(final Date start, final Date end) {
		return getNumberOfWeeksBetween(toCalendar(start), toCalendar(end));
	}

	public static final Calendar[] getWeeksBetween(final Calendar start, final Calendar end, final boolean inclusive) {
		final int numberOfWeeksBetween = getNumberOfWeeksBetween(start, end);

		if (numberOfWeeksBetween < 0) {
			return null;
		}

		Calendar[] dates = null;

		if (inclusive) {
			dates = new Calendar[numberOfWeeksBetween == 1 ? 2 : numberOfWeeksBetween + 1];
			for (int i = 0; i < dates.length; i++) {
				dates[i] = addWeeks(start, i);
			}
		}
		else {
			dates = new Calendar[numberOfWeeksBetween <= 0 ? 0 : numberOfWeeksBetween - 1];
			for (int i = 0; i < dates.length; i++) {
				dates[i] = addWeeks(start, i + 1);
			}
		}

		return dates;
	}

	public static final Date[] getWeeksBetween(final Date start, final Date end, final boolean inclusive) {
		final int numberOfWeeksBetween = getNumberOfWeeksBetween(start, end);

		if (numberOfWeeksBetween < 0) {
			return null;
		}

		Date[] dates = null;

		if (inclusive) {
			dates = new Date[numberOfWeeksBetween == 1 ? 2 : numberOfWeeksBetween + 1];
			for (int i = 0; i < dates.length; i++) {
				dates[i] = addWeeks(start, i);
			}
		}
		else {
			dates = new Date[numberOfWeeksBetween <= 0 ? 0 : numberOfWeeksBetween - 1];
			for (int i = 0; i < dates.length; i++) {
				dates[i] = addWeeks(start, i + 1);
			}
		}

		return dates;
	}

	public static final String[] getFormattedWeeksBetween(final Calendar start, final Calendar end,
	        final boolean inclusive, final Format format) {
		final int numberOfWeeksBetween = getNumberOfWeeksBetween(start, end);

		if (numberOfWeeksBetween < 0) {
			return null;
		}

		String[] dates = null;

		if (inclusive) {
			dates = new String[numberOfWeeksBetween == 1 ? 2 : numberOfWeeksBetween + 1];
			for (int i = 0; i < dates.length; i++) {
				dates[i] = format.format(addWeeks(start, i));
			}
		}
		else {
			dates = new String[numberOfWeeksBetween <= 0 ? 0 : numberOfWeeksBetween - 1];
			for (int i = 0; i < dates.length; i++) {
				dates[i] = format.format(addWeeks(start, i + 1));
			}
		}

		return dates;
	}

	public static final String[] getFormattedWeeksBetween(final Date start, final Date end, final boolean inclusive,
	        final Format format) {
		final int numberOfWeeksBetween = getNumberOfWeeksBetween(start, end);

		if (numberOfWeeksBetween < 0) {
			return null;
		}

		String[] dates = null;

		if (inclusive) {
			dates = new String[numberOfWeeksBetween == 1 ? 2 : numberOfWeeksBetween + 1];
			for (int i = 0; i < dates.length; i++) {
				dates[i] = format.format(addWeeks(start, i));
			}
		}
		else {
			dates = new String[numberOfWeeksBetween <= 0 ? 0 : numberOfWeeksBetween - 1];
			for (int i = 0; i < dates.length; i++) {
				dates[i] = format.format(addWeeks(start, i + 1));
			}
		}

		return dates;
	}

	public static final int getISOWeekOfYear(final Calendar c) {
		final Calendar cal = clone(c);
		cal.setMinimalDaysInFirstWeek(4);
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		return cal.get(Calendar.WEEK_OF_YEAR);
	}

	public static final int getISOWeekOfYear(final Date date) {
		return getISOWeekOfYear(toCalendar(date));
	}

	public static final int getWeekOfYear(final Calendar c) {
		final Calendar cal = clone(c);
		return cal.get(Calendar.WEEK_OF_YEAR);
	}

	public static final int getWeekOfYear(final Date date) {
		return getWeekOfYear(toCalendar(date));
	}

	// -------------------------------------------------------------------------------------------
	// Months
	// -------------------------------------------------------------------------------------------

	public static final int getNumberOfMonthsBetween(final Calendar start, final Calendar end) {
		final int startMonth = start.get(Calendar.MONTH) + 1;
		final int startYear = start.get(Calendar.YEAR);
		final int endMonth = end.get(Calendar.MONTH) + 1;
		final int endYear = end.get(Calendar.YEAR);

		// the following will work okay for Gregorian but will not
		// work correctly in a Calendar where the number of months
		// in a year is not constant
		return Math.round(((endYear - startYear) * (start.getMaximum(Calendar.MONTH) + 1)) + (endMonth - startMonth));
	}

	public static final int getNumberOfMonthsBetween(final Date start, final Date end) {
		return getNumberOfMonthsBetween(toCalendar(start), toCalendar(end));
	}

	public static final Calendar[] getMonthsBetween(final Calendar start, final Calendar end, final boolean inclusive) {
		final int numberOfMonthsBetween = getNumberOfMonthsBetween(start, end);

		if (numberOfMonthsBetween < 0) {
			return null;
		}

		Calendar[] dates = null;

		if (inclusive) {
			dates = new Calendar[numberOfMonthsBetween == 1 ? 2 : numberOfMonthsBetween + 1];
			for (int i = 0; i < dates.length; i++) {
				dates[i] = addMonths(start, i);
			}
		}
		else {
			dates = new Calendar[numberOfMonthsBetween <= 0 ? 0 : numberOfMonthsBetween - 1];
			for (int i = 0; i < dates.length; i++) {
				dates[i] = addMonths(start, i + 1);
			}
		}

		return dates;
	}

	public static final Date[] getMonthsBetween(final Date start, final Date end, final boolean inclusive) {
		final int numberOfMonthsBetween = getNumberOfMonthsBetween(start, end);

		if (numberOfMonthsBetween < 0) {
			return null;
		}

		Date[] dates = null;

		if (inclusive) {
			dates = new Date[numberOfMonthsBetween == 1 ? 2 : numberOfMonthsBetween + 1];
			for (int i = 0; i < dates.length; i++) {
				dates[i] = addMonths(start, i);
			}
		}
		else {
			dates = new Date[numberOfMonthsBetween <= 0 ? 0 : numberOfMonthsBetween - 1];
			for (int i = 0; i < dates.length; i++) {
				dates[i] = addMonths(start, i + 1);
			}
		}

		return dates;
	}

	public static final String[] getFormattedMonthsBetween(final Calendar start, final Calendar end,
	        final boolean inclusive, final Format format) {
		final int numberOfMonthsBetween = getNumberOfMonthsBetween(start, end);

		if (numberOfMonthsBetween < 0) {
			return null;
		}

		String[] dates = null;

		if (inclusive) {
			dates = new String[numberOfMonthsBetween == 1 ? 2 : numberOfMonthsBetween + 1];
			for (int i = 0; i < dates.length; i++) {
				dates[i] = format.format(addMonths(start, i));
			}
		}
		else {
			dates = new String[numberOfMonthsBetween <= 0 ? 0 : numberOfMonthsBetween - 1];
			for (int i = 0; i < dates.length; i++) {
				dates[i] = format.format(addMonths(start, i + 1));
			}
		}

		return dates;
	}

	public static final String[] getFormattedMonthsBetween(final Date start, final Date end, final boolean inclusive,
	        final Format format) {
		final int numberOfMonthsBetween = getNumberOfMonthsBetween(start, end);

		if (numberOfMonthsBetween < 0) {
			return null;
		}

		String[] dates = null;

		if (inclusive) {
			dates = new String[numberOfMonthsBetween == 1 ? 2 : numberOfMonthsBetween + 1];
			for (int i = 0; i < dates.length; i++) {
				dates[i] = format.format(addMonths(start, i));
			}
		}
		else {
			dates = new String[numberOfMonthsBetween <= 0 ? 0 : numberOfMonthsBetween - 1];
			for (int i = 0; i < dates.length; i++) {
				dates[i] = format.format(addMonths(start, i + 1));
			}
		}

		return dates;
	}

	// -------------------------------------------------------------------------------------------
	// Years
	// -------------------------------------------------------------------------------------------

	public static final int getNumberOfYearsBetween(final Calendar start, final Calendar end) {
		final int startYear = start.get(Calendar.YEAR);
		final int endYear = end.get(Calendar.YEAR);
		return Math.round(endYear - startYear);
	}

	public static final int getNumberOfYearsBetween(final Date start, final Date end) {
		return getNumberOfYearsBetween(toCalendar(start), toCalendar(end));
	}

	public static final Calendar[] getYearsBetween(final Calendar start, final Calendar end, final boolean inclusive) {
		final int numberOfYearsBetween = getNumberOfYearsBetween(start, end);

		if (numberOfYearsBetween < 0) {
			return null;
		}

		Calendar[] dates = null;

		if (inclusive) {
			dates = new Calendar[numberOfYearsBetween == 1 ? 2 : numberOfYearsBetween + 1];
			for (int i = 0; i < dates.length; i++) {
				dates[i] = addYears(start, i);
			}
		}
		else {
			dates = new Calendar[numberOfYearsBetween <= 0 ? 0 : numberOfYearsBetween - 1];
			for (int i = 0; i < dates.length; i++) {
				dates[i] = addYears(start, i + 1);
			}
		}

		return dates;
	}

	public static final Date[] getYearsBetween(final Date start, final Date end, final boolean inclusive) {
		final int numberOfYearsBetween = getNumberOfYearsBetween(start, end);

		if (numberOfYearsBetween < 0) {
			return null;
		}

		Date[] dates = null;

		if (inclusive) {
			dates = new Date[numberOfYearsBetween == 1 ? 2 : numberOfYearsBetween + 1];
			for (int i = 0; i < dates.length; i++) {
				dates[i] = addYears(start, i);
			}
		}
		else {
			dates = new Date[numberOfYearsBetween <= 0 ? 0 : numberOfYearsBetween - 1];
			for (int i = 0; i < dates.length; i++) {
				dates[i] = addYears(start, i + 1);
			}
		}

		return dates;
	}

	public static final String[] getFormattedYearsBetween(final Calendar start, final Calendar end,
	        final boolean inclusive, final Format format) {
		final int numberOfYearsBetween = getNumberOfYearsBetween(start, end);

		if (numberOfYearsBetween < 0) {
			return null;
		}

		String[] dates = null;

		if (inclusive) {
			dates = new String[numberOfYearsBetween == 1 ? 2 : numberOfYearsBetween + 1];
			for (int i = 0; i < dates.length; i++) {
				dates[i] = format.format(addYears(start, i));
			}
		}
		else {
			dates = new String[numberOfYearsBetween <= 0 ? 0 : numberOfYearsBetween - 1];
			for (int i = 0; i < dates.length; i++) {
				dates[i] = format.format(addYears(start, i + 1));
			}
		}

		return dates;
	}

	public static final String[] getFormattedYearsBetween(final Date start, final Date end, final boolean inclusive,
	        final Format format) {
		final int numberOfYearsBetween = getNumberOfYearsBetween(start, end);

		if (numberOfYearsBetween < 0) {
			return null;
		}

		String[] dates;
		if (inclusive) {
			dates = new String[numberOfYearsBetween == 1 ? 2 : numberOfYearsBetween + 1];
			for (int i = 0; i < dates.length; i++) {
				dates[i] = format.format(addYears(start, i));
			}
		}
		else {
			dates = new String[numberOfYearsBetween <= 0 ? 0 : numberOfYearsBetween - 1];
			for (int i = 0; i < dates.length; i++) {
				dates[i] = format.format(addYears(start, i + 1));
			}
		}

		return dates;
	}

	// -------------------------------------------------------------------------------------------
	// Quarters
	// -------------------------------------------------------------------------------------------

	/**
	 * @return a positive non-zero integer representing the quarter which the
	 *         given date belongs to. If the given date is greater than
	 *         startOfFiscalYear + 1 year, then the quarter will be incremented
	 *         past the 4th quarter (e.g. 5th, 6th, 7th, etc.) until the correct
	 *         Nth quarter is reached. The integer returned may be Mod by 4 to
	 *         keep it within the typical four quarters range.
	 */
	public static final int getQuarter(final Calendar date, final Calendar startOfFiscalYear) {
		return getQuarter(date.getTime(), startOfFiscalYear.getTime());
	}

	/**
	 * @return a positive non-zero integer representing the quarter which the
	 *         given date belongs to. If the given date is greater than
	 *         startOfFiscalYear + 1 year, then the quarter will be incremented
	 *         past the 4th quarter (e.g. 5th, 6th, 7th, etc.) until the correct
	 *         Nth quarter is reached. The integer returned may be Mod by 4 to
	 *         keep it within the typical four quarters range.
	 */
	public static final int getQuarter(final Date date, final Date startOfFiscalYear) {
		final Date newDate = zeroOutTime(date);
		int myQuarter = 0;
		Date endOfQuarter = zeroOutTime(startOfFiscalYear);
		do {
			++myQuarter;
			endOfQuarter = addMonths(endOfQuarter, 3);
			if (newDate.compareTo(addDays(endOfQuarter, -1)) <= 0) {
				return myQuarter;
			}
		}
		while (true);
	}

	public static final Calendar[] getQuarterStartDates(final Calendar startOfFiscalYear) {
		final Calendar[] startDates = new Calendar[4];
		startDates[0] = startOfFiscalYear;
		startDates[1] = addMonths(startOfFiscalYear, 3);
		startDates[2] = addMonths(startOfFiscalYear, 6);
		startDates[3] = addMonths(startOfFiscalYear, 9);
		return startDates;
	}

	public static final Date[] getQuarterStartDates(final Date startOfFiscalYear) {
		final Date[] startDates = new Date[4];
		startDates[0] = startOfFiscalYear;
		startDates[1] = addMonths(startOfFiscalYear, 3);
		startDates[2] = addMonths(startOfFiscalYear, 6);
		startDates[3] = addMonths(startOfFiscalYear, 9);
		return startDates;
	}

	public static final Calendar[] getQuarterEndDates(final Calendar startOfFiscalYear) {
		final Calendar[] startDates = new Calendar[4];
		startDates[0] = addDays(addMonths(startOfFiscalYear, 3), -1);
		startDates[1] = addDays(addMonths(startOfFiscalYear, 6), -1);
		startDates[2] = addDays(addMonths(startOfFiscalYear, 9), -1);
		startDates[3] = addDays(addMonths(startOfFiscalYear, 12), -1);
		return startDates;
	}

	public static final Date[] getQuarterEndDates(final Date startOfFiscalYear) {
		final Date[] startDates = new Date[4];
		startDates[0] = addDays(addMonths(startOfFiscalYear, 3), -1);
		startDates[1] = addDays(addMonths(startOfFiscalYear, 6), -1);
		startDates[2] = addDays(addMonths(startOfFiscalYear, 9), -1);
		startDates[3] = addDays(addMonths(startOfFiscalYear, 12), -1);
		return startDates;
	}

	// -------------------------------------------------------------------------------------------
	// Time Zones
	// -------------------------------------------------------------------------------------------

	/**
	 *
	 * @return - the offset in milliseconds
	 */
	public static final int getOffset(final Date date, final String timeZone) {
		return TimeZone.getTimeZone(timeZone).getOffset(date.getTime());
	}

	public static final int getOffsetInHoursBetweenTimeZones(final Date date, final TimeZone timeZone1,
	        final TimeZone timeZone2) {
		return (int) (Math.round(timeZone1.getOffset(date.getTime()) / Constants.ONE_HOUR)
		        - (timeZone2.getOffset(date.getTime()) / Constants.ONE_HOUR));
	}

	public static final int getOffsetInHoursBetweenTimeZones(final Date date, final String timeZone1,
	        final String timeZone2) {
		return (int) (Math.round(TimeZone.getTimeZone(timeZone1).getOffset(date.getTime()) / Constants.ONE_HOUR)
		        - (TimeZone.getTimeZone(timeZone2).getOffset(date.getTime()) / Constants.ONE_HOUR));
	}

	/**
	 *
	 * @deprecated - DO NOT USE, may provide incorrect results
	 * @return - the offset in hours between two time zones and the given dates
	 *
	 */
	@Deprecated
	public static final int getOffsetInHoursBetweenTimeZones(final Date timeZone1Date, final TimeZone timeZone1,
	        final Date timeZone2Date, final TimeZone timeZone2) {
		return (int) (Math.round(timeZone1.getOffset(timeZone1Date.getTime()) / Constants.ONE_HOUR)
		        - (timeZone2.getOffset(timeZone2Date.getTime()) / Constants.ONE_HOUR));
	}

	/**
	 *
	 * @deprecated - DO NOT USE, may provide incorrect results
	 *
	 */
	@Deprecated
	public static final int getOffsetInHoursBetweenTimeZones(final Date timeZone1Date, final String timeZone1,
	        final Date timeZone2Date, final String timeZone2) {
		return getOffsetInHoursBetweenTimeZones(timeZone1Date, TimeZone.getTimeZone(timeZone1), timeZone2Date,
		        TimeZone.getTimeZone(timeZone2));
	}

	/**
	 *
	 * @return - the offset in hours between two timezones
	 * @deprecated - This method uses the current system date in the
	 *             calculation, which may lead to incorrect offsets.
	 *
	 */
	@Deprecated
	public static final int getOffsetInHoursBetweenTimeZones(final String timeZone1, final String timeZone2) {
		return (int) (Math.round(TimeZone.getTimeZone(timeZone1).getOffset(new Date().getTime()) / Constants.ONE_HOUR)
		        - (TimeZone.getTimeZone(timeZone2).getOffset(new Date().getTime()) / Constants.ONE_HOUR));
	}

	/**
	 *
	 * @return - the offset in hours between two timezones
	 * @deprecated - This method uses the current system date in the
	 *             calculation, which may lead to incorrect offsets.
	 *
	 */
	@Deprecated
	public static final int getOffsetInHoursBetweenTimeZones(final TimeZone timeZone1, final String timeZone2) {
		return (int) (Math.round(timeZone1.getOffset(new Date().getTime()) / Constants.ONE_HOUR)
		        - (TimeZone.getTimeZone(timeZone2).getOffset(new Date().getTime()) / Constants.ONE_HOUR));
	}

	/**
	 *
	 * @return - the offset in hours between two timezones
	 * @deprecated - This method uses the current system date in the
	 *             calculation, which may lead to incorrect offsets.
	 *
	 */
	@Deprecated
	public static final int getOffsetInHoursBetweenTimeZones(final String timeZone1, final TimeZone timeZone2) {
		return (int) (Math.round(TimeZone.getTimeZone(timeZone1).getOffset(new Date().getTime()) / Constants.ONE_HOUR)
		        - (timeZone2.getOffset(new Date().getTime()) / Constants.ONE_HOUR));
	}

	/**
	 *
	 * @return - the offset in hours between two timezones
	 * @deprecated - This method uses the current system date in the
	 *             calculation, which may lead to incorrect offsets.
	 *
	 */
	@Deprecated
	public static final int getOffsetInHoursBetweenTimeZones(final TimeZone timeZone1, final TimeZone timeZone2) {
		return (int) (Math.round(timeZone1.getOffset(new Date().getTime()) / Constants.ONE_HOUR)
		        - (timeZone2.getOffset(new Date().getTime()) / Constants.ONE_HOUR));
	}

	// -------------------------------------------------------------------------------------------
	// Add/Subtract
	// -------------------------------------------------------------------------------------------

	/**
	 * Add seconds to the given date
	 *
	 * @param date
	 *            - the date
	 * @param x
	 *            - the number of seconds to add. Use a negative number to
	 *            subtract.
	 * @return a new date
	 */
	public static final Date addSeconds(final Date date, final int x) {
		return add(date, Calendar.SECOND, x);
	}

	/**
	 * Add seconds to the given calendar
	 *
	 * @param cal
	 *            - the calendar
	 * @param x
	 *            - the number of seconds to add. Use a negative number to
	 *            subtract.
	 * @return a new calendar
	 */
	public static final Calendar addSeconds(final Calendar cal, final int x) {
		return add(cal, Calendar.SECOND, x);
	}

	/**
	 * Add minutes to the given date
	 *
	 * @param date
	 *            - the date
	 * @param x
	 *            - the number of minutes to add. Use a negative number to
	 *            subtract.
	 * @return a new date
	 */
	public static final Date addMinutes(final Date date, final int x) {
		return add(date, Calendar.MINUTE, x);
	}

	/**
	 * Add minutes to the given calendar
	 *
	 * @param cal
	 *            - the calendar
	 * @param x
	 *            - the number of minutes to add. Use a negative number to
	 *            subtract.
	 * @return a new calendar
	 */
	public static final Calendar addMinutes(final Calendar cal, final int x) {
		return add(cal, Calendar.MINUTE, x);
	}

	/**
	 * Add hours to the given date
	 *
	 * @param date
	 *            - the date
	 * @param x
	 *            - the number of hours to add. Use a negative number to
	 *            subtract.
	 * @return a new date
	 */
	public static final Date addHours(final Date date, final int x) {
		return add(date, Calendar.HOUR_OF_DAY, x);
	}

	/**
	 * Add hours to the given calendar
	 *
	 * @param cal
	 *            - the calendar
	 * @param x
	 *            - the number of hours to add. Use a negative number to
	 *            subtract.
	 * @return a new calendar
	 */
	public static final Calendar addHours(final Calendar cal, final int x) {
		return add(cal, Calendar.HOUR_OF_DAY, x);
	}

	/**
	 * Add days to the given date
	 *
	 * @param date
	 *            - the date
	 * @param x
	 *            - the number of hours to add. Use a negative number to
	 *            subtract.
	 * @return a new date
	 */
	public static final Date addDays(final Date date, final int x) {
		return add(date, Calendar.DAY_OF_MONTH, x);
	}

	/**
	 * Add days to the given calendar
	 *
	 * @param cal
	 *            - the calendar
	 * @param x
	 *            - the number of hours to add. Use a negative number to
	 *            subtract.
	 * @return a new calendar
	 */
	public static final Calendar addDays(final Calendar cal, final int x) {
		return add(cal, Calendar.DAY_OF_MONTH, x);
	}

	/**
	 * Add weeks to the given date
	 *
	 * @param date
	 *            - the date
	 * @param x
	 *            - the number of hours to add. Use a negative number to
	 *            subtract.
	 * @return a new date
	 */
	public static final Date addWeeks(final Date date, final int x) {
		return add(date, Calendar.WEEK_OF_YEAR, x);
	}

	/**
	 * Add weeks to the given calendar
	 *
	 * @param cal
	 *            - the calendar
	 * @param x
	 *            - the number of weeks to add. Use a negative number to
	 *            subtract.
	 * @return a new calendar
	 */
	public static final Calendar addWeeks(final Calendar cal, final int x) {
		return add(cal, Calendar.WEEK_OF_YEAR, x);
	}

	/**
	 * Add months to the given date
	 *
	 * @param date
	 *            - the date
	 * @param x
	 *            - the number of months to add. Use a negative number to
	 *            subtract.
	 * @return a new date
	 */
	public static final Date addMonths(final Date date, final int x) {
		return add(date, Calendar.MONTH, x);
	}

	/**
	 * Add months to the given calendar
	 *
	 * @param cal
	 *            - the calendar
	 * @param x
	 *            - the number of months to add. Use a negative number to
	 *            subtract.
	 * @return a new calendar
	 */
	public static final Calendar addMonths(final Calendar cal, final int x) {
		return add(cal, Calendar.MONTH, x);
	}

	/**
	 * Add years to the given date
	 *
	 * @param date
	 *            - the date
	 * @param x
	 *            - the number of years to add. Use a negative number to
	 *            subtract.
	 * @return a new date
	 */
	public static final Date addYears(final Date date, final int x) {
		return add(date, Calendar.YEAR, x);
	}

	/**
	 * Add years to the given calendar
	 *
	 * @param cal
	 *            - the calendar
	 * @param x
	 *            - the number of years to add. Use a negative number to
	 *            subtract.
	 * @return a new calendar
	 */
	public static final Calendar addYears(final Calendar cal, final int x) {
		return add(cal, Calendar.YEAR, x);
	}

	private static final Date add(final Date date, final int field, final int numberToAdd) {
		if (date == null) {
			throw new IllegalArgumentException("Date is null");
		}

		final Calendar cal = toCalendar(date);
		cal.add(field, numberToAdd);
		return cal.getTime();
	}

	private static final Calendar add(final Calendar c, final int field, final int numberToAdd) {
		if (c == null) {
			throw new IllegalArgumentException("Calendar is null");
		}
		final Calendar cal = clone(c);
		cal.add(field, numberToAdd);
		return cal;
	}

	/**
	 *
	 * Subtracts two dates and returns the time difference in milliseconds.
	 *
	 */
	public static final long subtract(final Calendar x1, final Calendar x2, final boolean includeTimeInCalculation) {
		Calendar cal1 = null;
		Calendar cal2 = null;

		if (includeTimeInCalculation) {
			cal1 = x1;
			cal2 = x2;
		}
		else {
			cal1 = Calendar.getInstance();
			cal1.clear();
			cal1.set(x1.get(Calendar.YEAR), x1.get(Calendar.MONTH), x1.get(Calendar.DAY_OF_MONTH));

			cal2 = Calendar.getInstance();
			cal2.clear();
			cal2.set(x2.get(Calendar.YEAR), x2.get(Calendar.MONTH), x2.get(Calendar.DAY_OF_MONTH));
		}

		// Convert both dates to milliseconds
		final long cal1_ms = cal1.getTimeInMillis();
		final long cal2_ms = cal2.getTimeInMillis();

		return cal1_ms - cal2_ms;
	}

	public static final long subtract(final Date x1, final Date x2, final boolean includeTimeInCalculation) {
		return subtract(toCalendar(x1), toCalendar(x2), includeTimeInCalculation);
	}
}