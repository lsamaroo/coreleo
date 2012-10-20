package com.coreleo.util.date;

import java.io.Serializable;
import java.util.Date;

/**
 * Object representing a date range.
 */
public class DateRange implements Comparable<DateRange>, Serializable
{
	private static final long serialVersionUID = 1L;

	private Date start;
	private Date end;

	public DateRange()
	{
	}

	public DateRange(Date start, Date end)
	{
		this.start = start;
		this.end = end;
	}

	/**
	 * Creates a new DateRange from the string provided, assuming the string has the dates in the appropriate format (stardate -
	 * enddate). Dates are in the format of 'MM-dd-yyyy'.
	 * 
	 * @param dateRange
	 *            String format date range (same as toString() return value for this class).
	 */
	public DateRange(String dateRange)
	{
		start = DateUtil.parse(dateRange.substring(0, dateRange.indexOf(" to ")), "MM-dd-yyyy");
		end = DateUtil.parse(dateRange.substring(dateRange.indexOf(" to ") + 4, dateRange.length()), "MM-dd-yyyy");
	}

	public void setStartDate(Date start)
	{
		this.start = start;
	}

	public Date getStartDate()
	{
		return start;
	}

	public void setEndDate(Date end)
	{
		this.end = end;
	}

	public Date getEndDate()
	{
		return end;
	}

	@Override
	public String toString()
	{
		return start.toString() + " - " + end.toString();
	}

	public boolean isStartDateAfterEndDate()
	{
		return start.after(end);
	}

	/**
	 * Calculates the number of days between the start and end date. Ignores time values.
	 * 
	 */
	public int getNumberOfDaysBetweenDates()
	{
		return DateUtil.getNumberOfDaysBetween(end, start, false);
	}

	@Override
	public int compareTo(DateRange obj)
	{
		if (obj == null)
		{
			return -1;
		}

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
		return (start.hashCode() ^ end.hashCode());
	}

}