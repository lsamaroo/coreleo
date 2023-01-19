package com.coreleo.util;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

import com.coreleo.util.closure.Block;

/**
 *
 * @author Leon Samaroo
 *
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public final class ArrayUtil {

    public static int size(final Object[] x) {
        if (x == null) {
            return 0;
        }

        return x.length;
    }

    /**
     *
     * @param x     - the array
     * @param index - index to validate
     * @return true if index is valid, false otherwise
     */
    public static boolean isInbounds(final Object[] x, final Object index) {
        if (x == null) {
            return false;
        }
        return isInbounds(x, NumberUtil.toInteger(index, -1));
    }

    public static boolean isInbounds(final Object[] x, final int index) {
        if ((x == null) || index < 0 || index >= x.length) {
            return false;
        }

        return true;
    }

    /**
     * @deprecated
     * @see isInbounds
     */
    @Deprecated
    public static boolean isValidIndex(final Object[] x, final int index) {
        return isInbounds(x, index);
    }

    public static String get(final String[] x, final int index, final String defaultValue) {
        if (x == null) {
            return null;
        }

        if (index < 0 || index >= x.length) {
            return defaultValue;
        }

        return x[index];
    }

    public static Object get(final Object[] x, final int index, final Object defaultValue) {
        if (x == null) {
            return null;
        }

        if (index < 0 || index >= x.length) {
            return defaultValue;
        }

        return x[index];
    }

    public static String get(final String[] x, final int index) {
        if ((x == null) || index < 0 || index >= x.length) {
            return null;
        }

        return x[index];
    }

    public static Object get(final Object[] x, final int index) {
        if ((x == null) || index < 0 || index >= x.length) {
            return null;
        }

        return x[index];
    }

    public static boolean isEmpty(final Object x) {
        if (x == null) {
            return true;
        }

        if (x instanceof Object[]) {
            return ((Object[]) x).length <= 0;
        }

        return true;
    }

    public static boolean isEmpty(final int[] x) {
        if (x != null) {
            return x.length <= 0;
        }

        return true;
    }

    public static boolean isEmpty(final long[] x) {
        if (x != null) {
            return x.length <= 0;
        }

        return true;
    }

    public static boolean isEmpty(final double[] x) {
        if (x != null) {
            return x.length <= 0;
        }

        return true;
    }

    public static boolean isEmpty(final float[] x) {
        if (x != null) {
            return x.length <= 0;
        }

        return true;
    }

    public static boolean isEmpty(final char[] x) {
        if (x != null) {
            return x.length <= 0;
        }

        return true;
    }

    public static boolean isEmpty(final byte[] x) {
        if (x != null) {
            return x.length <= 0;
        }

        return true;
    }

    public static boolean isNotEmpty(final Object x) {
        return !isEmpty(x);
    }

    public static boolean isNotEmpty(final int[] x) {
        return !isEmpty(x);
    }

    public static boolean isNotEmpty(final long[] x) {
        return !isEmpty(x);
    }

    public static boolean isNotEmpty(final double[] x) {
        return !isEmpty(x);
    }

    public static boolean isNotEmpty(final float[] x) {
        return !isEmpty(x);
    }

    public static boolean isNotEmpty(final char[] x) {
        return !isEmpty(x);
    }

    public static boolean isNotEmpty(final byte[] x) {
        return !isEmpty(x);
    }

    public static String[] toStringArray(final int[] x) {
        if (x == null) {
            return null;
        }

        final var strings = new String[x.length];
        for (var i = 0; i < x.length; i++) {
            strings[i] = StringUtil.toString(x[i]);
        }
        return strings;
    }

    public static String[] toStringArray(final long[] x) {
        if (x == null) {
            return null;
        }

        final var strings = new String[x.length];
        for (var i = 0; i < x.length; i++) {
            strings[i] = StringUtil.toString((x[i]));
        }
        return strings;
    }

    public static String[] toStringArray(final char[] x) {
        if (x == null) {
            return null;
        }

        final var strings = new String[x.length];
        for (var i = 0; i < x.length; i++) {
            strings[i] = StringUtil.toString((x[i]));
        }
        return strings;
    }

    public static String[] toStringArray(final double[] x) {
        if (x == null) {
            return null;
        }

        final var strings = new String[x.length];
        for (var i = 0; i < x.length; i++) {
            strings[i] = StringUtil.toString((x[i]));
        }
        return strings;
    }

    public static String[] toStringArray(final Collection x) {
        if (x == null) {
            return null;
        }

        final var strings = new String[x.size()];
        var count = 0;
        for (final Object element : x) {
            strings[count++] = StringUtil.toString(element);
        }

        return strings;
    }

    public static String[] toStringArray(final Object[] x) {
        if (x == null) {
            return null;
        }

        final var strings = new String[x.length];
        for (var i = 0; i < x.length; i++) {
            strings[i] = StringUtil.toString((x[i]));
        }
        return strings;
    }

    // -------------------------------------------------------------------------------------------
    // Integer
    // -------------------------------------------------------------------------------------------

    public static Object getIntegerObject(final Object[] x, final int index) {
        return NumberUtil.toIntegerObject(get(x, index));
    }

    public static int[] toIntegerArray(final Object[] x) throws NumberFormatException {
        if (x == null) {
            return null;
        }

        final var ints = new int[x.length];
        for (var i = 0; i < x.length; i++) {
            ints[i] = NumberUtil.toInteger(x[i]);
        }
        return ints;

    }

    public static int[] toIntegerArray(final Object[] x, final int[] defaultValues) {
        try {
            return toIntegerArray(x);
        } catch (final Exception e) {
            return defaultValues;
        }
    }

    public static Integer[] toIntegerObjectArray(final Object[] x) throws NumberFormatException {
        if (x == null) {
            return null;
        }

        if (x instanceof Integer[]) {
            return (Integer[]) x;
        }

        final var integers = new Integer[x.length];
        for (var i = 0; i < x.length; i++) {
            integers[i] = NumberUtil.toIntegerObject(x[i]);
        }
        return integers;
    }

    public static Integer[] toIntegerObjectArray(final Object[] x, final Integer[] defaultValues) {
        try {
            return toIntegerObjectArray(x);
        } catch (final Exception e) {
            return defaultValues;
        }
    }

    public static Integer[] toIntegerObjectArray(final int[] x) {
        if (x == null) {
            return null;
        }

        final var integers = new Integer[x.length];
        for (var i = 0; i < x.length; i++) {
            integers[i] = NumberUtil.toIntegerObject(x[i]);
        }
        return integers;
    }

    public static Integer[] toIntegerObjectArray(final List x) {
        if (x == null) {
            return null;
        }

        Integer[] array = null;
        try {
            array = (Integer[]) x.toArray(new Integer[x.size()]);
        } catch (final ArrayStoreException ase) {
            // in case this is not a list of Integer objects,
            // then convert the objects to Integers
            array = new Integer[x.size()];
            for (var i = 0; i < x.size(); i++) {
                array[i] = NumberUtil.toIntegerObject(x.get(i));
            }
        }

        return array;
    }

    public static int[] toIntegerArray(final List x) {
        if (x == null) {
            return null;
        }

        final var array = new int[x.size()];
        for (var i = 0; i < x.size(); i++) {
            array[i] = NumberUtil.toInteger(x.get(i));
        }

        return array;
    }

    // -------------------------------------------------------------------------------------------
    // Double
    // -------------------------------------------------------------------------------------------

    public static Object getDoubleObject(final Object[] x, final int index) {
        return NumberUtil.toDoubleObject(get(x, index));
    }

    public static double[] toDoubleArray(final Object[] x) throws NumberFormatException {
        if (x == null) {
            return null;
        }

        final var ints = new double[x.length];
        for (var i = 0; i < x.length; i++) {
            ints[i] = NumberUtil.toDouble(x[i]);
        }
        return ints;

    }

    public static double[] toDoubleArray(final Object[] x, final double[] defaultValues) {
        try {
            return toDoubleArray(x);
        } catch (final Exception e) {
            return defaultValues;
        }
    }

    public static Double[] toDoubleObjectArray(final Object[] x) throws NumberFormatException {
        if (x == null) {
            return null;
        }

        if (x instanceof Double[]) {
            return (Double[]) x;
        }

        final var doubles = new Double[x.length];
        for (var i = 0; i < x.length; i++) {
            doubles[i] = NumberUtil.toDoubleObject(x[i]);
        }
        return doubles;
    }

    public static Double[] toDoubleObjectArray(final Object[] x, final Double[] defaultValues) {
        try {
            return toDoubleObjectArray(x);
        } catch (final Exception e) {
            return defaultValues;
        }
    }

    public static Double[] toDoubleObjectArray(final int[] x) {
        if (x == null) {
            return null;
        }

        final var doubles = new Double[x.length];
        for (var i = 0; i < x.length; i++) {
            doubles[i] = NumberUtil.toDoubleObject(x[i]);
        }
        return doubles;
    }

    public static Double[] toDoubleObjectArray(final List x) {
        if (x == null) {
            return null;
        }

        Double[] array = null;
        try {
            array = (Double[]) x.toArray(new Double[x.size()]);
        } catch (final ArrayStoreException ase) {
            // in case this is not a list of Double objects,
            // then convert the objects to Doubles
            array = new Double[x.size()];
            for (var i = 0; i < x.size(); i++) {
                array[i] = NumberUtil.toDoubleObject(x.get(i));
            }
        }

        return array;
    }

    public static double[] toDoubleArray(final List x) {
        if (x == null) {
            return null;
        }

        final var array = new double[x.size()];
        for (var i = 0; i < x.size(); i++) {
            array[i] = NumberUtil.toDouble(x.get(i));
        }

        return array;
    }

    public static Object[] toObjectArray(final List x) {
        if (x == null) {
            return null;
        }

        return x.toArray(new Object[x.size()]);
    }

    public static String toCommaDelimitedString(final int[] x) {
        return DelimiterUtil.toDelimitedString(x, ",");
    }

    public static final String toCommaDelimitedString(final Object... x) {
        return DelimiterUtil.toDelimitedString(x, ",");
    }

    public static String toDelimitedString(final Object[] x, final String delimiter) {
        return DelimiterUtil.toDelimitedString(x, delimiter);
    }

    public static String toDelimitedString(final Object[] x, final String delimiter, final Block alter) {
        return DelimiterUtil.toDelimitedString(x, delimiter, alter);
    }

    public static Integer[] delimitedStringToIntegerObjectArray(final String x, final String delimiter) {
        return DelimiterUtil.delimitedStringToIntegerObjectArray(x, delimiter);
    }

    public static Integer[] commaDelimitedStringToIntegerObjectArray(final String x) {
        return DelimiterUtil.delimitedStringToIntegerObjectArray(x, ",");
    }

    public static int[] delimitedStringToIntegerArray(final String x, final String delimiter) {
        return DelimiterUtil.delimitedStringToIntegerArray(x, delimiter);
    }

    public static int[] commaDelimitedStringToIntegerArray(final String x) {
        return DelimiterUtil.delimitedStringToIntegerArray(x, ",");
    }

    public static Integer getMax(final Integer[] x) {
        if (x == null) {
            return null;
        }

        var max = x[0];

        for (var i = 1; i < 24; i++) {
            max = NumberUtil.getMax(max, x[i]);
        }

        return max;
    }

    public static Double getMax(final Double[] x) {
        if (x == null) {
            return null;
        }

        var max = x[0];

        for (var i = 1; i < 24; i++) {
            max = NumberUtil.getMax(max, x[i]);
        }

        return max;
    }

    public static BigDecimal getMax(final Object[] x) {
        if (x == null) {
            return null;
        }

        var max = x[0];

        for (var i = 1; i < 24; i++) {
            max = NumberUtil.getMax(max, x[i]);
        }
        return NumberUtil.toBigDecimal(max, null);
    }

    public static Integer getMin(final Integer[] x) {
        if (x == null) {
            return null;
        }

        var min = x[0];

        for (var i = 1; i < 24; i++) {
            min = NumberUtil.getMin(min, x[i]);
        }

        return min;
    }

    public static Double getMin(final Double[] x) {
        if (x == null) {
            return null;
        }

        var min = x[0];

        for (var i = 1; i < 24; i++) {
            min = NumberUtil.getMin(min, x[i]);
        }

        return min;
    }

    public static BigDecimal getMin(final Object[] x) {
        if (x == null) {
            return null;
        }

        var min = x[0];

        for (var i = 1; i < 24; i++) {
            min = NumberUtil.getMin(min, x[i]);
        }

        return NumberUtil.toBigDecimal(min, null);
    }

    public static Integer getSum(final Integer[] x) {
        var total = NumberUtil.ZERO_INTEGER;
        if (x != null) {
            for (final Integer element : x) {
                total = NumberUtil.sum(total, element);
            }
        }
        return total;
    }

    public static Double getSum(final Double[] x) {
        var total = NumberUtil.ZERO_DOUBLE;
        if (x != null) {
            for (final Double element : x) {
                total = NumberUtil.sum(total, element);
            }
        }
        return total;
    }

    public static BigDecimal getSum(final Object[] x) {
        var total = NumberUtil.ZERO_BIGDECIMAL;
        if (x != null) {
            for (final Object element : x) {
                total = NumberUtil.sum(total, NumberUtil.toBigDecimal(element, 0));
            }
        }
        return total;
    }

    public static Object[] clone(final Object[] x) {
        if (x == null) {
            return null;
        }
        return x.clone();
    }

    public static int[] clone(final int[] x) {
        if (x == null) {
            return null;
        }
        return x.clone();
    }

    public static char[] clone(final char[] x) {
        if (x == null) {
            return null;
        }
        return x.clone();
    }

    public static byte[] clone(final byte[] x) {
        if (x == null) {
            return null;
        }
        return x.clone();
    }

    public static boolean isIntegerArray(final Object[] x) {
        if (x == null) {
            return false;
        }

        try {
            for (final Object element : x) {
                NumberUtil.toInteger(element);
            }
        } catch (final Exception e) {
            return false;
        }

        return true;
    }

    public static boolean contains(final Object[] xArray, final Object x) {
        if ((xArray == null) || (x == null)) {
            return false;
        }

        for (final Object element : xArray) {
            if (x.equals(element)) {
                return true;
            }
        }

        return false;
    }

    public static Object[] add(final Object[] xArray, final Object x) {
        Object[] newArray = null;
        if (xArray == null) {
            newArray = new Object[1];
        } else {
            newArray = (Object[]) expand(xArray, xArray.length + 1);
        }

        newArray[newArray.length - 1] = x;

        return newArray;
    }

    public static Object expand(final Object obj, final int newLength) {
        final Class clazz = obj.getClass();
        if (!clazz.isArray()) {
            return null;
        }
        final var length = Array.getLength(obj);

        final Class componentType = obj.getClass().getComponentType();
        final var newArray = Array.newInstance(componentType, newLength);
        System.arraycopy(obj, 0, newArray, 0, length < newLength ? length : newLength);
        return newArray;
    }

    public static Object[] addAll(final Object[] toThisArray, final Object[] addThisArray) {
        if (toThisArray == null && addThisArray == null) {
            return new Object[] {};
        }

        if (toThisArray == null) {
            return addThisArray;
        }

        if (addThisArray == null) {
            return toThisArray;
        }

        final var newArray = (Object[]) expand(toThisArray, toThisArray.length + addThisArray.length);
        for (var i = toThisArray.length; i < newArray.length; i++) {
            newArray[i] = addThisArray[i - toThisArray.length];
        }

        return newArray;
    }

}
