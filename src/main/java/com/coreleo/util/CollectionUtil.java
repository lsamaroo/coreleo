/**
 *
 */
package com.coreleo.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.coreleo.util.closure.Block;

/**
 * @author Leon Samaroo
 *
 */

public class CollectionUtil {

    protected CollectionUtil() {
        super();
    }

    /**
     * Splits a list into sublists of length <code>subListLength</code> @ return
     * a List containing the sublists. The sublists are views of the original
     * list.
     */
    public static <T> List<List<T>> split(final List<T> list, final int subListLength) {
        return split(list, subListLength, true);
    }

    /**
     * Splits a list into sublists of length <code>subListLength</code>
     *
     * @param subListAsView
     *            - true if the sublists should remain as views of the original
     *            list, false to create copies. @ return a List containing the
     *            sublists.
     */
    public static <T> List<List<T>> split(final List<T> list, final int subListLength, final boolean subListAsView) {
        final List<List<T>> parts = new ArrayList<>();
        final int size = list.size();
        for (int i = 0; i < size; i += subListLength) {
            if (subListAsView) {
                parts.add(list.subList(i, Math.min(size, i + subListLength)));
            }
            else {
                parts.add(new ArrayList<>(list.subList(i, Math.min(size, i + subListLength))));
            }
        }
        return parts;
    }

    public static <T> boolean contains(final List<T> x, final T value) {
        if (x != null) {
            return x.contains(value);
        }

        return false;
    }

    public static <T> boolean remove(final List<T> x, final T value) {
        if (x != null) {
            return x.remove(value);
        }

        return false;
    }

    public static <T> void clear(final List<T> x) {
        if (x != null) {
            x.clear();
        }
    }

    /**
     * Invokes the given Block on each item in the List
     */
    public static void forEach(final List<?> x, final Block b) {
        for (int i = 0; i < x.size(); i++) {
            b.invoke(x.get(i));
        }
    }

    /**
     * Invokes the given Block on each item in the Collection
     */
    public static void forEach(final Collection<?> x, final Block b) {
        for (final Object element : x) {
            b.invoke(element);
        }
    }

    public static <T> T getFirst(final Collection<T> x) {
        return get(x, 0);
    }

    public static <T> T get(final Collection<T> x, final int index) {
        if (isEmpty(x)) {
            return null;
        }

        if (index < 0 || index >= x.size()) {
            return null;
        }

        int i = 0;
        for (final T t : x) {
            if (i == index) {
                return t;
            }
            i++;
        }
        return null;
    }

    public static <T> T getFirst(final List<T> list) {
        return get(list, 0);
    }

    public static <T> T get(final List<T> x, final int index) {
        if (isEmpty(x)) {
            return null;
        }

        if (index < 0 || index >= x.size()) {
            return null;
        }

        return x.get(index);
    }

    public static int size(final Collection<?> x) {
        if (x == null) {
            return 0;
        }

        return x.size();
    }

    public static boolean isInbounds(final Collection<?> x, final Object ix) {
        if (x == null) {
            return false;
        }

        final int index = NumberUtil.toInteger(ix, -1);

        if (!(index < 0 || index >= x.size())) {
            return true;
        }

        return false;
    }

    public static boolean isInbounds(final Collection<?> x, final int index) {
        if (x == null) {
            return false;
        }

        if (!(index < 0 || index >= x.size())) {
            return true;
        }

        return false;
    }

    public static boolean isEmpty(final Object x) {
        if (x == null) {
            return true;
        }

        if (x instanceof Collection) {
            return ((Collection<?>) x).size() <= 0;
        }

        return true;
    }

    public static boolean isNotEmpty(final Object x) {
        return !isEmpty(x);
    }

    public static List<String> commaDelimitedStringToList(final String x) {
        return DelimiterUtil.delimitedStringToList(x, ",");
    }

    public static List<String> delimitedStringToList(final String x, final String delimiter) {
        return DelimiterUtil.delimitedStringToList(x, delimiter);
    }

    public static String toCommaDelimitedString(final Collection<?> x) {
        return DelimiterUtil.toDelimitedString(x, ",");
    }

    public static String toDelimitedString(final Collection<?> x, final String delimiter) {
        return DelimiterUtil.toDelimitedString(x, delimiter);
    }

    public static String toCommaDelimitedString(final List<?> x) {
        return DelimiterUtil.toDelimitedString(x, ",");
    }

    public static String toDelimitedString(final List<?> x, final String delimiter) {
        return DelimiterUtil.toDelimitedString(x, delimiter);
    }

    public static String toDelimitedString(final List<?> x, final String delimiter, final Block alter) {
        return DelimiterUtil.toDelimitedString(x, delimiter, alter);
    }

    public static List<Integer> delimitedStringToIntegerObjectList(final String x, final String delimiter) {
        return DelimiterUtil.delimitedStringToIntegerObjectList(x, delimiter);
    }

    public static List<Integer> commaDelimitedStringToIntegerObjectList(final String x) {
        return DelimiterUtil.delimitedStringToIntegerObjectList(x, ",");
    }

    public static <T extends Comparable<T>> boolean compare(final Collection<T> x1, final Collection<T> x2) {
        boolean isEqual = false;
        ArrayList<T> list1;
        ArrayList<T> list2;
        // converting the collections into ArrayList inorder to apply sort(),
        // binary search and
        // since it implements randomAccess interface ,binary search takes
        // O(log(n))time
        list1 = (x1 == null) ? new ArrayList<T>() : new ArrayList<>(x1);
        list2 = (x2 == null) ? new ArrayList<T>() : new ArrayList<>(x2);
        Collections.sort(list2);// list2 has to be sorted before applying
        // binarySearch()
        if (list1.size() == list2.size()) {
            if (list1.isEmpty()) {
                isEqual = true;
            }
            else {
                for (int i = 0; i < list1.size(); i++) {
                    if (!(isEqual = Collections.binarySearch(list2, list1.get(i)) >= 0)) {
                        break;
                    }
                }
            }
        }
        return isEqual;
    }

    public static String[] toStringArray(final Collection<?> x) {
        final String[] strings = new String[x.size()];
        int count = 0;

        for (final Object element : x) {
            strings[count++] = String.valueOf(element);
        }

        return strings;
    }

    public static String[] toStringArray(final List<?> x) {
        final String[] strings = new String[x.size()];

        for (int i = 0; i < x.size(); i++) {
            strings[i] = String.valueOf(x.get(i));
        }

        return strings;
    }

    public static <T> List<T> toList(final Collection<T> c) {
        if (c == null) {
            return null;
        }

        if (c instanceof List) {
            return (List<T>) c;
        }

        return new ArrayList<>(c);
    }

    public static List<Object> toList(final Object... x) {
        if (x == null) {
            return null;
        }

        return Arrays.asList(x);
    }

    public static List<Integer> toIntegerObjectList(final Object... x) throws NumberFormatException {
        if (x == null) {
            return null;
        }

        final List<Integer> list = new ArrayList<>();

        for (final Object element : x) {
            list.add(NumberUtil.toIntegerObject(element));
        }

        return list;
    }

    public static <T> List<T> merge(final Collection<T> x1, final Collection<T> x2) {
        if (x1 == null && x2 == null) {
            return null;
        }

        final ArrayList<T> list = new ArrayList<>();

        if (x1 != null) {
            list.addAll(x1);
        }

        if (x2 != null) {
            list.addAll(x2);
        }

        return list;
    }

    public static <T extends Comparable<T>> List<T> sort(final Collection<T> x) {
        if (x == null) {
            return null;
        }

        if (x instanceof List) {
            Collections.sort((List<T>) x);
            return (List<T>) x;
        }
        else {
            final List<T> list = new ArrayList<>(x);
            Collections.sort(list);
            return list;
        }
    }

    public static <T> Collection<T> sort(final Collection<T> x, final Comparator<T> c) {
        if (x == null) {
            return null;
        }

        if (c == null) {
            return x;
        }

        if (x instanceof List) {
            Collections.sort((List<T>) x, c);
            return x;
        }
        else {
            final List<T> list = new ArrayList<>(x);
            Collections.sort(list, c);
            return list;
        }
    }

    public static <T> List<T> sort(final List<T> list, final Comparator<T> c) {
        if (list == null) {
            return null;
        }

        if (c == null) {
            return list;
        }

        Collections.sort(list, c);
        return list;
    }
}
