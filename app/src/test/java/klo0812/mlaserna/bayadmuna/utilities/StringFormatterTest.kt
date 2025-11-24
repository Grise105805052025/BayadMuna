package klo0812.mlaserna.bayadmuna.utilities

import android.icu.text.DateFormat
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject
import io.mockk.mockkStatic
import net.bytebuddy.matcher.ElementMatchers.any
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.util.Date
import java.util.Locale

class StringFormatterTest {

    @Nested
    inner class FormatMoney {
        @Test
        fun `formats a double into a currency string with commas`() {
            val amount = 12345.67
            val expected = "12,345.67"
            assertEquals(
                expected,
                formatMoney(amount),
                "Formatted amount should match the expected value."
            )
        }

        @Test
        fun `formats a smaller double correctly`() {
            val amount = 123.45
            val expected = "123.45"
            assertEquals(
                expected,
                formatMoney(amount),
                "Formatted amount should not have commas."
            )
        }

        @Test
        fun `formats zero correctly`() {
            val amount = 0.0
            val expected = "0.00"
            assertEquals(
                expected,
                formatMoney(amount),
                "Formatted amount should be zero with a decimal point followed by two zeroes."
            )
        }
    }

    @Nested
    inner class HideMoney {
        @Test
        fun `replaces digits with asterisks but keeps the decimal point`() {
            val amount = 12345.67
            val expected = "*****.**"
            assertEquals(
                expected,
                hideMoney(amount),
                "Formatted amount should covert all numbers to *, remove commas, but keeps the decimal point."
            )
        }

        @Test
        fun `handles smaller amounts correctly`() {
            val amount = 987.65
            val expected = "***.**"
            assertEquals(
                expected,
                hideMoney(amount),
                "Formatted amount should covert all numbers to * but keeps the decimal point."
            )
        }

        @Test
        fun `handles zero correctly`() {
            val amount = 0.0
            val expected = "*.**"
            assertEquals(
                expected,
                hideMoney(amount),
                "Formatted amount zero should be $expected."
            )
        }
    }

    @Nested
    inner class FormatDate {
        @Test
        fun `formats a milliseconds timestamp into a medium date string`() {
            val timestamp = 0L
            val expected = "Jan 1, 2024"
            mockkStatic(DateFormat::class)
            val mockedDateFormat: DateFormat = mockk()
            every { mockedDateFormat.format(timestamp) } returns expected
            every { DateFormat.getDateInstance(any()) } returns mockedDateFormat

            assertEquals(
                expected,
                formatDate(timestamp),
                "Formatted date should match the expected mocked value."
            )
        }
    }

}