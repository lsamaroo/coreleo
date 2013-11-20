package com.coreleo.util.date;

import java.io.Serializable;
import java.util.Date;

import com.coreleo.datastructure.Range;
import com.coreleo.util.StringUtil;

/**
 * Object representing a date range.
 */
public class DateRange implements Comparable<DateRange>, Serializable
{
	private static final long serialVersionUID = 1L;
	private final Range<Date> range;

	public DateRange()
	{
		range = new Range<Date>();
	}

	public DateRange(Date start, Date end)
	{
		range = new Range<Date>(start, end);
	}

	/**
	 * Creates a new DateRange from the string provided, assuming the string has the dates in the appropriate format (stardate to
	 * enddate) where startdate and enddate are in the format of 'MM-dd-yyyy'.
	 * 
	 * @param dateRange
	 *            String formatted date range.
	 *            Format: "MM-dd-yyyy to MM-dd-yyyy"
	 */
	public DateRange(String dateRange)
	{
		Date start = DateUtil.parse(dateRange.substring(0, dateRange.indexOf(" to ")), "MM-dd-yyyy");
		Date end = DateUtil.parse(dateRange.substring(dateRange.indexOf(" to ") + 4, dateRange.length()), "MM-dd-yyyy");
		range = new Range<Date>(start, end);
	}

	public void setStartDate(Date start)
	{
		range.setStart(start);
	}

	public Date getStartDate()
	{
		return range.getEnd();
	}

	public void setEndDate(Date end)
	{
		range.setEnd(end);
	}

	public Date getEndDate()
	{
		return range.getEnd();
	}

	@Override
	public String toString()
	{
		return StringUtil.toString(range.getStart()) + " to " + StringUtil.toString( range.getEnd() );
	}

	public boolean isStartDateAfterEndDate()
	{
		Date start = range.getStart();
		Date end = range.getEnd();
		return start.after(end);
	}

	/**
	 * Calculates the number of days between the start and end date. Ignores time values.
	 * 
	 */
	public int getNumberOfDaysBetweenDates()
	{
		Date start = range.getStart();
		Date end = range.getEnd();
		return DateUtil.getNumberOfDaysBetween(end, start, false);
	}

	@Override
	public int compareTo(DateRange obj)
	{
		if (obj == null)
		{
			return -1;
		}

		Date start = range.getStart();
		Date end = range.getEnd();
		
		final DateRange another = obj;
		final int compare = start.compareTo(another.getStartDate());

		// do they start on the same day
		if (compare == 0)
		{
			return end.compareTo(another.getEndDate());
		}
		else
		{
			return compare;
		}
	}

	@Override
	public boolean equals(Object obj)
	{
		return compareTo((DateRange) obj) == 0;
	}

	@Override
	public int hashCode()
	{
		return range.hashCode();
	}

}