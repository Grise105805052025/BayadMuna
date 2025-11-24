package klo0812.mlaserna.bayadmuna.pages.main.models

import android.icu.text.DateFormat
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import klo0812.mlaserna.bayadmuna.extensions.InstantTaskExecutorExtension
import klo0812.mlaserna.bayadmuna.pages.main.models.TransactionItemViewModel
import klo0812.mlaserna.bayadmuna.utilities.formatDate
import klo0812.mlaserna.bayadmuna.utilities.formatMoney
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(InstantTaskExecutorExtension::class)
class TransactionItemViewModelTest {

    @Test
    fun `initializes and formats data correctly`() {
        val target = "Recipient Name"
        val amount = 100.00
        val date = System.currentTimeMillis()
        val fakeDateString = ""

        mockkStatic(DateFormat::class)
        val mockedDateFormat: DateFormat = mockk()
        every { mockedDateFormat.format(date) } returns fakeDateString
        every { DateFormat.getDateInstance(any()) } returns mockedDateFormat

        val viewModel = TransactionItemViewModel(target, amount, date)

        assertEquals(
            target,
            viewModel.target.value,
            "Target should match the provided value."
        )
        assertEquals(
            formatMoney(amount),
            viewModel.amountString.value,
            "Formatted amount should match the provided value."
        )
        assertEquals(
            formatDate(date),
            viewModel.dateString.value,
            "Formatted date should match the provided mocked value."
        )
    }

}