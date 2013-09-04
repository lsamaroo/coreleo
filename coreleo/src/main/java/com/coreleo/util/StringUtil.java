/**
 * 
 */
package com.coreleo.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.rmi.server.UID;
import java.util.ArrayList;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Leon Samaroo
 * 
 * 
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public final class StringUtil
{

	public final static String SPECIAL_CHARACTERS = "`~!@#$%^&*()-_=+[{]}\\|;:'\",<.>/?";
	private final static String _255 = "(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)";

	// Pre-compiled regex patterns
	private final static Pattern REGEX_WHITESPACE = Pattern.compile("^.*\\s.*$");
	private final static Pattern REGEX_ALPHANUMERIC = Pattern.compile("^\\w+$");
	private final static Pattern REGEX_ALPHANUMERIC_ALLOW_SPACES = Pattern.compile("^[a-zA-Z0-9\\s]*\\w+$");
	private final static Pattern REGEX_ALPHA = Pattern.compile("^[a-zA-Z]+$");
	private final static Pattern REGEX_ALPHA_ALLOW_SPACES = Pattern.compile("^[a-zA-Z\\s]*[a-zA-Z]+$");
	private final static Pattern REGEX_NUMERIC = Pattern.compile("^[0-9]+$");
	private final static Pattern REGEX_NUMERIC_ALLOW_SPACES = Pattern.compile("^[0-9\\s]*[0-9]+$");
	private final static Pattern REGEX_QUESTIONMARK = Pattern.compile("\\?");
	private final static Pattern REGEX_ALPHANUMERIC_ALLOW_SPACES_HYPHEN_UNDERSCORE = Pattern.compile("^[a-zA-Z_0-9\\Q-\\E\\s]*\\w+$");
	private final static Pattern REGEX_ALPHANUMERIC_ALLOW_HYPHEN_UNDERSCORE = Pattern.compile("^[a-zA-Z_0-9\\Q-\\E]*\\w+$");
	private final static Pattern REGEX_ALPHANUMERIC_ALLOW_SPACES_HYPHEN_UNDERSCORE_PERIOD_COMMA = Pattern.compile("^[a-zA-Z_0-9\\Q-\\E.,\\s]*\\w+$");
	private final static Pattern REGEX_EMAIL = Pattern.compile("^[\\w\\-]+(\\.[\\w\\-]+)*@([A-Za-z0-9\\Q-\\E]+\\.)+[A-Za-z]{2,4}$");
	private final static Pattern REGEX_IPADDRESS = Pattern.compile("^(?:" + _255 + "\\.){3}" + _255 + "$");
	private final static Pattern REGEX_DOLLARSIGN = Pattern.compile("\\$");
	private final static Pattern REGEX_HOST_NAME = Pattern.compile("^([A-Za-z0-9\\Q-\\E.]+\\.)+[A-Za-z]{2,4}$");
	private final static Pattern REGEX_USPHONE_NUMBERS = Pattern.compile("([0-9]( |-)?)?(\\(?[0-9]{3}\\)?|[0-9]{3})( |-)?([0-9]{3}( |-)?[0-9]{4})");
	private final static Pattern REGEX_PROPER_CASE = Pattern.compile("(^|\\W)([a-z])");

	private StringUtil()
	{
		super();
	}
	
	
	public static String toProperCase(String x) {
		Matcher m = REGEX_PROPER_CASE.matcher(x.toLowerCase());
		StringBuffer sb = new StringBuffer(x.length());
		while (m.find()) {
			m.appendReplacement(sb, m.group(1) + m.group(2).toUpperCase());
		}
		m.appendTail(sb);
		return sb.toString();
	}
	
	public static boolean isUSPhone(String x){
		if (x == null)
		{
			return false;
		}

		return REGEX_USPHONE_NUMBERS.matcher(x).matches();

		}
	
	

	public static final String repeat(CharSequence str, int numOfTimes)
	{
		final StringBuilder builder = new StringBuilder();
		for (int i = 0; i < numOfTimes; i++)
		{
			builder.append(str);
		}
		return builder.toString();
	}

	public static final String padStringWithCharacter(CharSequence string, char c, int numOfTimes, boolean asPrefix)
	{
		final StringBuilder builder = new StringBuilder();
		for (int i = 0; i < numOfTimes; i++)
		{
			builder.append(c);
		}

		if (asPrefix)
		{
			builder.append(string);
		}
		else
		{
			builder.insert(0, string);
		}

		return builder.toString();
	}

	public static final boolean isPalindrome(Object obj)
	{
		if (obj == null)
		{
			return false;
		}

		final char[] c = String.valueOf(obj).toCharArray();
		int start = 0;
		int end = c.length - 1;
		for (int i = 0; i < c.length / 2; i++)
		{
			if (c[start] != c[end])
			{
				return false;
			}
			start++;
			end--;
		}
		return true;
	}

	public static final String hexToString(String hex)
	{
		if (isEmpty(hex))
		{
			return hex;
		}

		hex = trim(hex);

		final StringBuilder sb = new StringBuilder();

		// 49204c6f7665204a617661 split into two characters 49, 20, 4c...
		for (int i = 0; i < hex.length() - 1; i += 2)
		{

			// grab the hex in pairs
			final String output = hex.substring(i, (i + 2));
			// convert hex to decimal
			final int decimal = Integer.parseInt(output, 16);
			// convert the decimal to character
			sb.append((char) decimal);
		}

		return sb.toString();
	}

	public static final String replaceFirstCharacter(char c, String string, String value)
	{
		final int index = string.indexOf(c);
		final StringBuffer buff = new StringBuffer(string);
		buff.replace(index, index + 1, value);
		return buff.toString();
	}

	public static final String replaceLastCharacter(char c, String string, String value)
	{
		final int index = string.lastIndexOf(c);
		final StringBuffer buff = new StringBuffer(string);
		buff.replace(index, index + 1, value);
		return buff.toString();
	}

	public static final String replaceLastQuestionMark(String string, String value)
	{
		return replaceLastCharacter('?', string, value);
	}

	public static final String deleteFirstQuestionMark(String string)
	{
		return replaceFirstQuestionMark(string, "");
	}

	public static final String replaceFirstQuestionMark(String string, String value)
	{
		return REGEX_QUESTIONMARK.matcher(string).replaceFirst(value);
	}

	public static final String replaceFirstQuestionMark(String string, Object value)
	{
		return replaceFirstQuestionMark(string, String.valueOf(value));
	}

	public static final String replaceFirstQuestionMark(String string, int value)
	{
		return replaceFirstQuestionMark(string, String.valueOf(value));
	}

	public static final String replaceFirstQuestionMark(String string, boolean value)
	{
		return replaceFirstQuestionMark(string, String.valueOf(value));
	}

	public static final String replaceFirstQuestionMark(String string, double value)
	{
		return replaceFirstQuestionMark(string, String.valueOf(value));
	}

	public static final String replaceFirstQuestionMark(String string, long value)
	{
		return replaceFirstQuestionMark(string, String.valueOf(value));
	}

	public static final String replaceFirstQuestionMark(String string, float value)
	{
		return replaceFirstQuestionMark(string, String.valueOf(value));
	}

	public static final String replaceFirstQuestionMark(String string, char value)
	{
		return replaceFirstQuestionMark(string, String.valueOf(value));
	}

	public static final String replaceLastDollarSign(String string, String value)
	{
		return replaceLastCharacter('$', string, value);
	}

	public static final String deleteFirstDollarSign(String string)
	{
		return replaceFirstDollarSign(string, "");
	}

	public static final String replaceFirstDollarSign(String string, String value)
	{
		return REGEX_DOLLARSIGN.matcher(string).replaceFirst(value);
	}

	public static final String replaceFirstDollarSign(String string, Object value)
	{
		return replaceFirstDollarSign(string, String.valueOf(value));
	}

	public static final String replaceFirstDollarSign(String string, int value)
	{
		return replaceFirstDollarSign(string, String.valueOf(value));
	}

	public static final String replaceFirstDollarSign(String string, boolean value)
	{
		return replaceFirstDollarSign(string, String.valueOf(value));
	}

	public static final String replaceFirstDollarSign(String string, double value)
	{
		return replaceFirstDollarSign(string, String.valueOf(value));
	}

	public static final String replaceFirstDollarSign(String string, long value)
	{
		return replaceFirstDollarSign(string, String.valueOf(value));
	}

	public static final String replaceFirstDollarSign(String string, float value)
	{
		return replaceFirstDollarSign(string, String.valueOf(value));
	}

	public static final String replaceFirstDollarSign(String string, char value)
	{
		return replaceFirstDollarSign(string, String.valueOf(value));
	}

	public static final boolean isEmpty(String x)
	{
		if (x == null)
		{
			return true;
		}
		else
		{
			return trim(x).length() <= 0;
		}
	}

	public static final boolean isNotEmpty(String x)
	{
		return !isEmpty(x);
	}

	public static final boolean isEmpty(Object x)
	{
		if (x == null)
		{
			return true;
		}
		else
		{
			return trim(x).length() <= 0;
		}
	}

	public static final boolean isNotEmpty(Object x)
	{
		return !isEmpty(x);
	}

	/**
	 * @return - true if it contains at least one whitespace character as defined by regex--space, tab, etc.
	 */
	public static final boolean constainsWhiteSpace(String str)
	{
		if (str == null)
		{
			return false;
		}

		return REGEX_WHITESPACE.matcher(str).matches();
	}

	/**
	 * 
	 * Check to string to see if the string contains any of the characters specified in the char array.
	 * 
	 * @param string
	 *            - the String to check.
	 * @param characters
	 *            - the character array
	 * @return true if the string contains any of the specified characters, false otherwise.
	 */
	public static final boolean containsAny(String string, char... characters)
	{
		if (string == null || characters == null)
		{
			return false;
		}

		for (final char character : characters)
		{
			if (string.indexOf(character) != -1)
			{
				return true;
			}
		}
		return false;
	}

	/**
	 * 
	 * A convenience method. This method calls <code>containsCharacters( string,
	 * characters.toCharArray() )</code>.
	 * 
	 */
	public static final boolean containsAny(String string, String characters)
	{
		return containsAny(string, characters.toCharArray());
	}

	public static final boolean containsSubString(String x, String subString)
	{
		if (x == null || subString == null)
		{
			return false;
		}

		return x.indexOf(subString) != -1;
	}

	public static final boolean containsSubString(Object x, String subString)
	{
		if (x == null || subString == null)
		{
			return false;
		}

		return toString(x).indexOf(subString) != -1;
	}

	public static final boolean containsSubStringIgnoreCase(String x, String subString)
	{
		if (x == null || subString == null)
		{
			return false;
		}

		return toLowerCase(x).indexOf(subString.toLowerCase()) != -1;
	}

	public static final boolean containsSubStringIgnoreCase(Object x, String subString)
	{
		if (x == null || subString == null)
		{
			return false;
		}

		return toLowerCase(x).indexOf(subString.toLowerCase()) != -1;
	}

	/**
	 * Search a String to find the first index of any character not in the given set of allowed characters.
	 * 
	 * StringUtils.indexOfAnyBut("zzabyycdxx", "za") = 3, because 'b' is the first character that is IN the search string BUT NOT
	 * in the given set of characters.
	 * 
	 */
	public static final int indexOfAnyBut(String str, String allowedCharacters)
	{
		if (str == null || allowedCharacters == null || isEmpty(str) || isEmpty(allowedCharacters))
		{
			return -1;
		}
		for (int i = 0; i < str.length(); i++)
		{
			if (allowedCharacters.indexOf(str.charAt(i)) < 0)
			{
				return i;
			}
		}
		return -1;
	}

	/**
	 * Search a String to find the first index of any character not in the given set of characters.
	 * 
	 * @see indexOfAnyBut(String str, String searchChars)
	 * 
	 */
	public static final int indexOfAnyBut(String str, char[] allowedCharacters)
	{
		return indexOfAnyBut(str, new String(allowedCharacters));
	}

	/**
	 * Checks if the String contains only certain characters.
	 */
	public static final boolean containsOnly(String str, char... validCharacters)
	{
		if ((validCharacters == null) || (str == null))
		{
			return false;
		}

		return containsOnly(str, new String(validCharacters));
	}

	/**
	 * Checks if the String contains only certain characters.
	 */
	public static final boolean containsOnly(String str, String validCharacters)
	{
		if ((validCharacters == null) || (str == null))
		{
			return false;
		}
		if (str.length() == 0)
		{
			return true;
		}
		if (validCharacters.length() == 0)
		{
			return false;
		}
		return indexOfAnyBut(str, validCharacters) == -1;
	}

	/**
	 * 
	 * 
	 * Efficient string replace function. Replaces instances of the substring find with replace in the string subject.
	 * 
	 */
	public static final String replace(String subject, String find, String replace)
	{
		final StringBuffer buf = new StringBuffer();
		final int l = find.length();
		int s = 0;
		int i = subject.indexOf(find);
		while (i != -1)
		{
			buf.append(subject.substring(s, i));
			buf.append(replace);
			s = i + l;
			i = subject.indexOf(find, s);
		}
		buf.append(subject.substring(s));

		return buf.toString();
	}

	/**
	 * 
	 * @deprecated - user toString with a default argument of empty string
	 * 
	 *             If the param is Null, it will return a blank string, otherwise it returns the obj.toString
	 * 
	 * @return a non null string
	 */
	@Deprecated
	public static final String toStringOrBlankNull(Object obj)
	{
		return toString(obj, "");
	}

	public static final InputStream toInputStream(String x)
	{
		final byte bytes[] = x.getBytes();
		final InputStream inputStream = new ByteArrayInputStream(bytes);
		return inputStream;
	}

	public static final String getContentOfStream(InputStream stream) throws IOException
	{
		final StringBuffer buff = new StringBuffer();
		int ch = 0;
		while ((ch = stream.read()) > -1)
		{
			buff.append((char) ch);
		}

		return buff.toString();
	}

	public static String trim(String x)
	{
		if (x == null)
		{
			return null;
		}

		return x.trim();
	}

	public static String trim(Object x)
	{
		if (x == null)
		{
			return null;
		}

		if (x instanceof String)
		{
			return ((String) x).trim();
		}

		return String.valueOf(x).trim();
	}

	public static final String toString(Object obj, Object defaultValue)
	{
		if (obj != null)
		{
			return obj.toString();
		}
		else
		{
			return String.valueOf(defaultValue);
		}
	}

	public static final String toString(Object obj)
	{
		if (obj == null)
		{
			return null;
		}
		else if (obj instanceof String)
		{
			return (String) obj;
		}
		else
		{
			return obj.toString();
		}
	}

	public static final String capitalize(String x)
	{
		return toUpperCaseCharAt(x, 0);
	}

	public static final String toLowerCaseCharAt(String x, int charIndex)
	{
		return toCaseCharAt(x, charIndex, false);
	}

	public static final String toLowerCase(String x)
	{
		if (x != null)
		{
			return x.toLowerCase();
		}
		return null;
	}

	public static final String toLowerCase(Object x)
	{
		if (x != null)
		{
			return toString(x).toLowerCase();
		}
		return null;
	}

	public static final String toUpperCaseCharAt(String x, int charIndex)
	{
		return toCaseCharAt(x, charIndex, true);
	}

	public static final String toUpperCase(String x)
	{
		if (x != null)
		{
			return x.toUpperCase();
		}
		return null;
	}

	public static final String toUpperCase(Object x)
	{
		if (x != null)
		{
			return toString(x).toUpperCase();
		}

		return null;
	}

	/**
	 * 
	 * @param x
	 *            - the string
	 * @param charIndex
	 *            - index of the character to change case
	 * @param upper
	 *            - true for upper, false for lower case
	 * @return - the converted string
	 */
	private static final String toCaseCharAt(String x, int charIndex, boolean upper)
	{
		if (isEmpty(x))
		{
			return x;
		}

		if (charIndex < 0 || charIndex >= x.length())
		{
			return x;
		}

		if (x.length() > 1)
		{
			final String s = upper ? toUpperCase(x.charAt(charIndex)) : toLowerCase(x.charAt(charIndex));
			return x.substring(0, charIndex) + s + x.substring(charIndex + 1, x.length());
		}
		else
		{
			return upper ? x.toUpperCase() : x.toLowerCase();
		}
	}

	public static final boolean endsWith(String x, String subString)
	{
		if (x == null || subString == null)
		{
			return false;
		}

		return x.endsWith(subString);

	}

	public static final boolean isAlphaNumeric(String x)
	{
		if (x == null)
		{
			return false;
		}

		return REGEX_ALPHANUMERIC.matcher(x).matches();
	}

	public static final boolean isAlpha(String x)
	{
		if (x == null)
		{
			return false;
		}
		return REGEX_ALPHA.matcher(x).matches();
	}

	public static final boolean isNumeric(String x)
	{
		if (x == null)
		{
			return false;
		}

		return REGEX_NUMERIC.matcher(x).matches();
	}

	public static final boolean isAlphaAllowSpaces(String x)
	{
		if (x == null)
		{
			return false;
		}
		return REGEX_ALPHA_ALLOW_SPACES.matcher(x).matches();
	}

	public static final boolean isNumericAllowSpaces(String x)
	{
		if (x == null)
		{
			return false;
		}

		return REGEX_NUMERIC_ALLOW_SPACES.matcher(x).matches();
	}

	/**
	 * 
	 * true if the string contains alpha-numeric characters and spaces. If the string is an empty string or only contains spaces,
	 * then it returns false.
	 * 
	 * 
	 * "abc 123" = true "" = false " " = false
	 * 
	 */
	public static final boolean isAlphaNumericAllowSpaces(String x)
	{
		if (x == null)
		{
			return false;
		}
		return REGEX_ALPHANUMERIC_ALLOW_SPACES.matcher(x).matches();
	}

	public static final boolean isAlphaNumericAllowSpacesHyphenUnderscore(String x)
	{
		if (x == null)
		{
			return false;
		}
		return REGEX_ALPHANUMERIC_ALLOW_SPACES_HYPHEN_UNDERSCORE.matcher(x).matches();
	}

	public static final boolean isAlphaNumericAllowHyphenUnderscore(String x)
	{
		if (x == null)
		{
			return false;
		}
		return REGEX_ALPHANUMERIC_ALLOW_HYPHEN_UNDERSCORE.matcher(x).matches();
	}

	public static final boolean isAlphaNumericAllowSpacesHyphenUnderscorePeriodComma(String str)
	{
		if (str == null)
		{
			return false;
		}
		return REGEX_ALPHANUMERIC_ALLOW_SPACES_HYPHEN_UNDERSCORE_PERIOD_COMMA.matcher(str).matches();
	}

	public static final String dropCharactersFromEnd(String x, int numOfCharacters)
	{
		if (x.length() <= numOfCharacters)
		{
			return "";
		}
		return x.substring(0, x.length() - numOfCharacters);
	}

	public static final String removeSpaces(String str)
	{
		if (isEmpty(str))
		{
			return str;
		}
		return str.replaceAll(" ", "");
	}

	public static final String getUniqueId()
	{
		final UID uniqueId = new UID();
		return uniqueId.toString();
	}

	public static final String[] split(String x, String regex)
	{
		if (x == null)
		{
			return null;
		}

		return x.split(regex);
	}

	public static final String[] split(Object x, String regex)
	{
		if (x == null)
		{
			return null;
		}
		final String str = toString(x);
		return str.split(regex);
	}

	public static final boolean isValidEmail(String x)
	{
		if (StringUtil.isEmpty(x))
		{
			return false;
		}
		return REGEX_EMAIL.matcher(x).matches();
	}

	public static final boolean isValidHostName(String x)
	{
		if (StringUtil.isEmpty(x))
		{
			return false;
		}
		return REGEX_HOST_NAME.matcher(x).matches();
	}

	public static final boolean isValidIPAddress(String x)
	{
		if (StringUtil.isEmpty(x))
		{
			return false;
		}
		return REGEX_IPADDRESS.matcher(x).matches();
	}

	public static String replace(String x, char oldChar, char newChar)
	{
		if (x == null)
		{
			return null;
		}
		return x.replace(oldChar, newChar);
	}

	// ------------------------------------------------------------------------------
	// Equality & Comparison
	// ------------------------------------------------------------------------------

	public static final boolean equalsIgnoreCase(Object x1, Object x2)
	{
		if (x1 == null || x2 == null)
		{
			return false;
		}

		return toString(x1).equalsIgnoreCase(toString(x2));
	}

	public static final boolean equalsIgnoreCase(String x1, String x2)
	{
		if (x1 == null || x2 == null)
		{
			return false;
		}

		return x1.equalsIgnoreCase(x2);
	}

	public static final boolean equals(Object x1, int x2)
	{
		if (x1 == null)
		{
			return false;
		}

		return String.valueOf(x1).equals(String.valueOf(x2));
	}

	public static final boolean equals(String x1, String x2)
	{
		if (x1 == null || x2 == null)
		{
			return false;
		}

		return x1.equals(x2);
	}

	public static final boolean equals(Object x1, Object x2)
	{
		if (x1 == null || x2 == null)
		{
			return false;
		}
		return toString(x1).equals(toString(x2));
	}

	public static final int compareIgnoreCase(String x1, String x2)
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

		return toLowerCase(x1).compareTo(toLowerCase(x2));
	}

	public static final int compare(Object x1, Object x2)
	{
		return compare(toString(x1), toString(x2));
	}

	public static final int compare(String x1, String x2)
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

	/**
	 * @deprecated
	 */
	@Deprecated
	public static final boolean isEqualIgnoreCase(Object x1, Object x2)
	{
		return equalsIgnoreCase(x1, x2);
	}

	/**
	 * @deprecated
	 */
	@Deprecated
	public static final boolean isEqualIgnoreCase(String x1, String x2)
	{
		return equalsIgnoreCase(x1, x2);
	}

	/**
	 * @deprecated
	 */
	@Deprecated
	public static final boolean isEqual(Object x1, int x2)
	{
		return equalsIgnoreCase(x1, x2);
	}

	/**
	 * @deprecated
	 */
	@Deprecated
	public static final boolean isEqual(String x1, String x2)
	{
		return equalsIgnoreCase(x1, x2);
	}

	/**
	 * @deprecated
	 */
	@Deprecated
	public static final boolean isEqual(Object x1, Object x2)
	{
		return equalsIgnoreCase(x1, x2);
	}

	// ------------------------------------------------------------------------------
	// Wildcard
	// ------------------------------------------------------------------------------

	/**
	 * Performs a case insensitive wild-card matching for the text and pattern provided.
	 * 
	 * @param text
	 *            the text to be tested for matches.
	 * 
	 * @param pattern
	 *            the pattern to be matched for. This can contain the wild-card character '*' (asterisk) or '?'.
	 * 
	 * @return <tt>true</tt> if a match is found, <tt>false</tt> otherwise.
	 */
	public static boolean wildCardMatch(String text, String pattern)
	{
		return wildCardMatch(text, pattern, true);
	}

	/**
	 * Performs a wild-card matching for the text and pattern provided.
	 * 
	 * @param text
	 *            the text to be tested for matches.
	 * 
	 * @param pattern
	 *            the pattern to be matched for. This can contain the wild-card character '*' (asterisk) or '?'.
	 * 
	 * @return <tt>true</tt> if a match is found, <tt>false</tt> otherwise.
	 */
	public static boolean wildCardMatch(String text, String pattern, boolean caseInsensitive)
	{
		if (text == null && pattern == null)
		{
			return true;
		}
		if (text == null || pattern == null)
		{
			return false;
		}

		if (caseInsensitive)
		{
			text = toLowerCase(text);
			pattern = toLowerCase(pattern);
		}

		final String[] wcs = splitOnTokens(pattern);
		boolean anyChars = false;
		int textIdx = 0;
		int wcsIdx = 0;
		final Stack backtrack = new Stack();

		// loop around a backtrack stack, to handle complex * matching
		do
		{
			if (backtrack.size() > 0)
			{
				final int[] array = (int[]) backtrack.pop();
				wcsIdx = array[0];
				textIdx = array[1];
				anyChars = true;
			}

			// loop whilst tokens and text left to process
			while (wcsIdx < wcs.length)
			{

				if (wcs[wcsIdx].equals("?"))
				{
					// ? so move to next text char
					textIdx++;
					anyChars = false;

				}
				else if (wcs[wcsIdx].equals("*"))
				{
					// set any chars status
					anyChars = true;
					if (wcsIdx == wcs.length - 1)
					{
						textIdx = text.length();
					}

				}
				else
				{
					// matching text token
					if (anyChars)
					{
						// any chars then try to locate text token
						textIdx = text.indexOf(wcs[wcsIdx], textIdx);
						if (textIdx == -1)
						{
							// token not found
							break;
						}
						final int repeat = text.indexOf(wcs[wcsIdx], textIdx + 1);
						if (repeat >= 0)
						{
							backtrack.push(new int[] { wcsIdx, repeat });
						}
					}
					else
					{
						// matching from current position
						if (!text.startsWith(wcs[wcsIdx], textIdx))
						{
							// couldnt match token
							break;
						}
					}

					// matched text token, move text index to end of matched token
					textIdx += wcs[wcsIdx].length();
					anyChars = false;
				}

				wcsIdx++;
			}

			// full match
			if (wcsIdx == wcs.length && textIdx == text.length())
			{
				return true;
			}

		} while (backtrack.size() > 0);

		return false;
	}

	/**
	 * Splits a string into a number of tokens.
	 * 
	 * @param text
	 *            the text to split
	 * @return the tokens, never null
	 */
	private static String[] splitOnTokens(String text)
	{
		// used by wildcardMatch
		// package level so a unit test may run on this

		if (text.indexOf("?") == -1 && text.indexOf("*") == -1)
		{
			return new String[] { text };
		}

		final char[] array = text.toCharArray();
		final ArrayList list = new ArrayList();
		final StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < array.length; i++)
		{
			if (array[i] == '?' || array[i] == '*')
			{
				if (buffer.length() != 0)
				{
					list.add(buffer.toString());
					buffer.setLength(0);
				}
				if (array[i] == '?')
				{
					list.add("?");
				}
				else if (list.size() == 0 || (i > 0 && list.get(list.size() - 1).equals("*") == false))
				{
					list.add("*");
				}
			}
			else
			{
				buffer.append(array[i]);
			}
		}
		if (buffer.length() != 0)
		{
			list.add(buffer.toString());
		}

		return (String[]) list.toArray(new String[list.size()]);
	}

}
