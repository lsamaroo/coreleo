package com.coreleo.datastructure;

/**
 * 
 * @author Leon Samaroo
 * 
 * @param <T>
 *            - any type which implements Comparable
 * 
 *            An object representing a range (start to end) of a given type.
 */
@SuppressWarnings("rawtypes")
public class Range<T extends Comparable> implements Comparable
{
	private T start;
	private T end;
	
	public Range()
	{
		super();
	}

	public Range(T start, T end)
	{
		super();
		this.start = start;
		this.end = end;
	}

	public T getStart()
	{
		return start;
	}

	public T getEnd()
	{
		return end;
	}
	

	public void setStart(T start) {
		this.start = start;
	}

	public void setEnd(T end) {
		this.end = end;
	}

	@SuppressWarnings("unchecked")
	public boolean contains(T value)
	{
		return start.compareTo(value) <= 0 && end.compareTo(value) >= 0;
	}

	@Override
	public boolean equals(Object obj)
	{

		return compareTo(obj) == 0;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int compareTo(Object obj)
	{
		final Range other = (Range) obj;
		final int compare = start.compareTo(other.getStart());
		if (compare != 0)
		{
			return compare;
		}
		else
		{
			return end.compareTo(other.getEnd());
		}
	}

	@Override
	public int hashCode() {
		return (start.hashCode() ^ end.hashCode());
	}

	
}
