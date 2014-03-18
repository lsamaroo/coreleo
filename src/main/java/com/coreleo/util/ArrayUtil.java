package com.coreleo.util;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.coreleo.util.closure.Block;

/**
 * 
 * @author Leon Samaroo
 * 
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public final class ArrayUtil
{


	
	public static final int size(Object[] x)
	{
		if (x == null)
		{
			return 0;
		}

		return x.length;
	}

	/**
	 * 
	 * @param x
	 *            - the array
	 * @param index
	 *            - index to validate
	 * @return true if index is valid, false otherwise
	 */
	public static final boolean isInbounds(Object[] x, Object index)
	{
		if (x == null)
		{
			return false;
		}

		final int ix = NumberUtil.toInteger(index, -1);

		if (ix < 0 || ix >= x.length)
		{
			return false;
		}

		return true;
	}

	public static final boolean isInbounds(Object[] x, int index)
	{
		if (x == null)
		{
			return false;
		}

		if (index < 0 || index >= x.length)
		{
			return false;
		}

		return true;
	}

	/**
	 * @deprecated
	 * @see isInbounds
	 */
	@Deprecated
	public static final boolean isValidIndex(Object[] x, int index)
	{
		return isInbounds(x, index);
	}

	public static final String get(String[] x, int index, String defaultValue)
	{
		if (x == null)
		{
			return null;
		}

		if (index < 0 || index >= x.length)
		{
			return defaultValue;
		}

		return x[index];
	}

	public static final Object get(Object[] x, int index, Object defaultValue)
	{
		if (x == null)
		{
			return null;
		}

		if (index < 0 || index >= x.length)
		{
			return defaultValue;
		}

		return x[index];
	}

	public static final String get(String[] x, int index)
	{
		if (x == null)
		{
			return null;
		}

		if (index < 0 || index >= x.length)
		{
			return null;
		}

		return x[index];
	}

	public static final Object get(Object[] x, int index)
	{
		if (x == null)
		{
			return null;
		}

		if (index < 0 || index >= x.length)
		{
			return null;
		}

		return x[index];
	}

	public static final boolean isEmpty(Object x)
	{
		if (x == null)
		{
			return true;
		}

		if (x instanceof Object[])
		{
			return ((Object[]) x).length <= 0;
		}

		return true;
	}

	public static final boolean isEmpty(int[] x)
	{
		if (x != null)
		{
			return x.length <= 0;
		}

		return true;
	}

	public static final boolean isEmpty(long[] x)
	{
		if (x != null)
		{
			return x.length <= 0;
		}

		return true;
	}

	public static final boolean isEmpty(double[] x)
	{
		if (x != null)
		{
			return x.length <= 0;
		}

		return true;
	}

	public static final boolean isEmpty(float[] x)
	{
		if (x != null)
		{
			return x.length <= 0;
		}

		return true;
	}

	public static final boolean isEmpty(char[] x)
	{
		if (x != null)
		{
			return x.length <= 0;
		}

		return true;
	}

	public static final boolean isEmpty(byte[] x)
	{
		if (x != null)
		{
			return x.length <= 0;
		}

		return true;
	}

	public static final boolean isNotEmpty(Object x)
	{
		return !isEmpty(x);
	}

	public static final boolean isNotEmpty(int[] x)
	{
		return !isEmpty(x);
	}

	public static final boolean isNotEmpty(long[] x)
	{
		return !isEmpty(x);
	}

	public static final boolean isNotEmpty(double[] x)
	{
		return !isEmpty(x);
	}

	public static final boolean isNotEmpty(float[] x)
	{
		return !isEmpty(x);
	}

	public static final boolean isNotEmpty(char[] x)
	{
		return !isEmpty(x);
	}

	public static final boolean isNotEmpty(byte[] x)
	{
		return !isEmpty(x);
	}

	public static final String[] toStringArray(int[] x)
	{
		if (x == null)
		{
			return null;
		}

		final String[] strings = new String[x.length];
		for (int i = 0; i < x.length; i++)
		{
			strings[i] = StringUtil.toString((x[i]));
		}
		return strings;
	}

	public static final String[] toStringArray(long[] x)
	{
		if (x == null)
		{
			return null;
		}

		final String[] strings = new String[x.length];
		for (int i = 0; i < x.length; i++)
		{
			strings[i] = StringUtil.toString((x[i]));
		}
		return strings;
	}

	public static final String[] toStringArray(char[] x)
	{
		if (x == null)
		{
			return null;
		}

		final String[] strings = new String[x.length];
		for (int i = 0; i < x.length; i++)
		{
			strings[i] = StringUtil.toString((x[i]));
		}
		return strings;
	}

	public static final String[] toStringArray(double[] x)
	{
		if (x == null)
		{
			return null;
		}

		final String[] strings = new String[x.length];
		for (int i = 0; i < x.length; i++)
		{
			strings[i] = StringUtil.toString((x[i]));
		}
		return strings;
	}

	public static final String[] toStringArray(Collection x)
	{
		if (x == null)
		{
			return null;
		}

		final String[] strings = new String[x.size()];
		int count = 0;
		for (final Iterator i = x.iterator(); i.hasNext();)
		{
			strings[count++] = StringUtil.toString(i.next());
		}

		return strings;
	}

	public static final String[] toStringArray(Object[] x)
	{
		if (x == null)
		{
			return null;
		}

		final String[] strings = new String[x.length];
		for (int i = 0; i < x.length; i++)
		{
			strings[i] = StringUtil.toString((x[i]));
		}
		return strings;
	}

	// -------------------------------------------------------------------------------------------
	// Integer
	// -------------------------------------------------------------------------------------------

	public static final Object getIntegerObject(Object[] x, int index)
	{
		return NumberUtil.toIntegerObject(get(x, index));
	}

	public static final int[] toIntegerArray(Object[] x) throws NumberFormatException
	{
		if (x == null)
		{
			return null;
		}

		final int[] ints = new int[x.length];
		for (int i = 0; i < x.length; i++)
		{
			ints[i] = NumberUtil.toInteger(x[i]);
		}
		return ints;

	}

	public static final int[] toIntegerArray(Object[] x, int[] defaultValues)
	{
		try
		{
			return toIntegerArray(x);
		}
		catch (final Exception e)
		{
			return defaultValues;
		}
	}

	public static final Integer[] toIntegerObjectArray(Object[] x) throws NumberFormatException
	{
		if (x == null)
		{
			return null;
		}

		if (x instanceof Integer[])
		{
			return (Integer[]) x;
		}

		final Integer[] integers = new Integer[x.length];
		for (int i = 0; i < x.length; i++)
		{
			integers[i] = NumberUtil.toIntegerObject(x[i]);
		}
		return integers;
	}

	public static final Integer[] toIntegerObjectArray(Object[] x, Integer[] defaultValues)
	{
		try
		{
			return toIntegerObjectArray(x);
		}
		catch (final Exception e)
		{
			return defaultValues;
		}
	}

	public static final Integer[] toIntegerObjectArray(int[] x)
	{
		if (x == null)
		{
			return null;
		}

		final Integer[] integers = new Integer[x.length];
		for (int i = 0; i < x.length; i++)
		{
			integers[i] = NumberUtil.toIntegerObject(x[i]);
		}
		return integers;
	}

	public static final Integer[] toIntegerObjectArray(List x)
	{
		if (x == null)
		{
			return null;
		}

		Integer[] array = null;
		try
		{
			array = (Integer[]) x.toArray(new Integer[x.size()]);
		}
		catch (final ArrayStoreException ase)
		{
			// in case this is not a list of Integer objects,
			// then convert the objects to Integers
			array = new Integer[x.size()];
			for (int i = 0; i < x.size(); i++)
			{
				array[i] = NumberUtil.toIntegerObject(x.get(i));
			}
		}

		return array;
	}

	public static final int[] toIntegerArray(List x)
	{
		if (x == null)
		{
			return null;
		}

		final int[] array = new int[x.size()];
		for (int i = 0; i < x.size(); i++)
		{
			array[i] = NumberUtil.toInteger(x.get(i));
		}

		return array;
	}

	// -------------------------------------------------------------------------------------------
	// Double
	// -------------------------------------------------------------------------------------------

	public static final Object getDoubleObject(Object[] x, int index)
	{
		return NumberUtil.toDoubleObject(get(x, index));
	}

	public static final double[] toDoubleArray(Object[] x) throws NumberFormatException
	{
		if (x == null)
		{
			return null;
		}

		final double[] ints = new double[x.length];
		for (int i = 0; i < x.length; i++)
		{
			ints[i] = NumberUtil.toDouble(x[i]);
		}
		return ints;

	}

	public static final double[] toDoubleArray(Object[] x, double[] defaultValues)
	{
		try
		{
			return toDoubleArray(x);
		}
		catch (final Exception e)
		{
			return defaultValues;
		}
	}

	public static final Double[] toDoubleObjectArray(Object[] x) throws NumberFormatException
	{
		if (x == null)
		{
			return null;
		}

		if (x instanceof Double[])
		{
			return (Double[]) x;
		}

		final Double[] Doubles = new Double[x.length];
		for (int i = 0; i < x.length; i++)
		{
			Doubles[i] = NumberUtil.toDoubleObject(x[i]);
		}
		return Doubles;
	}

	public static final Double[] toDoubleObjectArray(Object[] x, Double[] defaultValues)
	{
		try
		{
			return toDoubleObjectArray(x);
		}
		catch (final Exception e)
		{
			return defaultValues;
		}
	}

	public static final Double[] toDoubleObjectArray(int[] x)
	{
		if (x == null)
		{
			return null;
		}

		final Double[] Doubles = new Double[x.length];
		for (int i = 0; i < x.length; i++)
		{
			Doubles[i] = NumberUtil.toDoubleObject(x[i]);
		}
		return Doubles;
	}

	public static final Double[] toDoubleObjectArray(List x)
	{
		if (x == null)
		{
			return null;
		}

		Double[] array = null;
		try
		{
			array = (Double[]) x.toArray(new Double[x.size()]);
		}
		catch (final ArrayStoreException ase)
		{
			// in case this is not a list of Double objects,
			// then convert the objects to Doubles
			array = new Double[x.size()];
			for (int i = 0; i < x.size(); i++)
			{
				array[i] = NumberUtil.toDoubleObject(x.get(i));
			}
		}

		return array;
	}

	public static final double[] toDoubleArray(List x)
	{
		if (x == null)
		{
			return null;
		}

		final double[] array = new double[x.size()];
		for (int i = 0; i < x.size(); i++)
		{
			array[i] = NumberUtil.toDouble(x.get(i));
		}

		return array;
	}

	public static final Object[] toObjectArray(List x)
	{
		if (x == null)
		{
			return null;
		}

		return x.toArray(new Object[x.size()]);
	}

	public static final String toCommaDelimitedString(int[] x)
	{
		return DelimiterUtil.toDelimitedString(x, ",");
	}

	public static final String toCommaDelimitedString(Object... x)
	{
		return DelimiterUtil.toDelimitedString(x, ",");
	}

	public static final String toDelimitedString(Object[] x, String delimiter)
	{
		return DelimiterUtil.toDelimitedString(x, delimiter);
	}

	public static final String toDelimitedString(Object[] x, String delimiter, Block alter)
	{
		return DelimiterUtil.toDelimitedString(x, delimiter, alter);
	}

	public static final Integer[] delimitedStringToIntegerObjectArray(String x, String delimiter)
	{
		return DelimiterUtil.delimitedStringToIntegerObjectArray(x, delimiter);
	}

	public static final Integer[] commaDelimitedStringToIntegerObjectArray(String x)
	{
		return DelimiterUtil.delimitedStringToIntegerObjectArray(x, ",");
	}

	public static final int[] delimitedStringToIntegerArray(String x, String delimiter)
	{
		return DelimiterUtil.delimitedStringToIntegerArray(x, delimiter);
	}

	public static final int[] commaDelimitedStringToIntegerArray(String x)
	{
		return DelimiterUtil.delimitedStringToIntegerArray(x, ",");
	}


	public static final Integer getMax(Integer[] x)
	{
		if (x == null)
		{
			return null;
		}

		Integer max = x[0];

		for (int i = 1; i < 24; i++)
		{
			max = NumberUtil.getMax(max, x[i]);
		}

		return max;
	}

	public static final Double getMax(Double[] x)
	{
		if (x == null)
		{
			return null;
		}

		Double max = x[0];

		for (int i = 1; i < 24; i++)
		{
			max = NumberUtil.getMax(max, x[i]);
		}

		return max;
	}

	public static final BigDecimal getMax(Object[] x)
	{
		if (x == null)
		{
			return null;
		}

		Object max = x[0];

		for (int i = 1; i < 24; i++)
		{
			max = NumberUtil.getMax(max, x[i]);
		}
		return NumberUtil.toBigDecimal(max, null);
	}

	public static final Integer getMin(Integer[] x)
	{
		if (x == null)
		{
			return null;
		}

		Integer min = x[0];

		for (int i = 1; i < 24; i++)
		{
			min = NumberUtil.getMin(min, x[i]);
		}

		return min;
	}

	public static final Double getMin(Double[] x)
	{
		if (x == null)
		{
			return null;
		}

		Double min = x[0];

		for (int i = 1; i < 24; i++)
		{
			min = NumberUtil.getMin(min, x[i]);
		}

		return min;
	}

	public static final BigDecimal getMin(Object[] x)
	{
		if (x == null)
		{
			return null;
		}

		Object min = x[0];

		for (int i = 1; i < 24; i++)
		{
			min = NumberUtil.getMin(min, x[i]);
		}

		return NumberUtil.toBigDecimal(min, null);
	}

	public static final Integer getSum(Integer[] x)
	{
		Integer total = NumberUtil.ZERO_INTEGER;
		if (x != null)
		{
			for (final Integer element : x)
			{
				total = NumberUtil.sum(total, element);
			}
		}
		return total;
	}

	public static final Double getSum(Double[] x)
	{
		Double total = NumberUtil.ZERO_DOUBLE;
		if (x != null)
		{
			for (final Double element : x)
			{
				total = NumberUtil.sum(total, element);
			}
		}
		return total;
	}

	public static final BigDecimal getSum(Object[] x)
	{
		BigDecimal total = NumberUtil.ZERO_BIGDECIMAL;
		if (x != null)
		{
			for (final Object element : x)
			{
				total = NumberUtil.sum(total, NumberUtil.toBigDecimal(element, 0));
			}
		}
		return total;
	}

	public static Object[] clone(final Object[] x)
	{
		if (x == null)
		{
			return null;
		}
		return x.clone();
	}

	public static int[] clone(int[] x)
	{
		if (x == null)
		{
			return null;
		}
		return x.clone();
	}

	public static char[] clone(final char[] x)
	{
		if (x == null)
		{
			return null;
		}
		return x.clone();
	}

	public static byte[] clone(final byte[] x)
	{
		if (x == null)
		{
			return null;
		}
		return x.clone();
	}

	public static boolean isIntegerArray(Object[] x)
	{
		if (x == null)
		{
			return false;
		}

		try
		{
			for (final Object element : x)
			{
				NumberUtil.toInteger(element);
			}
		}
		catch (final Exception e)
		{
			return false;
		}

		return true;
	}

	public static boolean contains(Object[] xArray, Object x)
	{
		if (xArray == null)
		{
			return false;
		}

		if (x == null)
		{
			return false;
		}

		for (final Object element : xArray)
		{
			if (x.equals(element))
			{
				return true;
			}
		}

		return false;
	}

	public static Object[] add(Object[] xArray, Object x)
	{
		Object[] newArray = null;
		if (xArray == null)
		{
			newArray = new Object[1];
		}
		else
		{
			newArray = (Object[]) expand(xArray, xArray.length + 1);
		}

		newArray[newArray.length - 1] = x;

		return newArray;
	}

	public static Object expand(Object obj, int newLength)
	{
		final Class clazz = obj.getClass();
		if (!clazz.isArray())
		{
			return null;
		}
		final int length = Array.getLength(obj);

		final Class componentType = obj.getClass().getComponentType();
		final Object newArray = Array.newInstance(componentType, newLength);
		System.arraycopy(obj, 0, newArray, 0, length < newLength ? length : newLength);
		return newArray;
	}

	public static Object[] addAll(Object[] toThisArray, Object[] addThisArray)
	{
		if (toThisArray == null && addThisArray == null)
		{
			return null;
		}

		if (toThisArray == null)
		{
			return addThisArray;
		}

		if (addThisArray == null)
		{
			return toThisArray;
		}

		final Object[] newArray = (Object[]) expand(toThisArray, toThisArray.length + addThisArray.length);
		for (int i = toThisArray.length; i < newArray.length; i++)
		{
			newArray[i] = addThisArray[i - toThisArray.length];
		}

		return newArray;
	}

}
