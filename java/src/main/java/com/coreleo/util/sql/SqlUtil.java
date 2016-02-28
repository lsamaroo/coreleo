package com.coreleo.util.sql;

import org.apache.commons.lang.WordUtils;

import com.coreleo.util.Constants;

public final class SqlUtil {

	public static String commaDelimtedQuestionMarks(final int count) {
		final StringBuilder sb = new StringBuilder();
		for (int i = 0; i < count; i++) {
			sb.append(Constants.QUESTIONMARK);
			if (i < count - 1) {
				sb.append(Constants.COMMA);
			}
		}
		return sb.toString();
	}

	public static String underScoreToCamelCase(final String s) {
		return WordUtils.capitalizeFully(s, new char[] { '_' }).replaceAll("_", "");
	}
}
