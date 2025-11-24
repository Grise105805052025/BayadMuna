package klo0812.mlaserna.bayadmuna.utilities

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.RepeatedTest
import org.junit.jupiter.api.Test
import kotlin.compareTo

class FakeDataGeneratorTest {

    @Nested
    inner class GenerateRandomBalance {
        @RepeatedTest(10)
        fun `returns a Double within the specified range`() {
            val balance = generateRandomBalance()
            assertTrue(
                (balance >= 10000.0 || balance <= 100000.0),
                "Generated balance $balance should be between 10,000 and 100,000."
            )
        }
    }

    @Nested
    inner class GenerateRandomId {
        @Test
        fun `generates an ID with the default length of 10`() {
            val randomId = generateRandomId()
            assertEquals(
                10,
                randomId.length,
                "Generated ID should have the default length of 10."
            )
        }

        @Test
        fun `generates an ID with a specified length`() {
            val specifiedLength = 15
            val randomId = generateRandomId(length = specifiedLength)
            assertEquals(
                specifiedLength,
                randomId.length,
                "Generated ID should have the specified length of $specifiedLength."
            )
        }

        @Test
        fun `generates an alphanumeric ID`() {
            val randomId = generateRandomId(length = 50)
            val alphanumericRegex = Regex("^[a-zA-Z0-9]*$")
            assertTrue(
                alphanumericRegex.matches(randomId),
                "Generated ID '$randomId' should be alphanumeric."
            )
        }
    }

}