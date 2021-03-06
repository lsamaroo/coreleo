package com.coreleo.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;

public class StringUtilTest {

	@Test
	public final void testgetNumericCharacters() {
		final String phone1 = "1 (347) 878-1536";
		final String phone2 = "1-347-878-1536";
		final String phone3 = "+1 347 878 1536             ";
		final String result = "13478781536";

		assertEquals(result, StringUtil.getNumericCharacters(phone1));
		assertEquals(result, StringUtil.getNumericCharacters(phone2));
		assertEquals(result, StringUtil.getNumericCharacters(phone3));
	}

	@Test
	public final void testReplaceAllNamedParameter() {
		final String string = "This is a {namedParam} and so is this one: {namedParam}";
		final String result = "This is a value and so is this one: value";
		assertEquals(result, StringUtil.replaceAllNamedParameter(string, "namedParam", "value"));

	}

	@Test
	public final void testReplaceNamedParameter() {
		final String string = "This is a {namedParam}";
		final String result = "This is a value";
		assertEquals(result, StringUtil.replaceNamedParameter(string, "namedParam", "value"));
	}

	@Test
	public final void testToLowerCaseCharAt() {
		final String x = "STRINGUTIL";
		assertEquals("STRINGUTIL", StringUtil.toLowerCaseCharAt(x, 10));
		assertEquals("sTRINGUTIL", StringUtil.toLowerCaseCharAt(x, 0));
		assertEquals("STRINGUTIl", StringUtil.toLowerCaseCharAt(x, 9));
		assertEquals("STRiNGUTIL", StringUtil.toLowerCaseCharAt(x, 3));
	}

	@Test
	public final void testReplaceLastQuestionMark() {
		final String answer = "?1";
		assertEquals(answer, StringUtil.replaceLastQuestionMark("??", "1"));
	}

	@Test
	public final void testDeleteFirstQuestionMark() {
		final String answer = "?";
		assertEquals(answer, StringUtil.deleteFirstQuestionMark("??"));
	}

	@Test
	public final void testReplaceFirstQuestionMarkStringString() {
		final String answer = "1?";
		assertEquals(answer, StringUtil.replaceFirstQuestionMark("??", "1"));
	}

	@Test
	public final void testReplaceFirstQuestionMarkStringObject() {
		final String answer = "1?";
		final Object x = "1";
		assertEquals(answer, StringUtil.replaceFirstQuestionMark("??", x));
	}

	@Test
	public final void testReplaceFirstQuestionMarkStringInt() {
		final String answer = "1?";
		final int x = 1;
		assertEquals(answer, StringUtil.replaceFirstQuestionMark("??", x));
	}

	@Test
	public final void testReplaceFirstQuestionMarkStringBoolean() {
		final String answer = "false?";
		final boolean x = false;
		assertEquals(answer, StringUtil.replaceFirstQuestionMark("??", x));
	}

	@Test
	public final void testReplaceFirstQuestionMarkStringDouble() {
		final String answer = "1.0?";
		final double x = 1.0;
		assertEquals(answer, StringUtil.replaceFirstQuestionMark("??", x));
	}

	@Test
	public final void testReplaceFirstQuestionMarkStringLong() {
		final String answer = "1?";
		final long x = 1L;
		assertEquals(answer, StringUtil.replaceFirstQuestionMark("??", x));
	}

	@Test
	public final void testReplaceFirstQuestionMarkStringFloat() {
		final String answer = "1.0?";
		final float x = 1L;
		assertEquals(answer, StringUtil.replaceFirstQuestionMark("??", x));
	}

	@Test
	public final void testReplaceFirstQuestionMarkStringChar() {
		final String answer = "1?";
		final char x = '1';
		assertEquals(answer, StringUtil.replaceFirstQuestionMark("??", x));
	}

	@Test
	public final void testIsAllEmpty() {
		assertTrue(StringUtil.isAllEmpty("", null, "", "     ", ""));
		assertFalse(StringUtil.isAllEmpty("", null, "", " ", "I am a non empty string"));
	}

	@Test
	public final void testIsAllNotEmpty() {
		assertTrue(StringUtil.isAllNotEmpty("I", "am", "not", "  empty   "));
		assertFalse(StringUtil.isAllNotEmpty("I", "am", "not", "  empty   ", "    ", null));
	}

	@Test
	public final void testIsEmpty() {
		Object x = null;
		assertTrue(StringUtil.isEmpty(x));

		x = "  ";
		assertTrue(StringUtil.isEmpty(x));
	}

	@Test
	public final void testIsNotEmpty() {
		Object x = null;
		assertFalse(StringUtil.isNotEmpty(x));

		x = "I am not empty";
		assertTrue(StringUtil.isNotEmpty(x));
	}

	@Test
	public final void testConstainsWhiteSpace() {
		final String x = "I have white spaces";
		assertTrue(StringUtil.constainsWhiteSpace(x));
	}

	@Test
	public final void testContainsAnyStringCharArray() {
		final String x = "I have white spaces";
		assertTrue(StringUtil.containsAnyCharacters(x, new char[] { 'w', 'x', 'y' }));
	}

	@Test
	public final void testContainsAnyStringString() {
		final String x = "I have white spaces";
		assertTrue(StringUtil.containsAnyCharacters(x, "wxy"));
	}

	@Test
	public final void testContainsSubString() {
		final String x = "I have white spaces";
		assertTrue(StringUtil.containsSubString(x, "whit"));
	}

	@Test
	public final void testIndexOfAnyButStringString() {
		final String x = "abcdefg";
		assertEquals(StringUtil.indexOfAnyBut(x, "abcdef"), 6);
	}

	@Test
	public final void testIndexOfAnyButStringCharArray() {
		final String x = "abcdefg";
		assertEquals(StringUtil.indexOfAnyBut(x, "abcdef".toCharArray()), 6);
	}

	@Test
	public final void testContainsOnlyStringCharArray() {
		final String x = "I only contain vowels";
		assertFalse(StringUtil.containsOnly(x, "aeiou".toCharArray()));

	}

	@Test
	public final void testContainsOnlyStringString() {
		final String x = "I only contain vowels";
		assertFalse(StringUtil.containsOnly(x, "aeiou"));
	}

	@Test
	public final void testReplace() {
		final String oldText = "I only contain vowels";
		final String newText = "I only contain consonants";
		assertEquals(StringUtil.replace(oldText, "vowels", "consonants"), newText);
	}

	@Test
	public final void testToInputStream() {
		final String x = "abcdefg";
		assertTrue(StringUtil.toInputStream(x) instanceof InputStream);
	}

	@Test
	public final void testGetContentOfStream() {
		try {
			final String x = "abcdefg";
			final InputStream stream = StringUtil.toInputStream(x);
			assertEquals(x, StringUtil.getContentOfStream(stream));
		}
		catch (final IOException ioe) {
			fail("IOException");
		}
	}

	@Test
	public final void testTrim() {
		assertNull(StringUtil.trim(null));

		final String x = "  lots of spaces   ";
		assertEquals(StringUtil.trim(x), x.trim());
	}

	@Test
	public final void testWildCardMatch() {
		assertNull(StringUtil.trim(null));

		final String x = "lots of spaces";

		assertTrue(StringUtil.wildCardMatch(x, "*?*", true));
		assertTrue(StringUtil.wildCardMatch(x, "*lot?*", true));
		assertFalse(StringUtil.wildCardMatch(x, "*lot", true));
		assertFalse(StringUtil.wildCardMatch(x, "?", true));
	}

	@Test
	public final void testToStringObjectObject() {

	}

	@Test
	public final void testToStringObject() {

	}

	@Test
	public final void testToLowerCase() {

	}

	@Test
	public final void testToUpperCase() {

	}

	@Test
	public final void testEndsWith() {

	}

	@Test
	public final void testIsAlphaNumeric() {

	}

	@Test
	public final void testIsAlpha() {

	}

	@Test
	public final void testIsNumeric() {

	}

	@Test
	public final void testIsAlphaAllowSpaces() {

	}

	@Test
	public final void testIsNumericAllowSpaces() {

	}

	@Test
	public final void testIsAlphaNumericAllowSpaces() {

	}

	@Test
	public final void testIsAlphaNumericAllowSpacesHyphenUnderscore() {

	}

	@Test
	public final void testIsAlphaNumericAllowHyphenUnderscore() {

	}

	@Test
	public final void testIsAlphaNumericAllowSpacesHyphenUnderscorePeriodComma() {

	}

	@Test
	public final void testDropCharactersFromEnd() {

	}

	@Test
	public final void testRemoveSpaces() {

	}

	@Test
	public final void testGetUniqueId() {

	}

	@Test
	public final void testSplit() {

	}

	@Test
	public final void testIsValidEmail() {

	}

	@Test
	public final void testIsValidIPAddress() {

	}

	@Test
	public final void testCompareIgnoreCase() {

	}

	@Test
	public final void testCompare() {

	}

}
