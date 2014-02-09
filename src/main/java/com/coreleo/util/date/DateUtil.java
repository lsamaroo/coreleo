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

public final class DateUtil
{

	
	/** MM/dd/yyyy - Only use this to format a date, parsing is not supported */
	public final static FastDateFormat MMddyyyyForwardSlash = FastDateFormat.getInstance("MM/dd/yyyy");
	
	/** ww - Only use this to format a date, parsing is not supported */
	public final static FastDateFormat ww = FastDateFormat.getInstance("ww");

	/** yyyy - Only use this to format a date, parsing is not supported */
	public final static FastDateFormat yyyy = FastDateFormat.getInstance("yyyy");

	/** yyyy-MM-dd - Only use this to format a date, parsing is not supported */
	public final static FastDateFormat yyyyMMddHyphened = FastDateFormat.getInstance("yyyy-MM-dd");

	/** MM-yyyy - Only use this to format a date, parsing is not supported */
	public final static FastDateFormat MMyyyyHyphened = FastDateFormat.getInstance("MM-yyyy");

	/** ww-yyyy - Only use this to format a date, parsing is not supported */
	public final static FastDateFormat wwyyyyHyphened = FastDateFormat.getInstance("ww-yyyy");

	/** MM-dd-yyyy - Only use this to format a date, parsing is not supported */
	public final static FastDateFormat MMddyyyyHyphened = FastDateFormat.getInstance("MM-dd-yyyy");

	/** MM-dd-yy - Only use this to format a date, parsing is not supported */
	public final static FastDateFormat MMddyyHyphened = FastDateFormat.getInstance("MM-dd-yy");

	/** yyyy-MM-dd HH:mm:ss - Only use this to format a date, parsing is not supported */
	public final static FastDateFormat yyyyMMddHyphened_HHmmssColon = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss");

	/** MM/dd/yyyy HH:mm:ss - Only use this to format a date, parsing is not supported */
	public final static FastDateFormat MMddyyyyForwardSlash_HHmmssColon = FastDateFormat.getInstance("MM/dd/yyyy HH:mm:ss");

	/** MM-dd-yyyy HH:mm:ss - Only use this to format a date, parsing is not supported */
	public final static FastDateFormat MMddyyyyHyphened_HHmmssColon = FastDateFormat.getInstance("MM-dd-yyyy HH:mm:ss");

	/** MMM dd, yyyy */
	public final static FastDateFormat MMM_dd_Coma_yyyy = FastDateFormat.getInstance("MMM dd, yyyy");

	/** hh:mm aa - Only use this to format a date, parsing is not supported */
	public final static FastDateFormat hhmmColon_aa = FastDateFormat.getInstance("hh:mm aa");

	/** h:mm aa - Only use this to format a date, parsing is not supported */
	public final static FastDateFormat hmmColon_aa = FastDateFormat.getInstance("h:mm aa");

	
	/** yyyy-MM-dd HH:mm aa */
	public final static FastDateFormat yyyyMMddHyphened_hhmmColon_aa = FastDateFormat.getInstance("yyyy-MM-dd hh:mm aa");


	public final static FastDateFormat ISO8601DateTimeFormat = FastDateFormat.getInstance("yyyy-MM-dd'T'HH:mmZ");

	public final static FastDateFormat yyyyMMddHyphened_T_HHmmssColon = FastDateFormat.getInstance("yyyy-MM-dd'T'HH:mm:ss");

	
	private DateUtil()
	{
	}
	
	public static int compare( Date x1, Date x2 ){
		if (x1 == null && x2 == null)
		{
			return 0;
		}

		if (x1 == null)
		{
			return -1;
		}

		if (x2 == null)
		{
			return 1;
		}
		
		return x1.compareTo(x2);
	}
	
	public static Calendar utcTo( Date date, TimeZone tz ){
		Calendar c = Calendar.getInstance( TimeZone.getTimeZone("UTC") );
		c.setTime(date);	
		c.setTimeZone(tz);
		return c;
	}
	
	public static long getUnixTimestamp( Date date ){
		return (date.getTime() / 1000L );
	}
	

	public final static Calendar clone(Calendar c)
	{
		return (Calendar) c.clone();
	}

	/**
	 * sets minimal day in first week to 4 sets first day of week to Monday
	 * 
	 * @return a new Calendar object
	 */
	public final static Calendar toISO(Calendar c)
	{
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
	public final static Calendar toISO(Date date)
	{
		final Calendar cal = toCalendar(date);
		cal.setMinimalDaysInFirstWeek(4);
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		return cal;
	}

	

	public static String format( Calendar x, String pattern ){
		return FastDateFormat.getInstance(pattern, x.getTimeZone()).format(x);
	}
	
	
	public static Date parse(String date, String pattern)
	{
		try
		{
			return new SimpleDateFormat(pattern).parse(date);
		}
		catch (final ParseException e)
		{
			throw new SimpleException(e);
		}
	}
	
	
	public static Date parse(String date, String pattern, TimeZone tz)
	{
		try
		{
			SimpleDateFormat sf = new SimpleDateFormat(pattern);
			sf.setTimeZone(tz);
			return sf.parse(date);
		}
		catch (final ParseException e)
		{
			throw new SimpleException(e);
		}
	}

	/**
     * 
     */
	public static Date parse(DateFormat format, String date)
	{
		try
		{
			return format.parse(date);
		}
		catch (final ParseException e)
		{
			return null;
		}
	}

	public static Timestamp toTimestamp(Calendar cal)
	{
		if (cal == null)
		{
			throw new IllegalArgumentException("Calendar is null");
		}

		return new Timestamp(cal.getTime().getTime());
	}

	public static Timestamp toTimestamp(Date date)
	{
		if (date == null)
		{
			throw new IllegalArgumentException("Date is null");
		}

		return new Timestamp(date.getTime());
	}

	public static final Calendar yesterday()
	{
		return addDays(Calendar.getInstance(), -1);
	}

	public static final Calendar tomorrow()
	{
		return addDays(Calendar.getInstance(), 1);
	}

	public static final Calendar today()
	{
		return Calendar.getInstance();
	}

	public static Calendar toCalendar(Date date)
	{
		if (date == null)
		{
			throw new IllegalArgumentException("Date is null");
		}
		final Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal;
	}

	/**
	 * 
	 * @return a new calendar
	 */
	public static Calendar setTime(Calendar c, int hour, int minute, int second, int millisecond)
	{
		final Calendar cal = clone(c);
		cal.set(Calendar.HOUR_OF_DAY, hour);
		cal.set(Calendar.MINUTE, minute);
		cal.set(Calendar.SECOND, second);
		cal.set(Calendar.MILLISECOND, millisecond);
		return cal;
	}

	public static Date setTime(Date date, int hour, int minute, int second, int millisecond)
	{
		if( date == null ){
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
	public static Date zeroOutTime(Date date)
	{
		return setTime(date, 0, 0, 0, 0);
	}

	public static Calendar zeroOutTime(Calendar cal)
	{
		return setTime(cal, 0, 0, 0, 0);
	}

	public static Calendar toCalendar(Date date, int hourOfDay, int minute, int second, int millisecond)
	{
		final Calendar cal = toCalendar(date);
		cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
		cal.set(Calendar.MINUTE, minute);
		cal.set(Calendar.SECOND, second);
		cal.set(Calendar.MILLISECOND, millisecond);
		return cal;
	}

	public static Calendar toCalendar(Date date, String hourOfDay, String minute, String second)
	{
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
	public static Calendar toCalendar(int month, int day)
	{
		final Calendar cal = Calendar.getInstance();
		cal.set(Calendar.MONTH, month);
		cal.set(Calendar.DAY_OF_MONTH, day);
		return cal;
	}

	/**
	 * 
	 * @return a lenient Calendar set to the given values
	 */
	public static Calendar toCalendar(String month, String day)
	{
		final Calendar cal = Calendar.getInstance();
		cal.set(Calendar.MONTH, Integer.parseInt(month));
		cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(day));
		return cal;
	}

	/**
	 * 
	 * @return a lenient Calendar set to the given values
	 */
	public static Calendar toCalendar(int year, int month, int day)
	{
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
	public static Calendar toCalendar(String year, String month, String day)
	{
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
	public static Calendar toCalendar(String year, String month, String day, String hourOfDay, String minute, String second)
	{
		final Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, Integer.parseInt(year));
		cal.set(Calendar.MONTH, Integer.parseInt(month));
		cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(day));
		cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hourOfDay));
		cal.set(Calendar.MINUTE, Integer.parseInt(minute));
		cal.set(Calendar.SECOND, Integer.parseInt(second));
		return cal;
	}

	public static Calendar toCalendar(String year, String month, String day, String hourOfDay, String minute, String second, String millisecond)
	{
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
	public static Calendar toCalendar(int year, int month, int day, int hourOfDay, int minute, int second)
	{
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
	public static Calendar toCalendar(int year, int month, int day, int hourOfDay, int minute, int second, int millisecond)
	{
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

	public static Date toDate(Calendar cal)
	{
		return cal.getTime();
	}

	public static Date toDate(int month, int day)
	{
		return toCalendar(month, day).getTime();
	}

	public static Date toDate(String month, String day)
	{
		return toCalendar(month, day).getTime();
	}

	public static Date toDate(int year, int month, int day)
	{
		return toCalendar(year, month, day).getTime();
	}

	public static Date toDate(String year, String month, String day)
	{
		return toCalendar(year, month, day).getTime();
	}

	public static Date toDate(int year, int month, int day, int hourOfDay, int minute, int second)
	{
		return toCalendar(year, month, day, hourOfDay, minute, second).getTime();
	}

	public static Date toDate(String year, String month, String day, String hourOfDay, String minute, String second)
	{
		return toCalendar(year, month, day, hourOfDay, minute, second).getTime();
	}

	public static Date toDate(int year, int month, int day, int hourOfDay, int minute, int second, int millisecond)
	{
		return toCalendar(year, month, day, hourOfDay, minute, second, millisecond).getTime();
	}

	public static Date toDate(String year, String month, String day, String hourOfDay, String minute, String second, String millisecond)
	{
		return toCalendar(year, month, day, hourOfDay, minute, second, millisecond).getTime();
	}

	public static final boolean isValidDate(int month, int day, int year)
	{
		try
		{
			final Calendar cal = Calendar.getInstance();
			cal.setLenient(false); // must do this
			cal.set(Calendar.YEAR, year);
			cal.set(Calendar.MONTH, month);
			cal.set(Calendar.DAY_OF_MONTH, day);
			cal.getTime(); // exception thrown here
		}
		catch (final Exception e)
		{
			return false;
		}

		return true;
	}

	public static final boolean isValidDate(String month, String day, String year)
	{
		try
		{
			return isValidDate(Integer.parseInt(month), Integer.parseInt(day), Integer.parseInt(year));
		}
		catch (final Exception e)
		{
			return false;
		}
	}

	public static final int getDay(Calendar cal)
	{
		return cal.get(Calendar.DAY_OF_MONTH);
	}

	public static final int getDay(Date date)
	{
		final Calendar cal = toCalendar(date);
		return cal.get(Calendar.DAY_OF_MONTH);
	}

	public static int getYear(Date date)
	{
		final Calendar cal = toCalendar(date);
		return cal.get(Calendar.YEAR);
	}

	public static int getYear(Calendar cal)
	{
		return cal.get(Calendar.YEAR);
	}

	public static final int getMonth(Date date)
	{
		final Calendar cal = toCalendar(date);
		return cal.get(Calendar.MONTH);
	}

	public static final int getMonth(Calendar cal)
	{
		return cal.get(Calendar.MONTH);
	}

	public static final int getHourOfDay(Date date)
	{
		final Calendar cal = toCalendar(date);
		return cal.get(Calendar.HOUR_OF_DAY);
	}

	public static final int getHourOfDay(Calendar cal)
	{
		return cal.get(Calendar.HOUR_OF_DAY);
	}

	public static final int getMinute(Date date)
	{
		final Calendar cal = toCalendar(date);
		return cal.get(Calendar.MINUTE);
	}

	public static final int getMinute(Calendar cal)
	{
		return cal.get(Calendar.MINUTE);
	}

	public static final int getSecond(Date date)
	{
		final Calendar cal = toCalendar(date);
		return cal.get(Calendar.SECOND);
	}

	public static final int getSecond(Calendar cal)
	{
		return cal.get(Calendar.SECOND);
	}

	public final static Date set(Date date, int field, int value)
	{
		final Calendar c = toCalendar(date);
		c.set(field, value);
		return c.getTime();
	}

	public final static Calendar set(Calendar c, int field, int value)
	{
		if (c == null)
		{
			throw new IllegalArgumentException("Calendar is null");
		}

		final Calendar cal = clone(c);
		cal.set(field, value);
		return cal;
	}

	public final static int getActualMaximum(Date date, int field)
	{
		final Calendar c = toCalendar(date);
		return c.getActualMaximum(field);
	}

	public final static int getActualMinimum(Date date, int field)
	{
		final Calendar c = toCalendar(date);
		return c.getActualMinimum(field);
	}

	public final static Date setToLastDayOfMonth(Date date)
	{
		final Calendar c = toCalendar(date);
		c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
		return c.getTime();
	}

	public final static Calendar setToLastDayOfMonth(Calendar c)
	{
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
	public static final int getNumberOfDaysBetween(Calendar start, Calendar end, boolean includeTimeInCalculation)
	{
		Calendar cal1 = null;
		Calendar cal2 = null;

		if (includeTimeInCalculation)
		{
			cal1 = start;
			cal2 = end;
		}
		else
		{
			cal1 = Calendar.getInstance();
			cal1.clear();
			cal1.set(start.get(Calendar.YEAR), start.get(Calendar.MONTH), start.get(Calendar.DAY_OF_MONTH));

			cal2 = Calendar.getInstance();
			cal2.clear();
			cal2.set(end.get(Calendar.YEAR), end.get(Calendar.MONTH), end.get(Calendar.DAY_OF_MONTH));
		}

		// Convert both dates to milliseconds
		final long cal1_ms = cal1.getTimeInMillis();
		final long cal2_ms = cal2.getTimeInMillis();

		// Convert back to days and return
		return (int) Math.round(((cal2_ms - cal1_ms) / Constants.ONE_DAY));
	}

	/**
	 * 
	 * Returns the number of days between two dates, excluding the end date.
	 * 
	 */
	public static final int getNumberOfDaysBetween(Date start, Date end, boolean includeTimeInCalculation)
	{
		final Calendar cal1 = toCalendar(start);
		final Calendar cal2 = toCalendar(end);
		return getNumberOfDaysBetween(cal1, cal2, includeTimeInCalculation);
	}

	/**
	 * 
	 * @deprecated - use getDaysBetween instead
	 */
	@Deprecated
	public static Date[] getDailyDatesBetween(Date start, Date end, boolean inclusive)
	{
		return getDaysBetween(start, end, inclusive);
	}

	public static Calendar[] getDaysBetween(Calendar start, Calendar end, boolean includeEndDate)
	{
		final int numberOfDaysBetween = getNumberOfDaysBetween(start, end, false);

		if (numberOfDaysBetween < 0)
		{
			return null;
		}

		Calendar[] dates = null;

		if (includeEndDate)
		{
			dates = new Calendar[numberOfDaysBetween == 1 ? 2 : numberOfDaysBetween + 1];
			for (int i = 0; i < dates.length; i++)
			{
				dates[i] = addDays(start, i);
			}
		}
		else
		{
			dates = new Calendar[numberOfDaysBetween < 1 ? 0 : numberOfDaysBetween - 1];
			for (int i = 0; i < dates.length; i++)
			{
				dates[i] = addDays(start, i + 1);
			}
		}

		return dates;
	}

	public static Date[] getDaysBetween(Date start, Date end, boolean includeEndDate)
	{
		final int numberOfDaysBetween = getNumberOfDaysBetween(start, end, false);

		if (numberOfDaysBetween < 0)
		{
			return null;
		}

		Date[] dates = null;

		if (includeEndDate)
		{
			dates = new Date[numberOfDaysBetween == 1 ? 2 : numberOfDaysBetween + 1];
			for (int i = 0; i < dates.length; i++)
			{
				dates[i] = addDays(start, i);
			}
		}
		else
		{
			dates = new Date[numberOfDaysBetween < 1 ? 0 : numberOfDaysBetween - 1];
			for (int i = 0; i < dates.length; i++)
			{
				dates[i] = addDays(start, i + 1);
			}
		}

		return dates;
	}

	public static String[] getFormattedDaysBetween(Calendar start, Calendar end, boolean inclusive, Format format)
	{
		final int numberOfDaysBetween = getNumberOfDaysBetween(start, end, false);

		if (numberOfDaysBetween < 0)
		{
			return null;
		}

		String[] dates = null;

		if (inclusive)
		{
			dates = new String[numberOfDaysBetween == 1 ? 2 : numberOfDaysBetween + 1];
			for (int i = 0; i < dates.length; i++)
			{
				dates[i] = format.format(addDays(start, i));
			}
		}
		else
		{
			dates = new String[numberOfDaysBetween < 1 ? 0 : numberOfDaysBetween - 1];
			for (int i = 0; i < dates.length; i++)
			{
				dates[i] = format.format(addDays(start, i + 1));
			}
		}

		return dates;
	}

	/**
	 * @return a String array with dates formatted with the given date format
	 */
	public static String[] getFormattedDaysBetween(Date start, Date end, boolean inclusive, Format format)
	{
		final int numberOfDaysBetween = getNumberOfDaysBetween(start, end, false);

		if (numberOfDaysBetween < 0)
		{
			return null;
		}

		String[] dates = null;

		if (inclusive)
		{
			dates = new String[numberOfDaysBetween == 1 ? 2 : numberOfDaysBetween + 1];
			for (int i = 0; i < dates.length; i++)
			{
				dates[i] = format.format(addDays(start, i));
			}
		}
		else
		{
			dates = new String[numberOfDaysBetween < 1 ? 0 : numberOfDaysBetween - 1];
			for (int i = 0; i < dates.length; i++)
			{
				dates[i] = format.format(addDays(start, i + 1));
			}
		}

		return dates;
	}

	// -------------------------------------------------------------------------------------------
	// Week
	// -------------------------------------------------------------------------------------------

	public final static Calendar changeDayOfWeek(Calendar theWeek, int newDayOfWeek)
	{
		final Calendar cal = clone(theWeek);
		cal.set(Calendar.DAY_OF_WEEK, newDayOfWeek);
		return cal;
	}

	public final static Date changeDayOfWeek(Date theWeek, int newDayOfWeek)
	{
		final Calendar cal = toCalendar(theWeek);
		cal.set(Calendar.DAY_OF_WEEK, newDayOfWeek);
		return cal.getTime();
	}

	/**
	 * 
	 * Returns the number of weeks between two dates, not including the start or end .
	 * 
	 */
	public final static int getNumberOfWeeksBetween(Calendar start, Calendar end)
	{
		final int weekOffset = (end.get(Calendar.DAY_OF_WEEK) - start.get(Calendar.DAY_OF_WEEK)) < 0 ? 1 : 0;
		final int numberOfDaysBetween = getNumberOfDaysBetween(start, end, false);

		final int numberOfWeeksBetween = numberOfDaysBetween / 7;
		return Math.round(numberOfWeeksBetween + weekOffset);
	}

	public final static int getNumberOfWeeksBetween(Date start, Date end)
	{
		return getNumberOfWeeksBetween(toCalendar(start), toCalendar(end));
	}

	public final static Calendar[] getWeeksBetween(Calendar start, Calendar end, boolean inclusive)
	{
		final int numberOfWeeksBetween = getNumberOfWeeksBetween(start, end);

		if (numberOfWeeksBetween < 0)
		{
			return null;
		}

		Calendar[] dates = null;

		if (inclusive)
		{
			dates = new Calendar[numberOfWeeksBetween == 1 ? 2 : numberOfWeeksBetween + 1];
			for (int i = 0; i < dates.length; i++)
			{
				dates[i] = addWeeks(start, i);
			}
		}
		else
		{
			dates = new Calendar[numberOfWeeksBetween <= 0 ? 0 : numberOfWeeksBetween - 1];
			for (int i = 0; i < dates.length; i++)
			{
				dates[i] = addWeeks(start, i + 1);
			}
		}

		return dates;
	}

	public final static Date[] getWeeksBetween(Date start, Date end, boolean inclusive)
	{
		final int numberOfWeeksBetween = getNumberOfWeeksBetween(start, end);

		if (numberOfWeeksBetween < 0)
		{
			return null;
		}

		Date[] dates = null;

		if (inclusive)
		{
			dates = new Date[numberOfWeeksBetween == 1 ? 2 : numberOfWeeksBetween + 1];
			for (int i = 0; i < dates.length; i++)
			{
				dates[i] = addWeeks(start, i);
			}
		}
		else
		{
			dates = new Date[numberOfWeeksBetween <= 0 ? 0 : numberOfWeeksBetween - 1];
			for (int i = 0; i < dates.length; i++)
			{
				dates[i] = addWeeks(start, i + 1);
			}
		}

		return dates;
	}

	public final static String[] getFormattedWeeksBetween(Calendar start, Calendar end, boolean inclusive, Format format)
	{
		final int numberOfWeeksBetween = getNumberOfWeeksBetween(start, end);

		if (numberOfWeeksBetween < 0)
		{
			return null;
		}

		String[] dates = null;

		if (inclusive)
		{
			dates = new String[numberOfWeeksBetween == 1 ? 2 : numberOfWeeksBetween + 1];
			for (int i = 0; i < dates.length; i++)
			{
				dates[i] = format.format(addWeeks(start, i));
			}
		}
		else
		{
			dates = new String[numberOfWeeksBetween <= 0 ? 0 : numberOfWeeksBetween - 1];
			for (int i = 0; i < dates.length; i++)
			{
				dates[i] = format.format(addWeeks(start, i + 1));
			}
		}

		return dates;
	}

	public final static String[] getFormattedWeeksBetween(Date start, Date end, boolean inclusive, Format format)
	{
		final int numberOfWeeksBetween = getNumberOfWeeksBetween(start, end);

		if (numberOfWeeksBetween < 0)
		{
			return null;
		}

		String[] dates = null;

		if (inclusive)
		{
			dates = new String[numberOfWeeksBetween == 1 ? 2 : numberOfWeeksBetween + 1];
			for (int i = 0; i < dates.length; i++)
			{
				dates[i] = format.format(addWeeks(start, i));
			}
		}
		else
		{
			dates = new String[numberOfWeeksBetween <= 0 ? 0 : numberOfWeeksBetween - 1];
			for (int i = 0; i < dates.length; i++)
			{
				dates[i] = format.format(addWeeks(start, i + 1));
			}
		}

		return dates;
	}

	public final static int getISOWeekOfYear(Calendar c)
	{
		final Calendar cal = clone(c);
		cal.setMinimalDaysInFirstWeek(4);
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		return cal.get(Calendar.WEEK_OF_YEAR);
	}

	public final static int getISOWeekOfYear(Date date)
	{
		return getISOWeekOfYear(toCalendar(date));
	}

	public final static int getWeekOfYear(Calendar c)
	{
		final Calendar cal = clone(c);
		return cal.get(Calendar.WEEK_OF_YEAR);
	}

	public final static int getWeekOfYear(Date date)
	{
		return getWeekOfYear(toCalendar(date));
	}

	// -------------------------------------------------------------------------------------------
	// Months
	// -------------------------------------------------------------------------------------------

	public final static int getNumberOfMonthsBetween(Calendar start, Calendar end)
	{
		final int startMonth = start.get(Calendar.MONTH) + 1;
		final int startYear = start.get(Calendar.YEAR);
		final int endMonth = end.get(Calendar.MONTH) + 1;
		final int endYear = end.get(Calendar.YEAR);

		// the following will work okay for Gregorian but will not
		// work correctly in a Calendar where the number of months
		// in a year is not constant
		return Math.round(((endYear - startYear) * (start.getMaximum(Calendar.MONTH) + 1)) + (endMonth - startMonth));
	}

	public final static int getNumberOfMonthsBetween(Date start, Date end)
	{
		return getNumberOfMonthsBetween(toCalendar(start), toCalendar(end));
	}

	public final static Calendar[] getMonthsBetween(Calendar start, Calendar end, boolean inclusive)
	{
		final int numberOfMonthsBetween = getNumberOfMonthsBetween(start, end);

		if (numberOfMonthsBetween < 0)
		{
			return null;
		}

		Calendar[] dates = null;

		if (inclusive)
		{
			dates = new Calendar[numberOfMonthsBetween == 1 ? 2 : numberOfMonthsBetween + 1];
			for (int i = 0; i < dates.length; i++)
			{
				dates[i] = addMonths(start, i);
			}
		}
		else
		{
			dates = new Calendar[numberOfMonthsBetween <= 0 ? 0 : numberOfMonthsBetween - 1];
			for (int i = 0; i < dates.length; i++)
			{
				dates[i] = addMonths(start, i + 1);
			}
		}

		return dates;
	}

	public final static Date[] getMonthsBetween(Date start, Date end, boolean inclusive)
	{
		final int numberOfMonthsBetween = getNumberOfMonthsBetween(start, end);

		if (numberOfMonthsBetween < 0)
		{
			return null;
		}

		Date[] dates = null;

		if (inclusive)
		{
			dates = new Date[numberOfMonthsBetween == 1 ? 2 : numberOfMonthsBetween + 1];
			for (int i = 0; i < dates.length; i++)
			{
				dates[i] = addMonths(start, i);
			}
		}
		else
		{
			dates = new Date[numberOfMonthsBetween <= 0 ? 0 : numberOfMonthsBetween - 1];
			for (int i = 0; i < dates.length; i++)
			{
				dates[i] = addMonths(start, i + 1);
			}
		}

		return dates;
	}

	public final static String[] getFormattedMonthsBetween(Calendar start, Calendar end, boolean inclusive, Format format)
	{
		final int numberOfMonthsBetween = getNumberOfMonthsBetween(start, end);

		if (numberOfMonthsBetween < 0)
		{
			return null;
		}

		String[] dates = null;

		if (inclusive)
		{
			dates = new String[numberOfMonthsBetween == 1 ? 2 : numberOfMonthsBetween + 1];
			for (int i = 0; i < dates.length; i++)
			{
				dates[i] = format.format(addMonths(start, i));
			}
		}
		else
		{
			dates = new String[numberOfMonthsBetween <= 0 ? 0 : numberOfMonthsBetween - 1];
			for (int i = 0; i < dates.length; i++)
			{
				dates[i] = format.format(addMonths(start, i + 1));
			}
		}

		return dates;
	}

	public final static String[] getFormattedMonthsBetween(Date start, Date end, boolean inclusive, Format format)
	{
		final int numberOfMonthsBetween = getNumberOfMonthsBetween(start, end);

		if (numberOfMonthsBetween < 0)
		{
			return null;
		}

		String[] dates = null;

		if (inclusive)
		{
			dates = new String[numberOfMonthsBetween == 1 ? 2 : numberOfMonthsBetween + 1];
			for (int i = 0; i < dates.length; i++)
			{
				dates[i] = format.format(addMonths(start, i));
			}
		}
		else
		{
			dates = new String[numberOfMonthsBetween <= 0 ? 0 : numberOfMonthsBetween - 1];
			for (int i = 0; i < dates.length; i++)
			{
				dates[i] = format.format(addMonths(start, i + 1));
			}
		}

		return dates;
	}

	// -------------------------------------------------------------------------------------------
	// Years
	// -------------------------------------------------------------------------------------------

	public final static int getNumberOfYearsBetween(Calendar start, Calendar end)
	{
		final int startYear = start.get(Calendar.YEAR);
		final int endYear = end.get(Calendar.YEAR);
		return Math.round(endYear - startYear);
	}

	public final static int getNumberOfYearsBetween(Date start, Date end)
	{
		return getNumberOfYearsBetween(toCalendar(start), toCalendar(end));
	}

	public final static Calendar[] getYearsBetween(Calendar start, Calendar end, boolean inclusive)
	{
		final int numberOfYearsBetween = getNumberOfYearsBetween(start, end);

		if (numberOfYearsBetween < 0)
		{
			return null;
		}

		Calendar[] dates = null;

		if (inclusive)
		{
			dates = new Calendar[numberOfYearsBetween == 1 ? 2 : numberOfYearsBetween + 1];
			for (int i = 0; i < dates.length; i++)
			{
				dates[i] = addYears(start, i);
			}
		}
		else
		{
			dates = new Calendar[numberOfYearsBetween <= 0 ? 0 : numberOfYearsBetween - 1];
			for (int i = 0; i < dates.length; i++)
			{
				dates[i] = addYears(start, i + 1);
			}
		}

		return dates;
	}

	public final static Date[] getYearsBetween(Date start, Date end, boolean inclusive)
	{
		final int numberOfYearsBetween = getNumberOfYearsBetween(start, end);

		if (numberOfYearsBetween < 0)
		{
			return null;
		}

		Date[] dates = null;

		if (inclusive)
		{
			dates = new Date[numberOfYearsBetween == 1 ? 2 : numberOfYearsBetween + 1];
			for (int i = 0; i < dates.length; i++)
			{
				dates[i] = addYears(start, i);
			}
		}
		else
		{
			dates = new Date[numberOfYearsBetween <= 0 ? 0 : numberOfYearsBetween - 1];
			for (int i = 0; i < dates.length; i++)
			{
				dates[i] = addYears(start, i + 1);
			}
		}

		return dates;
	}

	public final static String[] getFormattedYearsBetween(Calendar start, Calendar end, boolean inclusive, Format format)
	{
		final int numberOfYearsBetween = getNumberOfYearsBetween(start, end);

		if (numberOfYearsBetween < 0)
		{
			return null;
		}

		String[] dates = null;

		if (inclusive)
		{
			dates = new String[numberOfYearsBetween == 1 ? 2 : numberOfYearsBetween + 1];
			for (int i = 0; i < dates.length; i++)
			{
				dates[i] = format.format(addYears(start, i));
			}
		}
		else
		{
			dates = new String[numberOfYearsBetween <= 0 ? 0 : numberOfYearsBetween - 1];
			for (int i = 0; i < dates.length; i++)
			{
				dates[i] = format.format(addYears(start, i + 1));
			}
		}

		return dates;
	}

	public final static String[] getFormattedYearsBetween(Date start, Date end, boolean inclusive, Format format)
	{
		final int numberOfYearsBetween = getNumberOfYearsBetween(start, end);

		if (numberOfYearsBetween < 0)
		{
			return null;
		}

		String[] dates = null;

		if (inclusive)
		{
			dates = new String[numberOfYearsBetween == 1 ? 2 : numberOfYearsBetween + 1];
			for (int i = 0; i < dates.length; i++)
			{
				dates[i] = format.format(addYears(start, i));
			}
		}
		else
		{
			dates = new String[numberOfYearsBetween <= 0 ? 0 : numberOfYearsBetween - 1];
			for (int i = 0; i < dates.length; i++)
			{
				dates[i] = format.format(addYears(start, i + 1));
			}
		}

		return dates;
	}

	// -------------------------------------------------------------------------------------------
	// Quarters
	// -------------------------------------------------------------------------------------------

	/**
	 * @return a positive non-zero integer representing the quarter which the given date belongs to. If the given date is greater
	 *         than startOfFiscalYear + 1 year, then the quarter will be incremented past the 4th quarter (e.g. 5th, 6th, 7th,
	 *         etc.) until the correct Nth quarter is reached. The integer returned may be Mod by 4 to keep it within the typical
	 *         four quarters range.
	 */
	public final static int getQuarter(Calendar date, Calendar startOfFiscalYear)
	{
		return getQuarter(date.getTime(), startOfFiscalYear.getTime());
	}

	/**
	 * @return a positive non-zero integer representing the quarter which the given date belongs to. If the given date is greater
	 *         than startOfFiscalYear + 1 year, then the quarter will be incremented past the 4th quarter (e.g. 5th, 6th, 7th,
	 *         etc.) until the correct Nth quarter is reached. The integer returned may be Mod by 4 to keep it within the typical
	 *         four quarters range.
	 */
	public final static int getQuarter(Date date, Date startOfFiscalYear)
	{
		date = zeroOutTime(date);
		int myQuarter = 0;
		Date endOfQuarter = zeroOutTime(startOfFiscalYear);
		do
		{
			++myQuarter;
			endOfQuarter = addMonths(endOfQuarter, 3);
			if (date.compareTo(addDays(endOfQuarter, -1)) <= 0)
			{
				return myQuarter;
			}
		} while (true);
	}

	public final static Calendar[] getQuarterStartDates(Calendar startOfFiscalYear)
	{
		final Calendar[] startDates = new Calendar[4];
		startDates[0] = startOfFiscalYear;
		startDates[1] = addMonths(startOfFiscalYear, 3);
		startDates[2] = addMonths(startOfFiscalYear, 6);
		startDates[3] = addMonths(startOfFiscalYear, 9);
		return startDates;
	}

	public final static Date[] getQuarterStartDates(Date startOfFiscalYear)
	{
		final Date[] startDates = new Date[4];
		startDates[0] = startOfFiscalYear;
		startDates[1] = addMonths(startOfFiscalYear, 3);
		startDates[2] = addMonths(startOfFiscalYear, 6);
		startDates[3] = addMonths(startOfFiscalYear, 9);
		return startDates;
	}

	public final static Calendar[] getQuarterEndDates(Calendar startOfFiscalYear)
	{
		final Calendar[] startDates = new Calendar[4];
		startDates[0] = addDays(addMonths(startOfFiscalYear, 3), -1);
		startDates[1] = addDays(addMonths(startOfFiscalYear, 6), -1);
		startDates[2] = addDays(addMonths(startOfFiscalYear, 9), -1);
		startDates[3] = addDays(addMonths(startOfFiscalYear, 12), -1);
		return startDates;
	}

	public final static Date[] getQuarterEndDates(Date startOfFiscalYear)
	{
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
	public static final int getOffset(Date date, String timeZone)
	{
		return TimeZone.getTimeZone(timeZone).getOffset(date.getTime());
	}

	public static final int getOffsetInHoursBetweenTimeZones(Date date, TimeZone timeZone1, TimeZone timeZone2)
	{
		return (int) (Math.round(timeZone1.getOffset(date.getTime()) / Constants.ONE_HOUR) - (timeZone2.getOffset(date.getTime()) / Constants.ONE_HOUR));
	}

	public static final int getOffsetInHoursBetweenTimeZones(Date date, String timeZone1, String timeZone2)
	{
		return (int) (Math.round(TimeZone.getTimeZone(timeZone1).getOffset(date.getTime()) / Constants.ONE_HOUR) - (TimeZone.getTimeZone(timeZone2).getOffset(
			date.getTime()) / Constants.ONE_HOUR));
	}

	/**
	 * 
	 * @deprecated - DO NOT USE, may provide incorrect results
	 * @return - the offset in hours between two time zones and the given dates
	 * 
	 */
	@Deprecated
	public static final int getOffsetInHoursBetweenTimeZones(Date timeZone1Date, TimeZone timeZone1, Date timeZone2Date, TimeZone timeZone2)
	{
		return (int) (Math.round(timeZone1.getOffset(timeZone1Date.getTime()) / Constants.ONE_HOUR) - (timeZone2.getOffset(timeZone2Date.getTime()) / Constants.ONE_HOUR));
	}

	/**
	 * 
	 * @deprecated - DO NOT USE, may provide incorrect results
	 * 
	 */
	@Deprecated
	public static final int getOffsetInHoursBetweenTimeZones(Date timeZone1Date, String timeZone1, Date timeZone2Date, String timeZone2)
	{
		return getOffsetInHoursBetweenTimeZones(timeZone1Date, TimeZone.getTimeZone(timeZone1), timeZone2Date, TimeZone.getTimeZone(timeZone2));
	}

	/**
	 * 
	 * @return - the offset in hours between two timezones
	 * @deprecated - This method uses the current system date in the calculation, which may lead to incorrect offsets.
	 * 
	 */
	@Deprecated
	public static final int getOffsetInHoursBetweenTimeZones(String timeZone1, String timeZone2)
	{
		return (int) (Math.round(TimeZone.getTimeZone(timeZone1).getOffset(new Date().getTime()) / Constants.ONE_HOUR) - (TimeZone.getTimeZone(timeZone2)
			.getOffset(new Date().getTime()) / Constants.ONE_HOUR));
	}

	/**
	 * 
	 * @return - the offset in hours between two timezones
	 * @deprecated - This method uses the current system date in the calculation, which may lead to incorrect offsets.
	 * 
	 */
	@Deprecated
	public static final int getOffsetInHoursBetweenTimeZones(TimeZone timeZone1, String timeZone2)
	{
		return (int) (Math.round(timeZone1.getOffset(new Date().getTime()) / Constants.ONE_HOUR) - (TimeZone.getTimeZone(timeZone2).getOffset(
			new Date().getTime()) / Constants.ONE_HOUR));
	}

	/**
	 * 
	 * @return - the offset in hours between two timezones
	 * @deprecated - This method uses the current system date in the calculation, which may lead to incorrect offsets.
	 * 
	 */
	@Deprecated
	public static final int getOffsetInHoursBetweenTimeZones(String timeZone1, TimeZone timeZone2)
	{
		return (int) (Math.round(TimeZone.getTimeZone(timeZone1).getOffset(new Date().getTime()) / Constants.ONE_HOUR) - (timeZone2.getOffset(new Date()
			.getTime()) / Constants.ONE_HOUR));
	}

	/**
	 * 
	 * @return - the offset in hours between two timezones
	 * @deprecated - This method uses the current system date in the calculation, which may lead to incorrect offsets.
	 * 
	 */
	@Deprecated
	public static final int getOffsetInHoursBetweenTimeZones(TimeZone timeZone1, TimeZone timeZone2)
	{
		return (int) (Math.round(timeZone1.getOffset(new Date().getTime()) / Constants.ONE_HOUR) - (timeZone2.getOffset(new Date().getTime()) / Constants.ONE_HOUR));
	}

	// -------------------------------------------------------------------------------------------
	// Add/Subtract
	// -------------------------------------------------------------------------------------------

	/**
	 * @deprecated - use addDays with a negative integer instead
	 */
	@Deprecated
	public static final Date subtractDays(Date date, int x)
	{
		return add(date, Calendar.DAY_OF_MONTH, (x * -1));
	}

	/**
	 * @deprecated - use addDays with a negative integer instead
	 */
	@Deprecated
	public static final Calendar subtractDays(Calendar cal, int x)
	{
		return add(cal, Calendar.DAY_OF_MONTH, (x * -1));
	}

	public static final Date addSeconds(Date date, int x)
	{
		return add(date, Calendar.SECOND, x);
	}

	public static final Calendar addSeconds(Calendar cal, int x)
	{
		return add(cal, Calendar.SECOND, x);
	}

	public static final Date addMinutes(Date date, int x)
	{
		return add(date, Calendar.MINUTE, x);
	}

	public static final Calendar addMinutes(Calendar cal, int x)
	{
		return add(cal, Calendar.MINUTE, x);
	}

	public static final Date addHours(Date date, int x)
	{
		return add(date, Calendar.HOUR_OF_DAY, x);
	}

	public static final Calendar addHours(Calendar cal, int x)
	{
		return add(cal, Calendar.HOUR_OF_DAY, x);
	}

	public static final Date addDays(Date date, int x)
	{
		return add(date, Calendar.DAY_OF_MONTH, x);
	}

	public static final Calendar addDays(Calendar cal, int x)
	{
		return add(cal, Calendar.DAY_OF_MONTH, x);
	}

	public static final Date addWeeks(Date date, int x)
	{
		return add(date, Calendar.WEEK_OF_YEAR, x);
	}

	public static final Calendar addWeeks(Calendar cal, int x)
	{
		return add(cal, Calendar.WEEK_OF_YEAR, x);
	}

	public static final Date addMonths(Date date, int x)
	{
		return add(date, Calendar.MONTH, x);
	}

	public static final Calendar addMonths(Calendar cal, int x)
	{
		return add(cal, Calendar.MONTH, x);
	}

	public static final Date addYears(Date date, int x)
	{
		return add(date, Calendar.YEAR, x);
	}

	public static final Calendar addYears(Calendar cal, int x)
	{
		return add(cal, Calendar.YEAR, x);
	}

	private static final Date add(Date date, int field, int numberToAdd)
	{
		if (date == null)
		{
			throw new IllegalArgumentException("Date is null");
		}

		final Calendar cal = toCalendar(date);
		cal.add(field, numberToAdd);
		return cal.getTime();
	}

	private static final Calendar add(Calendar c, int field, int numberToAdd)
	{
		if (c == null)
		{
			throw new IllegalArgumentException("Calendar is null");
		}
		final Calendar cal = clone(c);
		cal.add(field, numberToAdd);
		return cal;
	}

}