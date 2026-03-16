package com.cms.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RandomUtilityTest {

    @Test
    void generatePhone_defaultLength_returns12Digits() {
        String phone = RandomUtility.generatePhone();
        assertNotNull(phone);
        assertEquals(12, phone.length());
        assertTrue(phone.matches("\\d+"), "Phone should contain only digits");
    }

    @Test
    void generatePhone_customLength_returnsCorrectLength() {
        String phone = RandomUtility.generatePhone(8);
        assertNotNull(phone);
        assertEquals(8, phone.length());
        assertTrue(phone.matches("\\d+"), "Phone should contain only digits");
    }

    @Test
    void generatePhone_zeroLength_defaultsTo12() {
        String phone = RandomUtility.generatePhone(0);
        assertNotNull(phone);
        assertEquals(12, phone.length());
    }

    @Test
    void generateWord_noArgs_returnsNonEmptyString() {
        String word = RandomUtility.generatWord();
        assertNotNull(word);
        assertFalse(word.isEmpty(), "Generated word should not be empty");
    }

    @Test
    void generateWord_withLengthBounds_returnsStringWithinBounds() {
        int minLength = 3;
        int maxLength = 6;
        String word = RandomUtility.generatWord(minLength, maxLength);
        assertNotNull(word);
        // each character pair is consonant+vowel, so word length in chars = wordLength * 2
        int charLength = word.length();
        assertTrue(charLength >= minLength * 2 && charLength <= maxLength * 2,
                "Word char length " + charLength + " should be between " + (minLength * 2) + " and " + (maxLength * 2));
    }

    @Test
    void generateEmail_containsAtAndDot() {
        String email = RandomUtility.generateEmail();
        assertNotNull(email);
        assertTrue(email.contains("@"), "Email should contain '@'");
        assertTrue(email.contains("."), "Email should contain '.'");
    }

    @Test
    void generateWebsite_containsDot() {
        String website = RandomUtility.generateWebsite();
        assertNotNull(website);
        assertTrue(website.contains("."), "Website should contain '.'");
    }

    @Test
    void generateSentence_returnsNonEmptyString() {
        String sentence = RandomUtility.generateSentence();
        assertNotNull(sentence);
        assertFalse(sentence.trim().isEmpty(), "Sentence should not be empty");
    }

    @Test
    void generateNumber_returnsValueWithinRange() {
        for (int i = 0; i < 50; i++) {
            int number = RandomUtility.generateNumber(1, 10);
            assertTrue(number >= 1 && number <= 9,
                    "Number " + number + " should be in range [1, 9]");
        }
    }

    @Test
    void generateNumber_withSameBounds_returnsMin() {
        int number = RandomUtility.generateNumber(5, 6);
        assertEquals(5, number);
    }

    @Test
    void generateNumber_zero_returnsZero() {
        int number = RandomUtility.generateNumber(0, 1);
        assertEquals(0, number);
    }
}
