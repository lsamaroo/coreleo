/**
 *
 */
package com.coreleo.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import com.coreleo.util.closure.Block;

/**
 * @author Leon Samaroo
 *
 */

@SuppressWarnings({ "rawtypes", "unchecked" })
public final class CollectionUtil {

	private CollectionUtil() {
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
		final List<List<T>> parts = new ArrayList<List<T>>();
		final int N = list.size();
		for (int i = 0; i < N; i += subListLength) {
			if (subListAsView) {
				parts.add(list.subList(i, Math.min(N, i + subListLength)));
			}
			else {
				parts.add(new ArrayList<T>(list.subList(i, Math.min(N, i + subListLength))));
			}
		}
		return parts;
	}

	public static final boolean contains(final List x, final Object value) {
		if (x != null) {
			return x.contains(value);
		}

		return false;
	}

	public static final boolean remove(final List x, final Object value) {
		if (x != null) {
			return x.remove(value);
		}

		return false;
	}

	public static final void clear(final List x) {
		if (x != null) {
			x.clear();
		}
	}

	/**
	 * Invokes the given Block on each item in the List
	 */
	public static final void forEach(final List x, final Block b) {
		for (int i = 0; i < x.size(); i++) {
			b.invoke(x.get(i));
		}
	}

	/**
	 * Invokes the given Block on each item in the Collection
	 */
	public static final void forEach(final Collection x, final Block b) {
		for (final Iterator i = x.iterator(); i.hasNext();) {
			b.invoke(i.next());
		}
	}

	public static <T> T getFirst(final Collection<T> x) {
		return get(x, 0);
	}

	public static final <T> T get(final Collection<T> x, final int index) {
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

	public static final <T> T get(final List<T> x, final int index) {
		if (isEmpty(x)) {
			return null;
		}

		if (index < 0 || index >= x.size()) {
			return null;
		}

		return x.get(index);
	}

	public static final int size(final Collection x) {
		if (x == null) {
			return 0;
		}

		return x.size();
	}

	public static final boolean isInbounds(final Collection x, final Object ix) {
		if (x == null) {
			return false;
		}

		final int index = NumberUtil.toInteger(ix, -1);

		if (!(index < 0 || index >= x.size())) {
			return true;
		}

		return false;
	}

	public static final boolean isInbounds(final Collection<?> x, final int index) {
		if (x == null) {
			return false;
		}

		if (!(index < 0 || index >= x.size())) {
			return true;
		}

		return false;
	}

	public static final boolean isEmpty(final Object x) {
		if (x == null) {
			return true;
		}

		if (x instanceof Collection) {
			return ((Collection) x).size() <= 0;
		}

		return true;
	}

	public static final boolean isNotEmpty(final Object x) {
		return !isEmpty(x);
	}

	public static final List<String> commaDelimitedStringToList(final String x) {
		return DelimiterUtil.delimitedStringToList(x, ",");
	}

	public static final List<String> delimitedStringToList(final String x, final String delimiter) {
		return DelimiterUtil.delimitedStringToList(x, delimiter);
	}

	public static final String toCommaDelimitedString(final Collection x) {
		return DelimiterUtil.toDelimitedString(x, ",");
	}

	public static final String toDelimitedString(final Collection x, final String delimiter) {
		return DelimiterUtil.toDelimitedString(x, delimiter);
	}

	public static final String toCommaDelimitedString(final List x) {
		return DelimiterUtil.toDelimitedString(x, ",");
	}

	public static final String toDelimitedString(final List x, final String delimiter) {
		return DelimiterUtil.toDelimitedString(x, delimiter);
	}

	public static final String toDelimitedString(final List x, final String delimiter, final Block alter) {
		return DelimiterUtil.toDelimitedString(x, delimiter, alter);
	}

	public static final List<Integer> delimitedStringToIntegerObjectList(final String x, final String delimiter) {
		return DelimiterUtil.delimitedStringToIntegerObjectList(x, delimiter);
	}

	public static final List<Integer> commaDelimitedStringToIntegerObjectList(final String x) {
		return DelimiterUtil.delimitedStringToIntegerObjectList(x, ",");
	}

	public static final boolean compare(final Collection x1, final Collection x2) {
		boolean isEqual = false;
		ArrayList list1 = null;
		ArrayList list2 = null;
		// converting the collections into ArrayList inorder to apply sort(),
		// binary search and
		// since it implemnts randomAccess interface ,binary search takes
		// O(log(n))time
		list1 = (x1 == null) ? new ArrayList() : new ArrayList(x1);
		list2 = (x2 == null) ? new ArrayList() : new ArrayList(x2);
		Collections.sort(list2);// list2 has to be sorted before applying
		// binarySearch()
		if (list1.size() == list2.size()) {
			if (list1.size() == 0) {
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

	public static final String[] toStringArray(final Collection x) {
		final String[] strings = new String[x.size()];
		int count = 0;

		for (final Iterator i = x.iterator(); i.hasNext();) {
			strings[count++] = String.valueOf(i.next());
		}

		return strings;
	}

	public static final String[] toStringArray(final List x) {
		final String[] strings = new String[x.size()];

		for (int i = 0; i < x.size(); i++) {
			strings[i] = String.valueOf(x.get(i));
		}

		return strings;
	}

	public static final List toList(final Collection c) {
		if (c == null) {
			return null;
		}

		if (c instanceof List) {
			return (List) c;
		}

		return new ArrayList(c);
	}

	public static final List<Object> toList(final Object... x) {
		if (x == null) {
			return null;
		}

		return Arrays.asList(x);
	}

	public static final List toIntegerObjectList(final Object... x) throws NumberFormatException {
		if (x == null) {
			return null;
		}

		final List list = new ArrayList();

		for (final Object element : x) {
			list.add(NumberUtil.toIntegerObject(element));
		}

		return list;
	}

	public static final List merge(final Collection x1, final Collection x2) {
		if (x1 == null && x2 == null) {
			return null;
		}

		final ArrayList list = new ArrayList();

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
			return (List) x;
		}
		else {
			final List<T> list = new ArrayList<T>(x);
			Collections.sort(list);
			return list;
		}
	}

	public static final Collection sort(final Collection x, final Comparator c) {
		if (x == null) {
			return null;
		}

		if (c == null) {
			return x;
		}

		if (x instanceof List) {
			Collections.sort((List) x, c);
			return x;
		}
		else {
			final List list = new ArrayList(x);
			Collections.sort(list, c);
			return list;
		}
	}

	public static final List sort(final List list, final Comparator c) {
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
