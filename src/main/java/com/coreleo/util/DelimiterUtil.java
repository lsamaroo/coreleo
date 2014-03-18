package com.coreleo.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;
import java.util.StringTokenizer;

import com.coreleo.util.closure.Block;

public class DelimiterUtil {

	static final List<String> delimitedStringToList(String x, String delimiter) {
		if (x == null) {
			return null;
		}

		if (delimiter == null) {
			final List<String> list = new ArrayList<String>();
			list.add(x);
			return list;
		}

		final List<String> list = new ArrayList<String>();
		for (final StringTokenizer i = new StringTokenizer(x, delimiter); i.hasMoreTokens();) {
			final String nextToken = i.nextToken();
			if (StringUtil.isNotEmpty(nextToken)) {
				list.add(StringUtil.trim(nextToken));
			}
		}
		return list;
	}

	static final List<Integer> delimitedStringToIntegerObjectList(String x, String delimiter) {
		if (x == null || delimiter == null) {
			return null;
		}

		final List<Integer> list = new ArrayList<Integer>();
		final Enumeration<?> enumerator = new StringTokenizer(x, delimiter);
		while (enumerator.hasMoreElements()) {
			list.add(NumberUtil.toIntegerObject(enumerator.nextElement()));
		}
		return list;
	}

	static final Integer[] delimitedStringToIntegerObjectArray(String x, String delimiter) {
		final List<Integer> list = delimitedStringToIntegerObjectList(x, delimiter);
		return list.toArray(new Integer[list.size()]);
	}

	static final int[] delimitedStringToIntegerArray(String x, String delimiter) {
		List<String> list = delimitedStringToList(x, delimiter);
		return ArrayUtil.toIntegerArray(list);
	}

	static final String toDelimitedString(int[] x, String delimiter) {
		if (x == null) {
			return null;
		}

		final StringBuilder sb = new StringBuilder();
		for (int i = 0; i < x.length; i++) {
			sb.append(x[i]);
			if (i < x.length - 1) {
				sb.append(delimiter);
			}
		}
		return sb.toString();
	}


	static final String toDelimitedString(Collection<?> x, String delimiter) {
		if (x == null) {
			return null;
		}

		return toDelimitedString( x.toArray(), delimiter );
	}
	
	static final String toDelimitedString(Object[] x, String delimiter) {
		if (x == null) {
			return null;
		}

		final StringBuilder sb = new StringBuilder();
		for (int i = 0; i < x.length; i++) {
			sb.append(x[i]);
			if (i < x.length - 1) {
				sb.append(delimiter);
			}
		}
		return sb.toString();
	}

	static final String toDelimitedString(Collection<?> x, String delimiter, Block alter) {
		if (x == null) {
			return null;
		}
		return toDelimitedString( x.toArray(), delimiter, alter );
	}
	
	static final String toDelimitedString(Object[] x, String delimiter, Block alter) {
		if (x == null) {
			return null;
		}

		final StringBuilder sb = new StringBuilder();
		for (int i = 0; i < x.length; i++) {
			sb.append(alter.invoke(x[i]));
			if (i < x.length - 1) {
				sb.append(delimiter);
			}
		}
		return sb.toString();
	}

}
