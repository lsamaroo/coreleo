package com.coreleo.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.io.InputStream;

import org.junit.jupiter.api.Test;

class StringUtilTest {

    @Test
    final void testgetNumericCharacters() {
        final var phone1 = "1 (347) 878-1536";
        final var phone2 = "1-347-878-1536";
        final var phone3 = "+1 347 878 1536             ";
        final var result = "13478781536";

        assertEquals(result, StringUtil.getNumericCharacters(phone1));
        assertEquals(result, StringUtil.getNumericCharacters(phone2));
        assertEquals(result, StringUtil.getNumericCharacters(phone3));
    }

    @Test
    final void testReplaceAllNamedParameter() {
        final var string = "This is a {namedParam} and so is this one: {namedParam}";
        final var result = "This is a value and so is this one: value";
        assertEquals(result, StringUtil.replaceAllNamedParameter(string, "namedParam", "value"));

    }

    @Test
    final void testReplaceNamedParameter() {
        final var string = "This is a {namedParam}";
        final var result = "This is a value";
        assertEquals(result, StringUtil.replaceNamedParameter(string, "namedParam", "value"));
    }

    @Test
    final void testToLowerCaseCharAt() {
        final var x = "STRINGUTIL";
        assertEquals("STRINGUTIL", StringUtil.toLowerCaseCharAt(x, 10));
        assertEquals("sTRINGUTIL", StringUtil.toLowerCaseCharAt(x, 0));
        assertEquals("STRINGUTIl", StringUtil.toLowerCaseCharAt(x, 9));
        assertEquals("STRiNGUTIL", StringUtil.toLowerCaseCharAt(x, 3));
    }

    @Test
    final void testReplaceLastQuestionMark() {
        final var answer = "?1";
        assertEquals(answer, StringUtil.replaceLastQuestionMark("??", "1"));
    }

    @Test
    final void testDeleteFirstQuestionMark() {
        final var answer = "?";
        assertEquals(answer, StringUtil.deleteFirstQuestionMark("??"));
    }

    @Test
    final void testReplaceFirstQuestionMarkStringString() {
        final var answer = "1?";
        assertEquals(answer, StringUtil.replaceFirstQuestionMark("??", "1"));
    }

    @Test
    final void testReplaceFirstQuestionMarkStringObject() {
        final var answer = "1?";
        final Object x = "1";
        assertEquals(answer, StringUtil.replaceFirstQuestionMark("??", x));
    }

    @Test
    final void testReplaceFirstQuestionMarkStringInt() {
        final var answer = "1?";
        final var x = 1;
        assertEquals(answer, StringUtil.replaceFirstQuestionMark("??", x));
    }

    @Test
    final void testReplaceFirstQuestionMarkStringBoolean() {
        final var answer = "false?";
        final var x = false;
        assertEquals(answer, StringUtil.replaceFirstQuestionMark("??", x));
    }

    @Test
    final void testReplaceFirstQuestionMarkStringDouble() {
        final var answer = "1.0?";
        final var x = 1.0;
        assertEquals(answer, StringUtil.replaceFirstQuestionMark("??", x));
    }

    @Test
    final void testReplaceFirstQuestionMarkStringLong() {
        final var answer = "1?";
        final var x = 1L;
        assertEquals(answer, StringUtil.replaceFirstQuestionMark("??", x));
    }

    @Test
    final void testReplaceFirstQuestionMarkStringFloat() {
        final var answer = "1.0?";
        final float x = 1L;
        assertEquals(answer, StringUtil.replaceFirstQuestionMark("??", x));
    }

    @Test
    final void testReplaceFirstQuestionMarkStringChar() {
        final var answer = "1?";
        final var x = '1';
        assertEquals(answer, StringUtil.replaceFirstQuestionMark("??", x));
    }

    @Test
    final void testIsAllEmpty() {
        assertTrue(StringUtil.isAllEmpty("", null, "", "     ", ""));
        assertFalse(StringUtil.isAllEmpty("", null, "", " ", "I am a non empty string"));
    }

    @Test
    final void testIsAllNotEmpty() {
        assertTrue(StringUtil.isAllNotEmpty("I", "am", "not", "  empty   "));
        assertFalse(StringUtil.isAllNotEmpty("I", "am", "not", "  empty   ", "    ", null));
    }

    @Test
    final void testIsEmpty() {
        Object x = null;
        assertTrue(StringUtil.isEmpty(x));

        x = "  ";
        assertTrue(StringUtil.isEmpty(x));
    }

    @Test
    final void testIsNotEmpty() {
        Object x = null;
        assertFalse(StringUtil.isNotEmpty(x));

        x = "I am not empty";
        assertTrue(StringUtil.isNotEmpty(x));
    }

    @Test
    final void testConstainsWhiteSpace() {
        final var x = "I have white spaces";
        assertTrue(StringUtil.constainsWhiteSpace(x));
    }

    @Test
    final void testContainsAnyStringCharArray() {
        final var x = "I have white spaces";
        assertTrue(StringUtil.containsAnyCharacters(x, new char[] { 'w', 'x', 'y' }));
    }

    @Test
    final void testContainsAnyStringString() {
        final var x = "I have white spaces";
        assertTrue(StringUtil.containsAnyCharacters(x, "wxy"));
    }

    @Test
    final void testContainsSubString() {
        final var x = "I have white spaces";
        assertTrue(StringUtil.containsSubString(x, "whit"));
    }

    @Test
    final void testIndexOfAnyButStringString() {
        final var x = "abcdefg";
        assertEquals(StringUtil.indexOfAnyBut(x, "abcdef"), 6);
    }

    @Test
    final void testIndexOfAnyButStringCharArray() {
        final var x = "abcdefg";
        assertEquals(StringUtil.indexOfAnyBut(x, "abcdef".toCharArray()), 6);
    }

    @Test
    final void testContainsOnlyStringCharArray() {
        final var x = "I only contain vowels";
        assertFalse(StringUtil.containsOnly(x, "aeiou".toCharArray()));

    }

    @Test
    final void testContainsOnlyStringString() {
        final var x = "I only contain vowels";
        assertFalse(StringUtil.containsOnly(x, "aeiou"));
    }

    @Test
    final void testReplace() {
        final var oldText = "I only contain vowels";
        final var newText = "I only contain consonants";
        assertEquals(StringUtil.replace(oldText, "vowels", "consonants"), newText);
    }

    @Test
    final void testToInputStream() {
        final var x = "abcdefg";
        assertTrue(StringUtil.toInputStream(x) instanceof InputStream);
    }

    @Test
    final void testGetContentOfStream() {
        try {
            final var x = "abcdefg";
            final var stream = StringUtil.toInputStream(x);
            assertEquals(x, StringUtil.getContentOfStream(stream));
        } catch (final IOException ioe) {
            fail("IOException");
        }
    }

    @Test
    final void testTrim() {
        assertNull(StringUtil.trim(null));

        final var x = "  lots of spaces   ";
        assertEquals(StringUtil.trim(x), x.trim());
    }

    @Test
    final void testWildCardMatch() {
        assertNull(StringUtil.trim(null));

        final var x = "lots of spaces";

        assertTrue(StringUtil.wildCardMatch(x, "*?*", true));
        assertTrue(StringUtil.wildCardMatch(x, "*lot?*", true));
        assertFalse(StringUtil.wildCardMatch(x, "*lot", true));
        assertFalse(StringUtil.wildCardMatch(x, "?", true));
    }

    @Test
    final void testToStringObjectObject() {

    }

    @Test
    final void testToStringObject() {

    }

    @Test
    final void testToLowerCase() {

    }

    @Test
    final void testToUpperCase() {

    }

    @Test
    final void testEndsWith() {

    }

    @Test
    final void testIsAlphaNumeric() {

    }

    @Test
    final void testIsAlpha() {

    }

    @Test
    final void testIsNumeric() {

    }

    @Test
    final void testIsAlphaAllowSpaces() {

    }

    @Test
    final void testIsNumericAllowSpaces() {

    }

    @Test
    final void testIsAlphaNumericAllowSpaces() {

    }

    @Test
    final void testIsAlphaNumericAllowSpacesHyphenUnderscore() {

    }

    @Test
    final void testIsAlphaNumericAllowHyphenUnderscore() {

    }

    @Test
    final void testIsAlphaNumericAllowSpacesHyphenUnderscorePeriodComma() {

    }

    @Test
    final void testDropCharactersFromEnd() {

    }

    @Test
    final void testRemoveSpaces() {

    }

    @Test
    final void testGetUniqueId() {

    }

    @Test
    final void testSplit() {

    }

    @Test
    final void testIsValidEmail() {

    }

    @Test
    final void testIsValidIPAddress() {

    }

    @Test
    final void testCompareIgnoreCase() {

    }

    @Test
    final void testCompare() {

    }

}
