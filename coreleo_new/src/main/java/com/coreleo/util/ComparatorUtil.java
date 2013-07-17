package com.coreleo.util;

import java.io.Serializable;
import java.util.Comparator;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class ComparatorUtil
{
	public final static Comparator NATURAL_COMPARATOR = new NaturalComparator();
	public final static Comparator STRING_COMPARATOR = new StringComparator();
	public final static Comparator IGNORE_CASE_STRING_COMPARATOR = new IgnoreCaseStringComparator();

	private ComparatorUtil()
	{
		super();
	}

	private static class NaturalComparator implements Comparator, Serializable
	{
		private static final long serialVersionUID = 1L;

		@Override
		public int compare(Object obj1, Object obj2)
		{
			if (obj1 instanceof Comparable && obj2 instanceof Comparable)
			{
				final Comparable comparable1 = (Comparable) obj1;
				final Comparable comparable2 = (Comparable) obj2;
				return comparable1.compareTo(comparable2);
			}
			return -1;
		}

	}

	private static class StringComparator implements Comparator, Serializable
	{
		private static final long serialVersionUID = 1L;

		@Override
		public int compare(Object obj1, Object obj2)
		{
			if (obj1 != null && obj2 != null)
			{
				return String.valueOf(obj1).compareTo(String.valueOf(obj2));
			}
			return -1;
		}
	}

	private static class IgnoreCaseStringComparator implements Comparator, Serializable
	{
		private static final long serialVersionUID = 1L;

		@Override
		public int compare(Object obj1, Object obj2)
		{
			if (obj1 != null && obj2 != null)
			{
				return StringUtil.toLowerCase(obj1).compareTo(StringUtil.toLowerCase(obj2));
			}
			return -1;
		}
	}
}
