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

import org.apache.commons.lang.WordUtils;

/**
 * @author Leon Samaroo
 *
 *
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class StringUtil {

    public static final String SPECIAL_CHARACTERS = "`~!@#$%^&*()-_=+[{]}\\|;:'\",<.>/?";
    private static final String _255 = "(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)";

    // Pre-compiled regex patterns
    private static final Pattern REGEX_WHITESPACE = Pattern.compile("^.*\\s.*$");
    private static final Pattern REGEX_ALPHANUMERIC = Pattern.compile("^\\w+$");
    private static final Pattern REGEX_ALPHANUMERIC_ALLOW_SPACES = Pattern.compile("^[a-zA-Z0-9\\s]*\\w+$");
    private static final Pattern REGEX_ALPHA = Pattern.compile("^[a-zA-Z]+$");
    private static final Pattern REGEX_ALPHA_ALLOW_SPACES = Pattern.compile("^[a-zA-Z\\s]*[a-zA-Z]+$");
    private static final Pattern REGEX_NUMERIC = Pattern.compile("^[0-9]+$");
    private static final Pattern REGEX_NUMERIC_ALLOW_SPACES = Pattern.compile("^[0-9\\s]*[0-9]+$");
    private static final Pattern REGEX_QUESTIONMARK = Pattern.compile("\\?");
    private static final Pattern REGEX_ALPHANUMERIC_ALLOW_SPACES_HYPHEN_UNDERSCORE = Pattern
            .compile("^[a-zA-Z_0-9\\Q-\\E\\s]*\\w+$");
    private static final Pattern REGEX_ALPHANUMERIC_ALLOW_HYPHEN_UNDERSCORE = Pattern
            .compile("^[a-zA-Z_0-9\\Q-\\E]*\\w+$");
    private static final Pattern REGEX_ALPHANUMERIC_ALLOW_SPACES_HYPHEN_UNDERSCORE_PERIOD_COMMA = Pattern
            .compile("^[a-zA-Z_0-9\\Q-\\E.,\\s]*\\w+$");
    private static final Pattern REGEX_EMAIL = Pattern
            .compile("^[\\w\\-]+(\\.[\\w\\-]+)*@([A-Za-z0-9\\Q-\\E]+\\.)+[A-Za-z]{2,4}$");
    private static final Pattern REGEX_IPADDRESS = Pattern.compile("^(?:" + _255 + "\\.){3}" + _255 + "$");
    private static final Pattern REGEX_DOLLARSIGN = Pattern.compile("\\$");
    private static final Pattern REGEX_HOST_NAME = Pattern.compile("^([A-Za-z0-9\\Q-\\E.]+\\.)+[A-Za-z]{2,4}$");
    private static final Pattern REGEX_USPHONE_NUMBERS = Pattern
            .compile("([0-9]( |-)?)?(\\(?[0-9]{3}\\)?|[0-9]{3})( |-)?([0-9]{3}( |-)?[0-9]{4})");
    private static final Pattern REGEX_PROPER_CASE = Pattern.compile("(^|\\W)([a-z])");

    protected StringUtil() {
        super();
    }

    /**
     * Returns a new string with only the numeric characters of the input
     * string.
     *
     * @param x
     * @return
     */
    public static String getNumericCharacters(final String x) {
        if (isEmpty(x)) {
            return x;
        }
        final String string = trim(x);
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < string.length(); i++) {
            final char c = string.charAt(i);
            if (Character.isDigit(c)) {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * Replaces all the specified character in the target string with the
     * replacement string.
     *
     * @param target
     *            the target string to replace
     * @param characters
     *            list of characters to replace in the target string
     * @param replacement
     *            the string to replace the character with.
     * @return a new string after replacement
     */
    public static String replaceAllCharacters(final String target, final char[] characters, final String replacement) {
        String newValue = target;
        for (final char c : characters) {
            newValue = newValue.replaceAll(String.valueOf(c), replacement);
        }
        return newValue;
    }

    /**
     *
     * @param s
     *            the string with underscore used as separator
     * @param useLowerCamelCase
     *            true to use the camel case where the first letter is
     *            lowercase.
     * @return a new string in camel case format. e.g. "this_is_an_example"
     *         would become "thisIsAnExample".
     */
    public static String underScoreToCamelCase(final String s, final boolean useLowerCamelCase) {
        final String temp = WordUtils.capitalizeFully(s, new char[] { '_' }).replaceAll("_", "");
        return useLowerCamelCase ? toLowerCaseCharAt(temp, 0) : temp;
    }

    /**
     * Formats a 10 digit number 2123445555 as (212) 344-5555
     */
    public static String formatPhoneNumber(final String phoneNumber) {
        if (isEmpty(phoneNumber)) {
            return phoneNumber;
        }

        if (phoneNumber.length() < 10) {
            return phoneNumber;
        }

        if (phoneNumber.length() == 11) {
            return phoneNumber.substring(0, 1) + " (" + phoneNumber.substring(0, 3) + ") " + phoneNumber.substring(3, 6)
                    + "-" + phoneNumber.substring(6, 10);
        }

        return "(" + phoneNumber.substring(0, 3) + ") " + phoneNumber.substring(3, 6) + "-"
                + phoneNumber.substring(6, 10);
    };

    public static String truncate(final String x, final int length) {
        if (x == null) {
            return null;
        }
        return x.substring(0, Math.min(x.length(), length));
    }

    public static String replaceAllNamedParameter(final String x, final String name, final Object value) {
        return replaceAllNamedParameter(x, name, toString(value));
    }

    public static String replaceAllNamedParameter(final String x, final String name, final String value) {
        final String pattern = "\\{" + name + "\\}";
        return x.replaceAll(pattern, value);
    }

    public static String replaceNamedParameter(final String x, final String name, final Object value) {
        return replaceNamedParameter(x, name, toString(value));
    }

    public static String replaceNamedParameter(final String x, final String name, final String value) {
        final String pattern = "\\{" + name + "\\}";
        return x.replaceFirst(pattern, value);
    }

    public static String toProperCase(final String x) {
        final Matcher m = REGEX_PROPER_CASE.matcher(x.toLowerCase());
        final StringBuffer sb = new StringBuffer(x.length());
        while (m.find()) {
            m.appendReplacement(sb, m.group(1) + m.group(2).toUpperCase());
        }
        m.appendTail(sb);
        return sb.toString();
    }

    public static boolean isUSPhone(final String x) {
        if (x == null) {
            return false;
        }

        return REGEX_USPHONE_NUMBERS.matcher(x).matches();

    }

    public static final String repeat(final CharSequence str, final int numOfTimes) {
        final StringBuilder builder = new StringBuilder();
        for (int i = 0; i < numOfTimes; i++) {
            builder.append(str);
        }
        return builder.toString();
    }

    public static final String padStringWithCharacter(final CharSequence string, final char c, final int numOfTimes,
            final boolean asPrefix) {
        final StringBuilder builder = new StringBuilder();
        for (int i = 0; i < numOfTimes; i++) {
            builder.append(c);
        }

        if (asPrefix) {
            builder.append(string);
        }
        else {
            builder.insert(0, string);
        }

        return builder.toString();
    }

    public static final boolean isPalindrome(final Object obj) {
        if (obj == null) {
            return false;
        }

        final char[] c = String.valueOf(obj).toCharArray();
        int start = 0;
        int end = c.length - 1;
        for (int i = 0; i < (c.length / 2); i++) {
            if (c[start] != c[end]) {
                return false;
            }
            start++;
            end--;
        }
        return true;
    }

    public static final String hexToString(String hex) {
        if (isEmpty(hex)) {
            return hex;
        }

        hex = trim(hex);

        final StringBuilder sb = new StringBuilder();

        // 49204c6f7665204a617661 split into two characters 49, 20, 4c...
        for (int i = 0; i < (hex.length() - 1); i += 2) {

            // grab the hex in pairs
            final String output = hex.substring(i, (i + 2));
            // convert hex to decimal
            final int decimal = Integer.parseInt(output, 16);
            // convert the decimal to character
            sb.append((char) decimal);
        }

        return sb.toString();
    }

    public static final String replaceFirstCharacter(final char c, final String string, final String value) {
        final int index = string.indexOf(c);
        final StringBuffer buff = new StringBuffer(string);
        buff.replace(index, index + 1, value);
        return buff.toString();
    }

    public static final String replaceLastCharacter(final char c, final String string, final String value) {
        final int index = string.lastIndexOf(c);
        final StringBuffer buff = new StringBuffer(string);
        buff.replace(index, index + 1, value);
        return buff.toString();
    }

    public static final String replaceLastQuestionMark(final String string, final String value) {
        return replaceLastCharacter('?', string, value);
    }

    public static final String deleteFirstQuestionMark(final String string) {
        return replaceFirstQuestionMark(string, "");
    }

    public static final String replaceFirstQuestionMark(final String string, final String value) {
        return REGEX_QUESTIONMARK.matcher(string).replaceFirst(value);
    }

    public static final String replaceFirstQuestionMark(final String string, final Object value) {
        return replaceFirstQuestionMark(string, String.valueOf(value));
    }

    public static final String replaceFirstQuestionMark(final String string, final int value) {
        return replaceFirstQuestionMark(string, String.valueOf(value));
    }

    public static final String replaceFirstQuestionMark(final String string, final boolean value) {
        return replaceFirstQuestionMark(string, String.valueOf(value));
    }

    public static final String replaceFirstQuestionMark(final String string, final double value) {
        return replaceFirstQuestionMark(string, String.valueOf(value));
    }

    public static final String replaceFirstQuestionMark(final String string, final long value) {
        return replaceFirstQuestionMark(string, String.valueOf(value));
    }

    public static final String replaceFirstQuestionMark(final String string, final float value) {
        return replaceFirstQuestionMark(string, String.valueOf(value));
    }

    public static final String replaceFirstQuestionMark(final String string, final char value) {
        return replaceFirstQuestionMark(string, String.valueOf(value));
    }

    public static final String replaceLastDollarSign(final String string, final String value) {
        return replaceLastCharacter('$', string, value);
    }

    public static final String deleteFirstDollarSign(final String string) {
        return replaceFirstDollarSign(string, "");
    }

    public static final String replaceFirstDollarSign(final String string, final String value) {
        return REGEX_DOLLARSIGN.matcher(string).replaceFirst(value);
    }

    public static final String replaceFirstDollarSign(final String string, final Object value) {
        return replaceFirstDollarSign(string, String.valueOf(value));
    }

    public static final String replaceFirstDollarSign(final String string, final int value) {
        return replaceFirstDollarSign(string, String.valueOf(value));
    }

    public static final String replaceFirstDollarSign(final String string, final boolean value) {
        return replaceFirstDollarSign(string, String.valueOf(value));
    }

    public static final String replaceFirstDollarSign(final String string, final double value) {
        return replaceFirstDollarSign(string, String.valueOf(value));
    }

    public static final String replaceFirstDollarSign(final String string, final long value) {
        return replaceFirstDollarSign(string, String.valueOf(value));
    }

    public static final String replaceFirstDollarSign(final String string, final float value) {
        return replaceFirstDollarSign(string, String.valueOf(value));
    }

    public static final String replaceFirstDollarSign(final String string, final char value) {
        return replaceFirstDollarSign(string, String.valueOf(value));
    }

    /**
     *
     * @param strings
     *            - list of strings
     * @return - true if any of the strings is null or empty, false otherwise.
     */
    public static final boolean isAnyEmpty(final Object... strings) {
        for (final Object string : strings) {
            if (isEmpty(string)) {
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @return true if all the strings in the list are empty, false if at least
     *         one is non-empty
     */
    public static final boolean isAllEmpty(final Object... strings) {
        for (final Object string : strings) {
            if (isNotEmpty(string)) {
                return false;
            }
        }
        return true;
    }

    /**
     *
     * @return true if all the strings in the list are not empty, false if at
     *         least one is empty
     */
    public static final boolean isAllNotEmpty(final Object... strings) {
        for (final Object string : strings) {
            if (isEmpty(string)) {
                return false;
            }
        }
        return true;
    }

    public static final boolean isEmpty(final String x) {
        if (x == null) {
            return true;
        }
        else {
            return trim(x).length() <= 0;
        }
    }

    public static final boolean isNotEmpty(final String x) {
        return !isEmpty(x);
    }

    public static final boolean isEmpty(final Object x) {
        if (x == null) {
            return true;
        }
        else {
            return trim(x).length() <= 0;
        }
    }

    public static final boolean isNotEmpty(final Object x) {
        return !isEmpty(x);
    }

    /**
     * @return - true if it contains at least one whitespace character.
     *         Whitespace as defined in regex --space, tab, etc.
     */
    public static final boolean constainsWhiteSpace(final String str) {
        if (str == null) {
            return false;
        }

        return REGEX_WHITESPACE.matcher(str).matches();
    }

    /**
     *
     * Check to string to see if the string contains any of the characters
     * specified in the char array.
     *
     * @param string
     *            - the String to check.
     * @param characters
     *            - the character array
     * @return true if the string contains any of the specified characters,
     *         false otherwise.
     */
    public static final boolean containsAnyCharacters(final String string, final char... characters) {
        if ((string == null) || (characters == null)) {
            return false;
        }

        for (final char character : characters) {
            if (string.indexOf(character) != -1) {
                return true;
            }
        }
        return false;
    }

    /**
     *
     * A convenience method. This method calls
     * <code>containsAnyCharacters( string,
     * characters.toCharArray() )</code>.
     *
     */
    public static final boolean containsAnyCharacters(final String string, final String characters) {
        return containsAnyCharacters(string, characters.toCharArray());
    }

    public static final boolean containsSubString(final String x, final String subString) {
        if ((x == null) || (subString == null)) {
            return false;
        }

        return x.indexOf(subString) != -1;
    }

    /**
     * @deprecated - @see containsAny
     */
    @Deprecated
    public static final boolean containsAnySubString(final Object x, final String... subString) {
        if ((x == null) || (subString == null)) {
            return false;
        }

        for (final String string : subString) {
            if (containsSubString(x, string)) {
                return true;
            }
        }

        return false;
    }

    public static final boolean containsAny(final Object x, final String... subString) {
        return containsAnySubString(x, subString);
    }

    public static final boolean containsAnyIgnoreCase(final Object x, final String... subString) {
        if ((x == null) || (subString == null)) {
            return false;
        }
        for (final String string : subString) {
            if (containsSubStringIgnoreCase(x, string)) {
                return true;
            }
        }
        return false;
    }

    public static final boolean containsSubString(final Object x, final String subString) {
        if ((x == null) || (subString == null)) {
            return false;
        }

        return toString(x).indexOf(subString) != -1;
    }

    public static final boolean containsSubStringIgnoreCase(final String x, final String subString) {
        if ((x == null) || (subString == null)) {
            return false;
        }

        return toLowerCase(x).indexOf(subString.toLowerCase()) != -1;
    }

    public static final boolean containsSubStringIgnoreCase(final Object x, final String subString) {
        if ((x == null) || (subString == null)) {
            return false;
        }

        return toLowerCase(x).indexOf(subString.toLowerCase()) != -1;
    }

    /**
     * Search a String to find the first index of any character not in the given
     * set of allowed characters.
     *
     * StringUtils.indexOfAnyBut("zzabyycdxx", "za") = 3, because 'b' is the
     * first character that is IN the search string BUT NOT in the given set of
     * characters.
     *
     */
    public static final int indexOfAnyBut(final String str, final String allowedCharacters) {
        if ((str == null) || (allowedCharacters == null) || isEmpty(str) || isEmpty(allowedCharacters)) {
            return -1;
        }
        for (int i = 0; i < str.length(); i++) {
            if (allowedCharacters.indexOf(str.charAt(i)) < 0) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Search a String to find the first index of any character not in the given
     * set of characters.
     *
     * @see indexOfAnyBut(String str, String searchChars)
     *
     */
    public static final int indexOfAnyBut(final String str, final char[] allowedCharacters) {
        return indexOfAnyBut(str, new String(allowedCharacters));
    }

    /**
     * Checks if the String contains only certain characters.
     */
    public static final boolean containsOnly(final String str, final char... validCharacters) {
        if ((validCharacters == null) || (str == null)) {
            return false;
        }

        return containsOnly(str, new String(validCharacters));
    }

    /**
     * Checks if the String contains only certain characters.
     */
    public static final boolean containsOnly(final String str, final String validCharacters) {
        if ((validCharacters == null) || (str == null)) {
            return false;
        }
        if (str.length() == 0) {
            return true;
        }
        if (validCharacters.length() == 0) {
            return false;
        }
        return indexOfAnyBut(str, validCharacters) == -1;
    }

    /**
     *
     *
     * Efficient string replace function. Replaces instances of the substring
     * find with replace in the string subject.
     *
     */
    public static final String replace(final String subject, final String find, final String replace) {
        final StringBuffer buf = new StringBuffer();
        final int l = find.length();
        int s = 0;
        int i = subject.indexOf(find);
        while (i != -1) {
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
     *             If the param is Null, it will return a blank string,
     *             otherwise it returns the obj.toString
     *
     * @return a non null string
     */
    @Deprecated
    public static final String toStringOrBlankNull(final Object obj) {
        return toString(obj, "");
    }

    public static final InputStream toInputStream(final String x) {
        final byte bytes[] = x.getBytes();
        final InputStream inputStream = new ByteArrayInputStream(bytes);
        return inputStream;
    }

    public static final String getContentOfStream(final InputStream stream) throws IOException {
        final StringBuffer buff = new StringBuffer();
        int ch = 0;
        while ((ch = stream.read()) > -1) {
            buff.append((char) ch);
        }

        return buff.toString();
    }

    public static String trim(final String x) {
        if (x == null) {
            return null;
        }

        return x.trim();
    }

    public static String trim(final Object x) {
        if (x == null) {
            return null;
        }

        if (x instanceof String) {
            return ((String) x).trim();
        }

        return String.valueOf(x).trim();
    }

    public static final String toString(final Object obj, final Object defaultValue) {
        if (obj != null) {
            return obj.toString();
        }
        else {
            return String.valueOf(defaultValue);
        }
    }

    public static final String toString(final Object obj) {
        if (obj == null) {
            return null;
        }
        else if (obj instanceof String) {
            return (String) obj;
        }
        else {
            return obj.toString();
        }
    }

    public static final String capitalize(final String x) {
        return toUpperCaseCharAt(x, 0);
    }

    public static final String toLowerCaseCharAt(final String x, final int charIndex) {
        return toCaseCharAt(x, charIndex, false);
    }

    public static final String toLowerCase(final String x) {
        if (x != null) {
            return x.toLowerCase();
        }
        return null;
    }

    public static final String toLowerCase(final Object x) {
        if (x != null) {
            return toString(x).toLowerCase();
        }
        return null;
    }

    public static final String toUpperCaseCharAt(final String x, final int charIndex) {
        return toCaseCharAt(x, charIndex, true);
    }

    public static final String toUpperCase(final String x) {
        if (x != null) {
            return x.toUpperCase();
        }
        return null;
    }

    public static final String toUpperCase(final Object x) {
        if (x != null) {
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
    private static final String toCaseCharAt(final String x, final int charIndex, final boolean upper) {
        if (isEmpty(x)) {
            return x;
        }

        if ((charIndex < 0) || (charIndex >= x.length())) {
            return x;
        }

        if (x.length() > 1) {
            final String s = upper ? toUpperCase(x.charAt(charIndex)) : toLowerCase(x.charAt(charIndex));
            return x.substring(0, charIndex) + s + x.substring(charIndex + 1, x.length());
        }
        else {
            return upper ? x.toUpperCase() : x.toLowerCase();
        }
    }

    public static final boolean endsWith(final String x, final String subString) {
        if ((x == null) || (subString == null)) {
            return false;
        }

        return x.endsWith(subString);

    }

    public static final boolean isAlphaNumeric(final String x) {
        if (x == null) {
            return false;
        }

        return REGEX_ALPHANUMERIC.matcher(x).matches();
    }

    public static final boolean isAlpha(final String x) {
        if (x == null) {
            return false;
        }
        return REGEX_ALPHA.matcher(x).matches();
    }

    public static final boolean isNumeric(final String x) {
        if (x == null) {
            return false;
        }

        return REGEX_NUMERIC.matcher(x).matches();
    }

    public static final boolean isAlphaAllowSpaces(final String x) {
        if (x == null) {
            return false;
        }
        return REGEX_ALPHA_ALLOW_SPACES.matcher(x).matches();
    }

    public static final boolean isNumericAllowSpaces(final String x) {
        if (x == null) {
            return false;
        }

        return REGEX_NUMERIC_ALLOW_SPACES.matcher(x).matches();
    }

    /**
     *
     * true if the string contains alpha-numeric characters and spaces. If the
     * string is an empty string or only contains spaces, then it returns false.
     *
     *
     * "abc 123" = true "" = false " " = false
     *
     */
    public static final boolean isAlphaNumericAllowSpaces(final String x) {
        if (x == null) {
            return false;
        }
        return REGEX_ALPHANUMERIC_ALLOW_SPACES.matcher(x).matches();
    }

    public static final boolean isAlphaNumericAllowSpacesHyphenUnderscore(final String x) {
        if (x == null) {
            return false;
        }
        return REGEX_ALPHANUMERIC_ALLOW_SPACES_HYPHEN_UNDERSCORE.matcher(x).matches();
    }

    public static final boolean isAlphaNumericAllowHyphenUnderscore(final String x) {
        if (x == null) {
            return false;
        }
        return REGEX_ALPHANUMERIC_ALLOW_HYPHEN_UNDERSCORE.matcher(x).matches();
    }

    public static final boolean isAlphaNumericAllowSpacesHyphenUnderscorePeriodComma(final String str) {
        if (str == null) {
            return false;
        }
        return REGEX_ALPHANUMERIC_ALLOW_SPACES_HYPHEN_UNDERSCORE_PERIOD_COMMA.matcher(str).matches();
    }

    public static final String dropCharactersFromEnd(final String x, final int numOfCharacters) {
        if (x.length() <= numOfCharacters) {
            return "";
        }
        return x.substring(0, x.length() - numOfCharacters);
    }

    public static final String removeSpaces(final String str) {
        if (isEmpty(str)) {
            return str;
        }
        return str.replaceAll(" ", "");
    }

    public static final String getUniqueId() {
        final UID uniqueId = new UID();
        return uniqueId.toString();
    }

    public static final String[] split(final String x, final String regex) {
        if (x == null) {
            return null;
        }

        return x.split(regex);
    }

    public static final String[] split(final Object x, final String regex) {
        if (x == null) {
            return null;
        }
        final String str = toString(x);
        return str.split(regex);
    }

    public static final boolean isValidEmail(final String x) {
        if (StringUtil.isEmpty(x)) {
            return false;
        }
        return REGEX_EMAIL.matcher(x).matches();
    }

    public static final boolean isValidHostName(final String x) {
        if (StringUtil.isEmpty(x)) {
            return false;
        }
        return REGEX_HOST_NAME.matcher(x).matches();
    }

    public static final boolean isValidIPAddress(final String x) {
        if (StringUtil.isEmpty(x)) {
            return false;
        }
        return REGEX_IPADDRESS.matcher(x).matches();
    }

    public static String replace(final String x, final char oldChar, final char newChar) {
        if (x == null) {
            return null;
        }
        return x.replace(oldChar, newChar);
    }

    // ------------------------------------------------------------------------------
    // Equality & Comparison
    // ------------------------------------------------------------------------------

    public static final boolean equalsAnyIgnoreCase(final String x, final String... list) {
        if ((x == null) || (list == null)) {
            return false;
        }

        for (final String string : list) {
            if (x.equalsIgnoreCase(string)) {
                return true;
            }
        }

        return false;
    }

    public static final boolean equalsAny(final String x, final String... list) {
        if ((x == null) || (list == null)) {
            return false;
        }

        for (final String string : list) {
            if (x.equals(string)) {
                return true;
            }
        }

        return false;
    }

    public static final boolean equalsAny(final Object x, final Object... list) {
        if ((x == null) || (list == null)) {
            return false;
        }

        for (final Object string : list) {
            if (StringUtil.equals(x, string)) {
                return true;
            }
        }

        return false;
    }

    public static final boolean equalsIgnoreCase(final Object x1, final Object x2) {
        if ((x1 == null) || (x2 == null)) {
            return false;
        }

        return toString(x1).equalsIgnoreCase(toString(x2));
    }

    public static final boolean equalsIgnoreCase(final String x1, final String x2) {
        if ((x1 == null) || (x2 == null)) {
            return false;
        }

        return x1.equalsIgnoreCase(x2);
    }

    public static final boolean equals(final Object x1, final int x2) {
        if (x1 == null) {
            return false;
        }

        return String.valueOf(x1).equals(String.valueOf(x2));
    }

    public static final boolean notEquals(final Object x1, final int x2) {
        return !equals(x1, x2);
    }

    public static final boolean equals(final String x1, final String x2) {
        if ((x1 == null) || (x2 == null)) {
            return false;
        }

        return x1.equals(x2);
    }

    public static final boolean notEquals(final String x1, final String x2) {
        return !equals(x1, x2);
    }

    public static final boolean equals(final Object x1, final Object x2) {
        if ((x1 == null) || (x2 == null)) {
            return false;
        }
        return equals(toString(x1), (toString(x2)));
    }

    public static final boolean notEquals(final Object x1, final Object x2) {
        return !equals(x1, x2);
    }

    public static final int compareIgnoreCase(final String x1, final String x2) {
        if ((x1 == null) && (x2 == null)) {
            return 0;
        }

        if (x1 == null) {
            return -1;
        }

        if (x2 == null) {
            return 1;
        }

        return toLowerCase(x1).compareTo(toLowerCase(x2));
    }

    public static final int compare(final Object x1, final Object x2) {
        return compare(toString(x1), toString(x2));
    }

    public static final int compare(final String x1, final String x2) {
        if ((x1 == null) && (x2 == null)) {
            return 0;
        }

        if (x1 == null) {
            return -1;
        }

        if (x2 == null) {
            return 1;
        }

        return x1.compareTo(x2);
    }

    // ------------------------------------------------------------------------------
    // Wildcard
    // ------------------------------------------------------------------------------

    /**
     * Performs a case insensitive wild-card matching for the text and pattern
     * provided.
     *
     * @param text
     *            the text to be tested for matches.
     *
     * @param pattern
     *            the pattern to be matched for. This can contain the wild-card
     *            character '*' (asterisk) or '?'.
     *
     * @return <tt>true</tt> if a match is found, <tt>false</tt> otherwise.
     */
    public static boolean wildCardMatch(final String text, final String pattern) {
        return wildCardMatch(text, pattern, true);
    }

    /**
     * Performs a wild-card matching for the text and pattern provided.
     *
     * @param text
     *            the text to be tested for matches.
     *
     * @param pattern
     *            the pattern to be matched for. This can contain the wild-card
     *            character '*' (asterisk) or '?'.
     *
     * @return <tt>true</tt> if a match is found, <tt>false</tt> otherwise.
     */
    public static boolean wildCardMatch(String text, String pattern, final boolean caseInsensitive) {
        if ((text == null) && (pattern == null)) {
            return true;
        }
        if ((text == null) || (pattern == null)) {
            return false;
        }

        if (caseInsensitive) {
            text = toLowerCase(text);
            pattern = toLowerCase(pattern);
        }

        final String[] wcs = splitOnTokens(pattern);
        boolean anyChars = false;
        int textIdx = 0;
        int wcsIdx = 0;
        final Stack backtrack = new Stack();

        // loop around a backtrack stack, to handle complex * matching
        do {
            if (backtrack.size() > 0) {
                final int[] array = (int[]) backtrack.pop();
                wcsIdx = array[0];
                textIdx = array[1];
                anyChars = true;
            }

            // loop whilst tokens and text left to process
            while (wcsIdx < wcs.length) {

                if (wcs[wcsIdx].equals("?")) {
                    // ? so move to next text char
                    textIdx++;
                    anyChars = false;

                }
                else if (wcs[wcsIdx].equals("*")) {
                    // set any chars status
                    anyChars = true;
                    if (wcsIdx == (wcs.length - 1)) {
                        textIdx = text.length();
                    }

                }
                else {
                    // matching text token
                    if (anyChars) {
                        // any chars then try to locate text token
                        textIdx = text.indexOf(wcs[wcsIdx], textIdx);
                        if (textIdx == -1) {
                            // token not found
                            break;
                        }
                        final int repeat = text.indexOf(wcs[wcsIdx], textIdx + 1);
                        if (repeat >= 0) {
                            backtrack.push(new int[] { wcsIdx, repeat });
                        }
                    }
                    else {
                        // matching from current position
                        if (!text.startsWith(wcs[wcsIdx], textIdx)) {
                            // couldnt match token
                            break;
                        }
                    }

                    // matched text token, move text index to end of matched
                    // token
                    textIdx += wcs[wcsIdx].length();
                    anyChars = false;
                }

                wcsIdx++;
            }

            // full match
            if ((wcsIdx == wcs.length) && (textIdx == text.length())) {
                return true;
            }

        }
        while (backtrack.size() > 0);

        return false;
    }

    /**
     * Splits a string into a number of tokens.
     *
     * @param text
     *            the text to split
     * @return the tokens, never null
     */
    private static String[] splitOnTokens(final String text) {
        // used by wildcardMatch
        // package level so a unit test may run on this

        if ((text.indexOf("?") == -1) && (text.indexOf("*") == -1)) {
            return new String[] { text };
        }

        final char[] array = text.toCharArray();
        final ArrayList list = new ArrayList();
        final StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < array.length; i++) {
            if ((array[i] == '?') || (array[i] == '*')) {
                if (buffer.length() != 0) {
                    list.add(buffer.toString());
                    buffer.setLength(0);
                }
                if (array[i] == '?') {
                    list.add("?");
                }
                else if ((list.size() == 0) || ((i > 0) && (list.get(list.size() - 1).equals("*") == false))) {
                    list.add("*");
                }
            }
            else {
                buffer.append(array[i]);
            }
        }
        if (buffer.length() != 0) {
            list.add(buffer.toString());
        }

        return (String[]) list.toArray(new String[list.size()]);
    }

}
