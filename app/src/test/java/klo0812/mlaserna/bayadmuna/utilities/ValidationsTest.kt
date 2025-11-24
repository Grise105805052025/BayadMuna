package klo0812.mlaserna.bayadmuna.utilities

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class ValidationsTest {

    @Nested
    inner class ValidateString {
        @Test
        fun `returns false for an empty string`() {
            assertFalse(
                validateString(""),
                "Should return false for an empty string."
            )
        }

        @Test
        fun `returns false for a null string`() {
            assertFalse(
                validateString(null),
                "Should return false for null."
            )
        }

        @Test
        fun `returns true for a non-empty string`() {
            assertTrue(
                validateString("Hello World!"),
                "Should return true for a valid string."
            )
        }

        @Test
        fun `returns true for a string with only whitespace`() {
            assertTrue(
                validateString("  "),
                "Should return true for a string with only whitespace."
            )
        }
    }

}