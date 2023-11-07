/**
 * 
 */
package com.coreleo.util;

/**
 * @author Leon Samaroo
 * 
 */
public final class CharUtil {

	private static final String CHAR_STRING = 
        "\u0000\u0001\u0002\u0003\u0004\u0005\u0006\u0007" +
        "\b\t\n\u000b\f\r\u000e\u000f" +
        "\u0010\u0011\u0012\u0013\u0014\u0015\u0016\u0017" +
        "\u0018\u0019\u001a\u001b\u001c\u001d\u001e\u001f" +
        "\u0020\u0021\"\u0023\u0024\u0025\u0026\u0027" +
        "\u0028\u0029\u002a\u002b\u002c\u002d\u002e\u002f" +
        "\u0030\u0031\u0032\u0033\u0034\u0035\u0036\u0037" +
        "\u0038\u0039\u003a\u003b\u003c\u003d\u003e\u003f" +
        "\u0040\u0041\u0042\u0043\u0044\u0045\u0046\u0047" +
        "\u0048\u0049\u004a\u004b\u004c\u004d\u004e\u004f" +
        "\u0050\u0051\u0052\u0053\u0054\u0055\u0056\u0057" +
        "\u0058\u0059\u005a\u005b\\\u005d\u005e\u005f" +
        "\u0060\u0061\u0062\u0063\u0064\u0065\u0066\u0067" +
        "\u0068\u0069\u006a\u006b\u006c\u006d\u006e\u006f" +
        "\u0070\u0071\u0072\u0073\u0074\u0075\u0076\u0077" +
        "\u0078\u0079\u007a\u007b\u007c\u007d\u007e\u007f";
	private static final String[] CHAR_STRING_ARRAY = new String[128];
	private static final Character[] CHAR_ARRAY = new Character[128];

	static {
		for (int i = 127; i >= 0; i--) {
			CHAR_STRING_ARRAY[i] = CHAR_STRING.substring(i, i + 1);
			CHAR_ARRAY[i] = Character.valueOf((char) i);
		}
	}


	private CharUtil() {
		super();
	}


	public static final Character toCharacterObject(char x) {
		if (x < CHAR_ARRAY.length) {
			return CHAR_ARRAY[x];
		}
		else {
			return Character.valueOf(x);
		}
	}


	public static final Character toCharacterObject(String x) {
		if (StringUtil.isEmpty(x)) {
			return null;
		}
		return toCharacterObject(x.charAt(0));
	}


	public static final char toChar(Character x) {
		if (x == null) {
			throw new IllegalArgumentException("The Character must not be null");
		}
		return x.charValue();
	}


	public static final char toChar(Character x, char defaultValue) {
		if (x == null) {
			return defaultValue;
		}
		return x.charValue();
	}


	public static final char toChar(String x) {
		if (StringUtil.isEmpty(x)) {
			throw new IllegalArgumentException("The String must not be empty");
		}
		return x.charAt(0);
	}


	public static final char toChar(String x, char defaultValue) {
		if (StringUtil.isEmpty(x)) {
			return defaultValue;
		}
		return x.charAt(0);
	}


	public static final int toIntValue(char x) {
		if (isAsciiNumeric(x) == false) {
			throw new IllegalArgumentException("The character " + x + " is not in the range '0' - '9'");
		}
		return (x - 48);
	}


	public static final int toIntValue(char x, int defaultValue) {
		if (isAsciiNumeric(x) == false) {
			return defaultValue;
		}
		return (x - 48);
	}


	public static final int toIntValue(Character x) {
		if (x == null) {
			throw new IllegalArgumentException("The character must not be null");
		}
		return toIntValue(x.charValue());
	}


	public static final int toIntValue(Character x, int defaultValue) {
		if (x == null) {
			return defaultValue;
		}
		return toIntValue(x.charValue(), defaultValue);
	}


	public static final String toString(char x) {
		if (x < 128) {
			return CHAR_STRING_ARRAY[x];
		}
		else {
			return new String(new char[] { x });
		}
	}


	public static final String toString(Character x) {
		if (x == null) {
			return null;
		}
		else {
			return toString(x.charValue());
		}
	}


	public static final boolean isAscii(char x) {
		return (x < 128);
	}


	public static final boolean isAsciiAlpha(char x) {
		return (x >= 'A' && x <= 'Z') || (x >= 'a' && x <= 'z');
	}


	public static final boolean isAsciiAlphaUpper(char x) {
		return (x >= 'A' && x <= 'Z');
	}


	public static boolean isAsciiAlphaLower(char x) {
		return (x >= 'a' && x <= 'z');
	}


	public static final boolean isAsciiNumeric(char x) {
		return (x >= '0' && x <= '9');
	}


	public static final boolean isAsciiAlphanumeric(char x) {
		return (x >= 'A' && x <= 'Z') || (x >= 'a' && x <= 'z') || (x >= '0' && x <= '9');
	}

}
