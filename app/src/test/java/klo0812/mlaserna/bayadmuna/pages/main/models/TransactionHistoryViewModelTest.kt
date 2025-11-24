package klo0812.mlaserna.bayadmuna.pages.main.models

import android.icu.text.DateFormat
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkAll
import klo0812.mlaserna.bayadmuna.database.entities.TransactionEntity
import klo0812.mlaserna.bayadmuna.extensions.InstantTaskExecutorExtension
import klo0812.mlaserna.bayadmuna.pages.main.database.MainRepository
import klo0812.mlaserna.bayadmuna.pages.main.models.JSONPlaceHolderResponseModel
import klo0812.mlaserna.bayadmuna.pages.main.models.TransactionHistoryViewModel
import klo0812.mlaserna.bayadmuna.pages.main.models.TransactionItemViewModel
import klo0812.mlaserna.bayadmuna.pages.main.services.MainService
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.StringFormat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(InstantTaskExecutorExtension::class, MockKExtension::class)
class TransactionHistoryViewModelTest {

    @MockK
    private lateinit var mockService: MainService

    @MockK
    private lateinit var mockRepository: MainRepository

    private lateinit var viewModel: TransactionHistoryViewModel

    private val userID = "userID-123"
    private val currentUsername = "user@test.com"
    private val targetUsername = "recipient@test.com"

    @BeforeEach
    fun setUp() {
        viewModel = TransactionHistoryViewModel(
            username = currentUsername,
            service = mockService,
            repository = mockRepository
        )
    }

    @AfterEach
    fun tearDown() {
        unmockkAll()
    }

    @Nested
    inner class FetchTransactions {
        @Test
        fun `fetchTransactions clears old data and adds new data on success`() = runTest {
            val userId = userID
            val date = System.currentTimeMillis()
            val fakeDateString = ""

            mockkStatic(DateFormat::class)
            val mockedDateFormat: DateFormat = mockk()
            every { mockedDateFormat.format(date) } returns fakeDateString
            every { DateFormat.getDateInstance(any()) } returns mockedDateFormat

            val dbTransactions = listOf(
                TransactionEntity(id = "1", mockk(), target = "A", amount = 10.0, date = date),
                TransactionEntity(id = "2", mockk(), target = "B", amount = 20.0, date = date)
            )
            coEvery { mockService.fetchTransactions() } returns JSONPlaceHolderResponseModel(code = 200)
            coEvery { mockRepository.getTransactions(userId) } returns dbTransactions

            // Initially we have one transaction stored in the model
            viewModel.transactions.add(TransactionItemViewModel(targetUsername, 0.0, date))

            // This will change the number of transactions stored in the model to 2
            val result = viewModel.fetchTransactions(userId)

            assertEquals(
                200,
                result.code,
                "Result should return successful (200)."
            )
            assertEquals(
                2,
                viewModel.transactions.size,
                "Model should contain 2 new transactions."
            )
            assertEquals(
                "A",
                viewModel.transactions[0].target.value,
                "First transaction should have target A."
            )
            assertEquals(
                "B",
                viewModel.transactions[1].target.value,
                "First transaction should have target B."
            )
        }

        @Test
        fun `fetchTransactions does not clear or add data on failure`() = runTest {
            val userId = userID
            val date = System.currentTimeMillis()
            val fakeDateString = ""

            mockkStatic(DateFormat::class)
            val mockedDateFormat: DateFormat = mockk()
            every { mockedDateFormat.format(date) } returns fakeDateString
            every { DateFormat.getDateInstance(any()) } returns mockedDateFormat

            val dbTransactions = listOf(
                TransactionEntity(id = "1", mockk(), target = "A", amount = 10.0, date = date),
                TransactionEntity(id = "2", mockk(), target = "B", amount = 20.0, date = date)
            )
            coEvery { mockService.fetchTransactions() } returns JSONPlaceHolderResponseModel(code = 500)
            coEvery { mockRepository.getTransactions(userId) } returns dbTransactions

            // Initially we have one transaction stored in the model
            viewModel.transactions.add(TransactionItemViewModel(targetUsername, 0.0, date))

            // This will NOT change the number of transactions stored in the model to 2
            val result = viewModel.fetchTransactions(userId)

            assertEquals(
                500,
                result.code,
                "Result should return unsuccessful (500)."
            )
            assertEquals(
                1,
                viewModel.transactions.size,
                "Transaction list should be unchanged."
            )
            assertEquals(
                targetUsername,
                viewModel.transactions[0].target.value,
                "First transaction should remain unchanged hence the target should be $targetUsername"
            )
        }
    }

}