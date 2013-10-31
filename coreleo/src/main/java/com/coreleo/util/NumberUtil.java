/**
 * 
 */
package com.coreleo.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * @author Leon Samaroo
 * 
 */
public final class NumberUtil
{
	public static final double kilobytes = 1024;
	public static final BigDecimal NEGATIVE_ONE_BIGDECIMAL = new BigDecimal("-1");
	public static final Integer NEGATIVE_ONE_INTEGER = new Integer("-1");
	public static final Double NEGATIVE_ONE_DOUBLE = new Double("-1");
	public static final BigDecimal ZERO_BIGDECIMAL = new BigDecimal("0");
	public static final Integer ZERO_INTEGER = new Integer("0");
	public static final Double ZERO_DOUBLE = new Double("0");
	public static final Long ZERO_LONG = new Long("0");
	public static final Float ZERO_FLOAT = new Float("0");
	public static final BigDecimal ONE_BIGDECIMAL = new BigDecimal("1");
	public static final Integer ONE_INTEGER = new Integer("1");
	public static final Long ONE_LONG = new Long("1");
	public static final Double ONE_DOUBLE = new Double("1");
	public static final Float ONE_FLOAT = new Float("1");
	public static final Number THOUSAND = new BigDecimal("1000");
	public static final Number MILLION = new BigDecimal("1000000");
	public static final Number BILLION = new BigDecimal("1000000000");
	public static final Number TRILLION = new BigDecimal("1000000000000");
	public static final Number QUADRILLION = new BigDecimal("1000000000000000");
	public static final Number QUINTILLION = new BigDecimal("1000000000000000000");
	public static final Number SEXTILLION = new BigDecimal("1000000000000000000000");

	/**
	 * @deprecated
	 */
	@Deprecated
	public static final NumberFormat NINE_DIGIT_NUMBER = new DecimalFormat("###,###,##0");
	public static final DecimalFormat NO_DECIMAL_PLACES = new DecimalFormat("0");
	public static final DecimalFormat DECIMALFORMAT = new DecimalFormat("###.###");
	public static final NumberFormat COMMASEPARATED = new DecimalFormat("###,###,###,###");

	private NumberUtil()
	{
		super();
	}

	public final static boolean isPowerTwo(int i)
	{
		if (i == 0)
		{
			return false;
		}

		return (i & (i - 1)) == 0;
	}

	public static String toBinaryString(int x, int bits)
	{
		if (x == 0)
		{
			return StringUtil.padStringWithCharacter("", '0', bits, true);
		}

		final boolean isNegative = x < 0;

		final StringBuilder builder = new StringBuilder();
		int r = -1;
		int q = isNegative ? Math.abs(x) - 1 : x;
		while (q != 0)
		{
			r = q % 2;
			q = q / 2;

			final int temp = isNegative ? Math.abs(r - 1) : r;
			builder.insert(0, temp);
		}

		final char prefixChar = isNegative ? '1' : '0';
		final int numTimes = bits - builder.length();
		return StringUtil.padStringWithCharacter(builder, prefixChar, numTimes, true);
	}

	@SuppressWarnings("unchecked")
	public static final boolean inRange(Number x, Number startRange, Number endRange)
	{
		if (x == null)
		{
			return false;
		}
		if (x.getClass() != startRange.getClass())
		{
			throw new IllegalArgumentException("The number must be of the same type as the range numbers");
		}
		final int compareMin = ((Comparable<Number>) startRange).compareTo(x);
		final int compareMax = ((Comparable<Number>) endRange).compareTo(x);
		return compareMin <= 0 && compareMax >= 0;
	}

	public static final boolean inRange(int x, int startRange, int endRange)
	{
		return startRange <= x && endRange >= x;
	}

	public static final boolean inRange(float x, float startRange, float endRange)
	{
		return startRange <= x && endRange >= x;
	}

	public static final boolean inRange(long x, long startRange, long endRange)
	{
		return startRange <= x && endRange >= x;
	}

	public static final boolean inRange(double x, double startRange, double endRange)
	{
		return startRange <= x && endRange >= x;
	}

	public static final String toStringPadSingleDigitsWithZeros(int x, int numberOfZeros)
	{
		final String temp = String.valueOf(x);
		if (x != 0 && x > -10 && x < 10)
		{
			return StringUtil.padStringWithCharacter(temp, '0', 1, true);
		}
		else
		{
			return temp;
		}
	}

	public static final boolean equals(int x1, int x2)
	{
		return (x1 == x2);
	}

	/**
	 * @deprecated
	 */
	@Deprecated
	public static final boolean isEqual(int x1, int x2)
	{
		return equals(x1, x2);
	}

	public static final boolean equals(double x1, double x2)
	{
		return (x1 == x2);
	}

	/**
	 * @deprecated
	 */
	@Deprecated
	public static final boolean isEqual(double x1, double x2)
	{
		return equals(x1, x2);
	}

	public static final boolean equals(Number x1, Number x2)
	{
		if (x1 == null || x2 == null)
		{
			return false;
		}

		return x1.doubleValue() == x2.doubleValue();
	}

	/**
	 * @deprecated
	 */
	@Deprecated
	public static final boolean isEqual(Number x1, Number x2)
	{
		return equals(x1, x2);
	}

	public static final boolean equals(Object x1, Object x2)
	{
		if (x1 == null || x2 == null)
		{
			return false;
		}

		try
		{
			return equals(toDouble(x1), toDouble(x2));
		}
		catch (final Exception e)
		{
			return false;
		}
	}

	/**
	 * @deprecated
	 */
	@Deprecated
	public static final boolean isEqual(Object x1, Object x2)
	{
		return equals(x1, x2);
	}

	public static final boolean equals(Object x1, int x2)
	{
		if (x1 == null)
		{
			return false;
		}

		try
		{
			return equals(toInteger(x1), x2);
		}
		catch (final NumberFormatException nfe)
		{
			return false;
		}
	}

	/**
	 * @deprecated
	 */
	@Deprecated
	public static final boolean isEqual(Object x1, int x2)
	{
		return equals(x1, x2);
	}

	public static final boolean notEquals(int x1, int x2)
	{
		return (!equals(x1, x2));
	}

	/**
	 * @deprecated
	 */
	@Deprecated
	public static final boolean isNotEqual(int x1, int x2)
	{
		return (!isEqual(x1, x2));
	}

	public static final boolean notEquals(Object x1, Object x2)
	{
		return (!equals(x1, x2));
	}

	/**
	 * @deprecated
	 */
	@Deprecated
	public static final boolean isNotEqual(Object x1, Object x2)
	{
		return (!isEqual(x1, x2));
	}

	public static final boolean notEquals(Object x1, int x2)
	{
		return (!equals(x1, x2));
	}

	/**
	 * @deprecated
	 */
	@Deprecated
	public static final boolean isNotEqual(Object x1, int x2)
	{
		return (!isEqual(x1, x2));
	}

	public static final boolean isPositiveInteger(int x)
	{
		return (x > 0);
	}

	public static final boolean isPositiveInteger(Object x)
	{
		try
		{
			final int num = toInteger(x);
			return (num > 0);
		}
		catch (final Exception e)
		{
			return false;
		}
	}

	public static final boolean isNegativeInteger(Object x)
	{
		try
		{
			final int num = toInteger(x);
			return (num < 0);
		}
		catch (final Exception e)
		{
			return false;
		}
	}

	public static final boolean isNegativeInteger(int x)
	{
		return (x < 0);
	}

	public static final boolean isZero(String x)
	{
		try
		{
			final int num = Integer.parseInt(x);
			return (num == 0);
		}
		catch (final Exception e)
		{
			return false;
		}
	}

	public static final boolean isInteger(Object x)
	{
		if (x == null)
		{
			return false;
		}

		try
		{
			if (x instanceof Integer)
			{
				return true;
			}
			else
			{
				Integer.parseInt(StringUtil.trim(x));
				return true;
			}
		}
		catch (final Exception e)
		{
			return false;
		}
	}

	public static final boolean isLong(Object x)
	{
		if (x == null || StringUtil.isEmpty(x))
		{
			return false;
		}

		try
		{
			if (x instanceof Long)
			{
				return true;
			}
			else
			{
				Long.parseLong(StringUtil.trim(x));
				return true;
			}
		}
		catch (final Exception e)
		{
			return false;
		}
	}

	public static final boolean isDouble(Object x)
	{
		if (x == null)
		{
			return false;
		}

		try
		{
			if (x instanceof Double)
			{
				return true;
			}
			else
			{
				Double.parseDouble(StringUtil.trim(x));
				return true;
			}
		}
		catch (final Exception e)
		{
			return false;
		}
	}

	public static final boolean isFloat(Object x)
	{
		if (x == null)
		{
			return false;
		}

		try
		{
			if (x instanceof Float)
			{
				return true;
			}
			else
			{
				Float.parseFloat(StringUtil.trim(x));
				return true;
			}
		}
		catch (final Exception e)
		{
			return false;
		}
	}

	public static final boolean equalsToAny(Number x1, Number[] x2)
	{
		if (x1 == null || x2 == null)
		{
			return false;
		}

		for (final Number element : x2)
		{
			if (equals(x1, element))
			{
				return true;
			}
		}

		return false;
	}

	/**
	 * @deprecated
	 */
	@Deprecated
	public static final boolean isEqualToAny(Number x1, Number[] x2)
	{
		return equalsToAny(x1, x2);
	}

	public static final boolean equalsToAny(int x1, Number[] x2)
	{
		return equalsToAny(toIntegerObject(x1), x2);
	}

	/**
	 * @deprecated
	 */
	@Deprecated
	public static final boolean isEqualToAny(int x1, Number[] x2)
	{
		return equalsToAny(x1, x2);
	}

	public static final boolean equalsToAny(Number x1, int[] x2)
	{
		if (x1 == null || x2 == null)
		{
			return false;
		}

		return equalsToAny(x1.intValue(), x2);
	}

	/**
	 * @deprecated
	 */
	@Deprecated
	public static final boolean isEqualToAny(Number x1, int[] x2)
	{
		return equalsToAny(x1, x2);
	}

	public static final boolean equalsToAny(int x1, int[] x2)
	{
		if (x2 == null)
		{
			return false;
		}

		for (final int element : x2)
		{
			if (equals(x1, element))
			{
				return true;
			}
		}

		return false;
	}

	/**
	 * @deprecated
	 */
	@Deprecated
	public static final boolean isEqualToAny(int x1, int[] x2)
	{
		return equalsToAny(x1, x2);
	}

	// ------------------------------------------------------------------------------
	// Functions for working with integers
	// ------------------------------------------------------------------------------

	public static final int toInteger(Number x)
	{
		if (x == null)
		{
			throw new NumberFormatException("The integer must not be null");
		}

		return x.intValue();
	}

	public static final int toInteger(Object x)
	{
		if (x == null)
		{
			throw new NumberFormatException("The parameter must not be null");
		}

		if (x instanceof Number)
		{
			return ((Number) x).intValue();
		}

		return Integer.parseInt(StringUtil.trim(x));
	}

	public static final int toInteger(Object x, int defaultValue)
	{
		if (x == null)
		{
			return defaultValue;
		}

		try
		{
			return toInteger(x);
		}
		catch (final Exception e)
		{
			return defaultValue;
		}
	}

	/**
	 * 
	 * @throws NumberFormatException
	 *             if the default value is null;
	 */
	public static final int toInteger(Object x, Number defaultValue)
	{
		if (defaultValue == null)
		{
			throw new NumberFormatException("The default argument cannot be null.");
		}

		if (x == null)
		{
			return defaultValue.intValue();
		}

		try
		{
			return toInteger(x);
		}
		catch (final Exception e)
		{
			return defaultValue.intValue();
		}
	}

	public static final Integer toIntegerObject(int x)
	{
		return Integer.valueOf(x);
	}

	public static final Integer toIntegerObject(Object x)
	{
		if (x == null)
		{
			throw new NumberFormatException("The object must not be null");
		}

		if (x instanceof Integer)
		{
			return (Integer) x;
		}

		if (x instanceof Number)
		{
			return toIntegerObject(((Number) x).intValue());
		}

		return Integer.valueOf(StringUtil.trim(x));
	}

	public static final Integer toIntegerObject(Object x, Integer defaultValue)
	{
		if (x == null)
		{
			return defaultValue;
		}

		try
		{
			return toIntegerObject(x);
		}
		catch (final Exception e)
		{
			return defaultValue;
		}
	}

	public static final Integer toIntegerObject(Object x, int defaultValue)
	{
		if (x == null)
		{
			return toIntegerObject(defaultValue);
		}

		try
		{
			return toIntegerObject(x);
		}
		catch (final Exception e)
		{
			return toIntegerObject(defaultValue);
		}
	}

	// ------------------------------------------------------------------------------
	// Functions for working with doubles, floats and longs
	// ------------------------------------------------------------------------------

	public static final double toDouble(Object x)
	{
		if (x == null)
		{
			throw new NumberFormatException("The object must not be null");
		}

		if (x instanceof Number)
		{
			return ((Number) x).doubleValue();
		}

		return Double.parseDouble(x.toString().trim());
	}

	public static final double toDouble(Object x, double defaultValue)
	{
		if (x == null)
		{
			return defaultValue;
		}

		try
		{
			return toDouble(x);
		}
		catch (final Exception e)
		{
			return defaultValue;
		}
	}

	public static final Double toDoubleObject(double x)
	{
		return new Double(x);
	}

	public static final Double toDoubleObject(Object x)
	{
		if (x == null)
		{
			throw new NumberFormatException("The object must not be null");
		}

		if (x instanceof Double)
		{
			return (Double) x;
		}

		if (x instanceof Number)
		{
			return new Double(((Number) x).doubleValue());
		}

		return new Double(StringUtil.trim(x));
	}

	public static final Double toDoubleObject(Object x, Double defaultValue)
	{
		if (x == null)
		{
			return defaultValue;
		}

		try
		{
			return toDoubleObject(x);
		}
		catch (final Exception e)
		{
			return defaultValue;
		}
	}

	public static final Double toDoubleObject(Object x, double defaultValue)
	{
		if (x == null)
		{
			return new Double(defaultValue);
		}

		try
		{
			return toDoubleObject(x);
		}
		catch (final Exception e)
		{
			return new Double(defaultValue);
		}
	}

	public static final float toFloat(Object x)
	{
		if (x == null)
		{
			throw new NumberFormatException("The object must not be null");
		}

		if (x instanceof Number)
		{
			return ((Number) x).floatValue();
		}

		return Float.parseFloat(x.toString().trim());
	}

	public static final Float toFloatObject(Object x)
	{
		if (x == null)
		{
			throw new NumberFormatException("The object must not be null");
		}

		if (x instanceof Float)
		{
			return (Float) x;
		}

		if (x instanceof Number)
		{
			return new Float(((Number) x).floatValue());
		}

		return new Float(StringUtil.trim(x));
	}

	public static final Float toFloatObject(float x)
	{
		return new Float(x);
	}

	public static final long toLong(Object x)
	{
		if (x == null)
		{
			throw new NumberFormatException("The object must not be null");
		}

		if (x instanceof Number)
		{
			return ((Number) x).longValue();
		}

		return Long.parseLong(x.toString().trim());
	}

	public static final long toLong(Object x, long defaultValue)
	{
		if (x == null)
		{
			return defaultValue;
		}

		try
		{
			return toLong(x);
		}
		catch (final Exception e)
		{
			return defaultValue;
		}
	}

	public static final Long toLongObject(long x)
	{
		// TODO replace with Long.value of in JDK 5
		return new Long(x);
	}

	public static final Long toLongObject(Object x)
	{
		if (x == null)
		{
			throw new NumberFormatException("The object must not be null");
		}

		if (x instanceof Long)
		{
			return (Long) x;
		}

		if (x instanceof Number)
		{
			return toLongObject(((Number) x).longValue());
		}

		return Long.valueOf(StringUtil.trim(x));
	}

	public static final Long toLongObject(Object x, Long defaultValue)
	{
		if (x == null)
		{
			return defaultValue;
		}

		try
		{
			return toLongObject(x);
		}
		catch (final Exception e)
		{
			return defaultValue;
		}
	}

	public static final Long toLongObject(Object x, long defaultValue)
	{
		if (x == null)
		{
			return toLongObject(defaultValue);
		}

		try
		{
			return toLongObject(x);
		}
		catch (final Exception e)
		{
			return toLongObject(defaultValue);
		}
	}

	// ------------------------------------------------------------------------------
	// Functions for working with BigDecimal
	// ------------------------------------------------------------------------------

	public static final BigDecimal toBigDecimal(Object x)
	{
		if (x == null)
		{
			throw new NumberFormatException("The object must not be null");
		}

		if (x instanceof BigDecimal)
		{
			return (BigDecimal) x;
		}
		else if (x instanceof Number)
		{
			return new BigDecimal(((Number) x).doubleValue());
		}

		return new BigDecimal(StringUtil.trim(x));
	}

	public static final BigDecimal toBigDecimal(Object x, int defaultValue)
	{
		if (x == null)
		{
			return new BigDecimal((double) defaultValue);
		}

		try
		{
			return toBigDecimal(x);
		}
		catch (final Exception e)
		{
			return new BigDecimal((double) defaultValue);
		}
	}

	public static final BigDecimal toBigDecimal(Object x, BigDecimal defaultValue)
	{
		if (x == null)
		{
			return defaultValue;
		}

		try
		{
			return toBigDecimal(x);
		}
		catch (final Exception e)
		{
			return defaultValue;
		}
	}

	// ------------------------------------------------------------------------------
	// Math functions
	// ------------------------------------------------------------------------------

	public static final float getMax(float x1, float x2)
	{
		return Math.max(x1, x2);
	}

	public static final long getMax(long x1, long x2)
	{
		return Math.max(x1, x2);
	}

	public static final double getMax(double x1, double x2)
	{
		return Math.max(x1, x2);
	}

	public static final int getMax(int x1, int x2)
	{
		return Math.max(x1, x2);
	}

	public static final int getMax(Number x1, int x2)
	{
		if (x1 == null)
		{
			return x2;
		}

		return getMax(x1.intValue(), x2);
	}

	public static final Integer getMax(Integer x1, Integer x2)
	{
		if (x1 == null && x2 == null)
		{
			return null;
		}

		if (x1 == null)
		{
			return x2;
		}

		if (x2 == null)
		{
			return x1;
		}

		return x1.compareTo(x2) <= 0 ? x2 : x1;
	}

	public static final Double getMax(Double x1, Double x2)
	{
		if (x1 == null && x2 == null)
		{
			return null;
		}

		if (x1 == null)
		{
			return x2;
		}

		if (x2 == null)
		{
			return x1;
		}

		return x1.compareTo(x2) <= 0 ? x2 : x1;
	}

	public static final Long getMax(Long x1, Long x2)
	{
		if (x1 == null && x2 == null)
		{
			return null;
		}

		if (x1 == null)
		{
			return x2;
		}

		if (x2 == null)
		{
			return x1;
		}

		return x1.compareTo(x2) <= 0 ? x2 : x1;
	}

	public static final Float getMax(Float x1, Float x2)
	{
		if (x1 == null && x2 == null)
		{
			return null;
		}

		if (x1 == null)
		{
			return x2;
		}

		if (x2 == null)
		{
			return x1;
		}

		return x1.compareTo(x2) <= 0 ? x2 : x1;
	}

	public static final BigDecimal getMax(Object x1, Object x2)
	{
		if (x1 instanceof Integer && x2 instanceof Integer)
		{
			return toBigDecimal(getMax((Integer) x1, (Integer) x2));
		}
		else if (x1 instanceof Double && x2 instanceof Double)
		{
			return toBigDecimal(getMax((Double) x1, (Double) x2));
		}
		else if (x1 instanceof Long && x2 instanceof Long)
		{
			return toBigDecimal(getMax((Long) x1, (Long) x2));
		}
		else if (x1 instanceof Float && x2 instanceof Float)
		{
			return toBigDecimal(getMax((Float) x1, (Float) x2));
		}
		else
		{
			return getMax(toBigDecimal(x1, null), toBigDecimal(x2, null));
		}
	}

	public static final BigDecimal getMax(BigDecimal x1, BigDecimal x2)
	{
		if (x1 == null && x2 == null)
		{
			return null;
		}

		if (x1 == null)
		{
			return x2;
		}

		if (x2 == null)
		{
			return x1;
		}

		return x1.max(x2);
	}

	public static final float getMin(float x1, float x2)
	{
		return Math.min(x1, x2);
	}

	public static final long getMin(long x1, long x2)
	{
		return Math.min(x1, x2);
	}

	public static final double getMin(double x1, double x2)
	{
		return Math.min(x1, x2);
	}

	public static final int getMin(int x1, int x2)
	{
		return Math.min(x1, x2);
	}

	public static final int getMin(Number x1, int x2)
	{
		if (x1 == null)
		{
			return x2;
		}

		return getMin(x1.intValue(), x2);
	}

	public static final Integer getMin(Integer x1, Integer x2)
	{
		if (x1 == null && x2 == null)
		{
			return null;
		}

		if (x1 == null)
		{
			return x2;
		}

		if (x2 == null)
		{
			return x1;
		}

		return x1.compareTo(x2) <= 0 ? x1 : x2;
	}

	public static final Double getMin(Double x1, Double x2)
	{
		if (x1 == null && x2 == null)
		{
			return null;
		}

		if (x1 == null)
		{
			return x2;
		}

		if (x2 == null)
		{
			return x1;
		}

		return x1.compareTo(x2) <= 0 ? x1 : x2;
	}

	public static final Long getMin(Long x1, Long x2)
	{
		if (x1 == null && x2 == null)
		{
			return null;
		}

		if (x1 == null)
		{
			return x2;
		}

		if (x2 == null)
		{
			return x1;
		}

		return x1.compareTo(x2) <= 0 ? x1 : x2;
	}

	public static final Float getMin(Float x1, Float x2)
	{
		if (x1 == null && x2 == null)
		{
			return null;
		}

		if (x1 == null)
		{
			return x2;
		}

		if (x2 == null)
		{
			return x1;
		}

		return x1.compareTo(x2) <= 0 ? x1 : x2;
	}

	public static final BigDecimal getMin(Object x1, Object x2)
	{
		if (x1 instanceof Integer && x2 instanceof Integer)
		{
			return toBigDecimal(getMin((Integer) x1, (Integer) x2));
		}
		else if (x1 instanceof Double && x2 instanceof Double)
		{
			return toBigDecimal(getMin((Double) x1, (Double) x2));
		}
		else if (x1 instanceof Long && x2 instanceof Long)
		{
			return toBigDecimal(getMin((Long) x1, (Long) x2));
		}
		else if (x1 instanceof Float && x2 instanceof Float)
		{
			return toBigDecimal(getMin((Float) x1, (Float) x2));
		}
		else
		{
			return getMin(toBigDecimal(x1, null), toBigDecimal(x2, null));
		}
	}

	public static final BigDecimal getMin(BigDecimal x1, BigDecimal x2)
	{
		if (x1 == null && x2 == null)
		{
			return null;
		}

		if (x1 == null)
		{
			return x2;
		}

		if (x2 == null)
		{
			return x1;
		}

		return x1.min(x2);
	}

	// ------------------------------------------------------------------------------
	// Sum
	// ------------------------------------------------------------------------------

	public static Integer sum(Integer x1, Integer x2)
	{
		if (x1 == null && x2 == null)
		{
			return ZERO_INTEGER;
		}

		if (x1 == null)
		{
			return x2;
		}

		if (x2 == null)
		{
			return x1;
		}

		return toIntegerObject(x1.intValue() + x2.intValue());
	}

	public static Double sum(Double x1, Double x2)
	{
		if (x1 == null && x2 == null)
		{
			return ZERO_DOUBLE;
		}

		if (x1 == null)
		{
			return x2;
		}

		if (x2 == null)
		{
			return x1;
		}

		return new Double(x1.doubleValue() + x2.doubleValue());
	}

	public static Long sum(Long x1, Long x2)
	{
		if (x1 == null && x2 == null)
		{
			return ZERO_LONG;
		}

		if (x1 == null)
		{
			return x2;
		}

		if (x2 == null)
		{
			return x1;
		}

		return toLongObject(x1.longValue() + x2.longValue());
	}

	public static Float sum(Float x1, Float x2)
	{
		if (x1 == null && x2 == null)
		{
			return ZERO_FLOAT;
		}

		if (x1 == null)
		{
			return x2;
		}

		if (x2 == null)
		{
			return x1;
		}

		return new Float(x1.floatValue() + x2.floatValue());
	}

	public static Number sum(Number x1, Number x2)
	{
		if (x1 == null && x2 == null)
		{
			return ZERO_DOUBLE;
		}

		if (x1 == null)
		{
			return x2;
		}

		if (x2 == null)
		{
			return x1;
		}

		return new Double(x1.doubleValue() + x2.doubleValue());
	}

	public static Integer sum(Integer x1, int x2)
	{
		if (x1 == null)
		{
			return toIntegerObject(x2);
		}

		return toIntegerObject(x1.intValue() + x2);
	}

	public static BigDecimal sum(Number x1, double x2)
	{
		return sum((Object) x1, new BigDecimal(x2));
	}

	public static BigDecimal sum(Object x1, Object x2)
	{
		if( x1 == null && x2 == null ){
			return ZERO_BIGDECIMAL;
		}
		
		if( x1 == null ){
			return toBigDecimal(x2);
		}
		
		if( x2 == null ){
			toBigDecimal(x1);
		}
		
		return sum(toBigDecimal(x1), toBigDecimal(x2));
	}

	public static BigDecimal sum(BigDecimal x1, BigDecimal x2)
	{
		if (x1 == null)
		{
			return (x2 == null) ? ZERO_BIGDECIMAL : x2;
		}
		return x1.add(x2);
	}
	
	
	// ------------------------------------------------------------------------------
	// Difference
	// ------------------------------------------------------------------------------
	
	public static Number difference(Number x1, Number x2)
	{
		if (x1 == null && x2 == null)
		{
			return ZERO_DOUBLE;
		}

		if (x1 == null)
		{
			return 0 - x2.doubleValue();
		}

		if (x2 == null)
		{
			return x1;
		}

		return new Double(x1.doubleValue() - x2.doubleValue());
	}


	// ------------------------------------------------------------------------------
	// Percent
	// ------------------------------------------------------------------------------

	public final static double percentChange(Integer oldValue, Integer newValue)
	{
		return percentChange(toDouble(oldValue), toDouble(newValue));
	}

	public final static double percentChange(Double oldValue, Double newValue)
	{
		return percentChange(toDouble(oldValue), toDouble(newValue));
	}

	public final static double percentChange(int oldValue, int newValue)
	{
		return percentChange((double) oldValue, (double) newValue);
	}

	public final static double percentChange(double oldValue, double newValue)
	{
		if (oldValue == 0)
		{
			return 0;
		}
		else
		{
			return ((newValue - oldValue) / oldValue) * 100;
		}
	}

	public final static int compare(Double x1, Double x2)
	{
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

	public static int compare(Integer x1, Integer x2)
	{
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

	public static int compare(Long x1, Long x2)
	{
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

	public static int compare(Float x1, Float x2)
	{
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

	public static int compare(Object x1, Object x2)
	{
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

		if (x1 instanceof Integer && x2 instanceof Integer)
		{
			return compare((Integer) x1, (Integer) x2);
		}
		else if (x1 instanceof Double && x2 instanceof Double)
		{
			return compare((Double) x1, (Double) x2);
		}
		else if (x1 instanceof Long && x2 instanceof Long)
		{
			return compare((Long) x1, (Long) x2);
		}
		else if (x1 instanceof Float && x2 instanceof Float)
		{
			return compare((Float) x1, (Float) x2);
		}
		else
		{
			return compare(toBigDecimal(x1), toBigDecimal(x2));
		}
	}

	public static int compare(BigDecimal x1, BigDecimal x2)
	{
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

	public static boolean booleanCompare(Double x1, Double x2, String operator)
	{
		if (StringUtil.equals(operator, "<"))
		{
			return compare(x1, x2) < 0;
		}
		else if (StringUtil.equals(operator, "<="))
		{
			return compare(x1, x2) <= 0;
		}
		else if (StringUtil.equals(operator, ">"))
		{
			return compare(x1, x2) > 0;
		}
		else if (StringUtil.equals(operator, ">="))
		{
			return compare(x1, x2) >= 0;
		}
		else if (StringUtil.equals(operator, "!="))
		{
			return compare(x1, x2) != 0;
		}
		else if (StringUtil.equals(operator, "=="))
		{
			return compare(x1, x2) == 0;
		}
		else
		{
			return false;
		}
	}

	public static boolean booleanCompare(Integer x1, Integer x2, String operator)
	{
		if (StringUtil.equals(operator, "<"))
		{
			return compare(x1, x2) < 0;
		}
		else if (StringUtil.equals(operator, "<="))
		{
			return compare(x1, x2) <= 0;
		}
		else if (StringUtil.equals(operator, ">"))
		{
			return compare(x1, x2) > 0;
		}
		else if (StringUtil.equals(operator, ">="))
		{
			return compare(x1, x2) >= 0;
		}
		else if (StringUtil.equals(operator, "!="))
		{
			return compare(x1, x2) != 0;
		}
		else if (StringUtil.equals(operator, "=="))
		{
			return compare(x1, x2) == 0;
		}
		else
		{
			return false;
		}
	}

	public static boolean booleanCompare(Object x1, Object x2, String operator)
	{
		if (StringUtil.equals(operator, "<"))
		{
			return compare(x1, x2) < 0;
		}
		else if (StringUtil.equals(operator, "<="))
		{
			return compare(x1, x2) <= 0;
		}
		else if (StringUtil.equals(operator, ">"))
		{
			return compare(x1, x2) > 0;
		}
		else if (StringUtil.equals(operator, ">="))
		{
			return compare(x1, x2) >= 0;
		}
		else if (StringUtil.equals(operator, "!="))
		{
			return compare(x1, x2) != 0;
		}
		else if (StringUtil.equals(operator, "=="))
		{
			return compare(x1, x2) == 0;
		}
		else
		{
			return false;
		}
	}

	// This method fixes (to a certain extent)the inaccuracy when formatting  
	// BigDecimals.  Due to class NumberFormat converting to a double in order to
	// perform formatting, accuracy is lost when dealing with large numbers.
	// Numbers less than 1 quadrillion are fine.
	public static String toString(Number num, NumberFormat nf)
	{
		if (num == null)
		{
			return "";
		}

		if (num instanceof BigDecimal)
		{
			if (booleanCompare(num, NumberUtil.QUADRILLION, "<"))
			{
				return nf.format(num);
			}
			else
			{
				return num.toString();
			}
		}

		return nf.format(num);
	}

	public static BigDecimal increment(Object x)
	{
		if (x == null)
		{
			return ONE_BIGDECIMAL;
		}

		return sum(x, ONE_BIGDECIMAL);
	}

	public static Integer increment(Integer x)
	{
		if (x == null)
		{
			return ONE_INTEGER;
		}

		return toIntegerObject(x.intValue() + 1);
	}

	public static Long increment(Long x)
	{
		if (x == null)
		{
			return ONE_LONG;
		}

		return toLongObject(x.longValue() + 1);
	}

	public static Double increment(Double x)
	{
		if (x == null)
		{
			return ONE_DOUBLE;
		}

		return toDoubleObject(x.doubleValue() + 1);
	}

	public static Float increment(Float x)
	{
		if (x == null)
		{
			return ONE_FLOAT;
		}

		return toFloatObject(x.floatValue() + 1);
	}

	// ------------------------------------------------------------------------------
	// Conversions
	// ------------------------------------------------------------------------------

	public static final double toMegabyte(double bytes)
	{
		return bytes / (kilobytes * kilobytes);
	}

	public static final double toGigabyte(double bytes)
	{
		return bytes / (kilobytes * kilobytes * kilobytes);
	}

	public static final Double toMegabyte(Double bytes)
	{
		if (bytes == null)
		{
			return null;
		}
		return new Double(toMegabyte(bytes.doubleValue()));
	}

	public static final Double toGigabyte(Double bytes)
	{
		if (bytes == null)
		{
			return null;
		}
		return new Double(toGigabyte(bytes.doubleValue()));
	}

}
