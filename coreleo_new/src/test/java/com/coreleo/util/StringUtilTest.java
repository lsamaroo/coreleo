package com.coreleo.util;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.InputStream;
import org.junit.Test;

public class StringUtilTest {

	@Test
	public final void testReplaceLastQuestionMark() {
		String answer = "?1";
		assertEquals(answer, StringUtil.replaceLastQuestionMark("??", "1"));
	}

	@Test
	public final void testDeleteFirstQuestionMark() {
		String answer = "?";
		assertEquals(answer, StringUtil.deleteFirstQuestionMark("??"));
	}

	@Test
	public final void testReplaceFirstQuestionMarkStringString() {
		String answer = "1?";
		assertEquals(answer, StringUtil.replaceFirstQuestionMark("??", "1"));
	}

	@Test
	public final void testReplaceFirstQuestionMarkStringObject() {
		String answer = "1?";
		Object x = "1";
		assertEquals(answer, StringUtil.replaceFirstQuestionMark("??", x));
	}

	@Test
	public final void testReplaceFirstQuestionMarkStringInt() {
		String answer = "1?";
		int x = 1;
		assertEquals(answer, StringUtil.replaceFirstQuestionMark("??", x));
	}

	@Test
	public final void testReplaceFirstQuestionMarkStringBoolean() {
		String answer = "false?";
		boolean x = false;
		assertEquals(answer, StringUtil.replaceFirstQuestionMark("??", x));
	}

	@Test
	public final void testReplaceFirstQuestionMarkStringDouble() {
		String answer = "1.0?";
		double x = 1.0;
		assertEquals(answer, StringUtil.replaceFirstQuestionMark("??", x));
	}

	@Test
	public final void testReplaceFirstQuestionMarkStringLong() {
		String answer = "1?";
		long x = 1L;
		assertEquals(answer, StringUtil.replaceFirstQuestionMark("??", x));
	}

	@Test
	public final void testReplaceFirstQuestionMarkStringFloat() {
		String answer = "1.0?";
		float x = 1L;
		assertEquals(answer, StringUtil.replaceFirstQuestionMark("??", x));
	}

	@Test
	public final void testReplaceFirstQuestionMarkStringChar() {
		String answer = "1?";
		char x = '1';
		assertEquals(answer, StringUtil.replaceFirstQuestionMark("??", x));
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
		String x = "I have white spaces";
		assertTrue(StringUtil.constainsWhiteSpace(x));
	}

	@Test
	public final void testContainsAnyStringCharArray() {
		String x = "I have white spaces";
		assertTrue(StringUtil.containsAny(x, new char[] { 'w', 'x', 'y' }));
	}

	@Test
	public final void testContainsAnyStringString() {
		String x = "I have white spaces";
		assertTrue(StringUtil.containsAny(x, "wxy"));
	}

	@Test
	public final void testContainsSubString() {
		String x = "I have white spaces";
		assertTrue(StringUtil.containsSubString(x, "whit"));
	}

	@Test
	public final void testIndexOfAnyButStringString() {
		String x = "abcdefg";
		assertEquals(StringUtil.indexOfAnyBut(x, "abcdef"), 6);
	}

	@Test
	public final void testIndexOfAnyButStringCharArray() {
		String x = "abcdefg";
		assertEquals(StringUtil.indexOfAnyBut(x, "abcdef".toCharArray()), 6);
	}

	@Test
	public final void testContainsOnlyStringCharArray() {
		String x = "I only contain vowels";
		assertFalse(StringUtil.containsOnly(x, "aeiou".toCharArray()));

	}

	@Test
	public final void testContainsOnlyStringString() {
		String x = "I only contain vowels";
		assertFalse(StringUtil.containsOnly(x, "aeiou"));
	}

	@Test
	public final void testReplace() {
		String oldText = "I only contain vowels";
		String newText = "I only contain consonants";
		assertEquals(StringUtil.replace(oldText, "vowels", "consonants"), newText);
	}

	@Test
	public final void testToInputStream() {
		String x = "abcdefg";
		assertTrue(StringUtil.toInputStream(x) instanceof InputStream );
	}

	@Test
	public final void testGetContentOfStream() {
		try {
			String x = "abcdefg";
			InputStream stream = StringUtil.toInputStream(x);
			assertEquals(x, StringUtil.getContentOfStream(stream));
		} catch (IOException ioe) {
			fail("IOException");
		}
	}

	@Test
	public final void testIsEqualIgnoreCase() {
		String x = "abcdefg";
		String y = "ABCDEFG";
		assertTrue(StringUtil.isEqualIgnoreCase(x, y));
	}

	@Test
	public final void testIsEqualObjectInt() {
		Object x = "1";
		assertTrue(StringUtil.isEqual(x, 1));
	}

	@Test
	public final void testIsEqualStringString() {
		assertTrue(StringUtil.isEqual("1", "1"));
	}

	@Test
	public final void testIsEqualObjectObject() {
		Object x = "1";
		Object y = Integer.valueOf(1);
		assertTrue(StringUtil.isEqual(x, y));
	}

	@Test
	public final void testTrim() {
		assertNull( StringUtil.trim(null) );
		
		String x = "  lots of spaces   ";
		assertEquals(StringUtil.trim(x), x.trim() );
	}
	
	
	@Test
	public final void testWildCardMatch() {
		assertNull( StringUtil.trim(null) );
		
		String x = "lots of spaces";
		
		assertTrue( StringUtil.wildCardMatch(x, "*?*", true));
		assertTrue( StringUtil.wildCardMatch(x, "*lot?*", true));
		assertFalse( StringUtil.wildCardMatch(x, "*lot", true) );
		assertFalse( StringUtil.wildCardMatch(x, "?", true) );
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
