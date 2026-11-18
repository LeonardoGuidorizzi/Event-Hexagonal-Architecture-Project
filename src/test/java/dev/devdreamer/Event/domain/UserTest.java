package dev.devdreamer.Event.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UserTest {
    @Test
    void normalizeEmailTrimsWhitespaceAndConvertsToLowercase() {
        assertEquals("person@example.com", User.normalizeEmail("  Person@Example.COM  "));
    }

    @Test
    void normalizeEmailRejectsNullAndBlankValues() {
        assertThrows(IllegalArgumentException.class, () -> User.normalizeEmail(null));
        assertThrows(IllegalArgumentException.class, () -> User.normalizeEmail("  "));
    }
}
