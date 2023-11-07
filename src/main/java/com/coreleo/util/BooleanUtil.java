/**
 *
 */
package com.coreleo.util;

/**
 * @author Leon Samaroo
 *
 */
public class BooleanUtil {

	private BooleanUtil() {
		super();
	}

	public static final boolean isFalse(final Object x) {
		return toBoolean(x, false) == false;
	}

	public static final boolean isTrue(final Object x) {
		return toBoolean(x, false) == true;
	}

	public static final boolean isEqual(final Boolean x1, final boolean x2) {
		return BooleanUtil.isEqual(x1, Boolean.valueOf(x2));
	}

	public static final boolean isEqual(final Boolean x1, final Boolean x2) {
		if ((x1 == null) || (x2 == null)) {
			return false;
		}

		return x1.equals(x2);
	}

	public static final boolean isNotEqual(final Boolean x1, final boolean x2) {
		return (!isEqual(x1, x2));
	}

	public static final boolean isNotEqual(final Boolean x1, final Boolean x2) {
		return (!isEqual(x1, x2));
	}

	public static final boolean toBoolean(final Boolean x) {
		if (x == null) {
			throw new IllegalArgumentException("The object cannot not be null");
		}

		return x.booleanValue();
	}

	public static final boolean toBoolean(final Object x, final boolean defaultValue) {
		if (x == null) {
			return defaultValue;
		}

		try {
			return toBoolean(x);
		}
		catch (final Exception e) {
			return defaultValue;
		}
	}

	/**
	 *
	 * @throws IllegalArgumentException
	 *             if the default value is null.
	 */
	public static final boolean toBoolean(final Object x, final Boolean defaultValue) {
		if (defaultValue == null) {
			throw new IllegalArgumentException("The default value cannot be null.");
		}

		if (x == null) {
			return defaultValue.booleanValue();
		}

		try {
			return toBoolean(x);
		}
		catch (final Exception e) {
			return defaultValue.booleanValue();
		}
	}

	public static final Boolean toBooleanObject(final Object x, final boolean defaultValue) {
		if (x == null) {
			return toBooleanObject(defaultValue);
		}

		try {
			return toBooleanObject(x);
		}
		catch (final Exception e) {
			return toBooleanObject(defaultValue);
		}
	}

	public static final Boolean toBooleanObject(final Object x, final Boolean defaultValue) {
		if (x == null) {
			return defaultValue;
		}

		try {
			return toBooleanObject(x);
		}
		catch (final Exception e) {
			return defaultValue;
		}
	}

	public static final Boolean toBooleanObject(final boolean x) {
		return (x ? Boolean.TRUE : Boolean.FALSE);
	}

	public static final Boolean toBooleanObject(final Object x) {
		if (x instanceof Boolean) {
			return (Boolean) x;
		}

		return Boolean.valueOf(toBoolean(x));
	}

	/**
	 *
	 * @param x
	 * @return true if parameter is (case-insensitive) true, t, 1, y, yes
	 */
	public static final boolean toBoolean(final Object x) {
		if (x == null) {
			throw new IllegalArgumentException("The object must not be null");
		}

		if (x instanceof Boolean) {
			return ((Boolean) x).booleanValue();
		}

		final String trimmedCaseInsensitive = x.toString().trim().toLowerCase();
		return StringUtil.equalsAny(trimmedCaseInsensitive, "true", "t", "1", "y", "yes");
	}

	/**
	 * @deprecated - simply use toBooleanObject
	 *
	 *             If the argument is the String yes or y (case doesn't matter)
	 *             then it returns Boolean.TRUE, otherwise Boolean.False
	 *
	 */
	@Deprecated
	public static final Boolean toBooleanObjectIgnoreCaseYesNoYN(final Object yesNoYNString) {
		if (yesNoYNString == null) {
			return Boolean.FALSE;
		}

		final String trimmedCaseInsensitiveString = yesNoYNString.toString().trim().toLowerCase();
		if (trimmedCaseInsensitiveString.equals("yes") || trimmedCaseInsensitiveString.equals("y")) {
			return Boolean.TRUE;
		}

		return Boolean.FALSE;

	}

	/**
	 * @deprecated - use toBoolean
	 */
	@Deprecated
	public static final boolean toBooleanIgnoreCaseYesNoYN(final Object yesNoYNString) {
		return toBooleanObjectIgnoreCaseYesNoYN(yesNoYNString).booleanValue();
	}

	public static final boolean isTrueFalseString(final String string) {
		if (StringUtil.isEmpty(string)) {
			return false;
		}

		if (string.equalsIgnoreCase("true") || string.equalsIgnoreCase("false")) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 *
	 * @return the String "Yes" or "No" based on true and false respectively.
	 */
	public static final String toStringYesNo(final Boolean bool) {
		if (bool == null) {
			return "No";
		}

		if (bool.booleanValue()) {
			return "Yes";
		}
		else {
			return "No";
		}
	}

	/**
	 *
	 * @return the String "Yes" or "No" based on true and false respectively.
	 */
	public static final String toStringYesNo(final boolean bool) {
		return toStringYesNo(Boolean.valueOf(bool));
	}

	/**
	 *
	 * @return the String "Y" or "N" based on true and false respectively.
	 */
	public static final String toStringYN(final Boolean bool) {
		if (bool == null) {
			return "N";
		}

		if (bool.booleanValue()) {
			return "Y";
		}
		else {
			return "N";
		}
	}

	/**
	 *
	 * @return the String "Y" or "N" based on true and false respectively.
	 */
	public static final String toStringYN(final boolean bool) {
		return toStringYN(Boolean.valueOf(bool));
	}

}
