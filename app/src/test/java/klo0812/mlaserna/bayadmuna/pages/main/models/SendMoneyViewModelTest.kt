package klo0812.mlaserna.bayadmuna.pages.main.models

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.unmockkAll
import klo0812.mlaserna.bayadmuna.database.entities.TransactionEntity
import klo0812.mlaserna.bayadmuna.extensions.InstantTaskExecutorExtension
import klo0812.mlaserna.bayadmuna.pages.main.database.MainRepository
import klo0812.mlaserna.bayadmuna.pages.main.services.MainService
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

@ExtendWith(InstantTaskExecutorExtension::class, MockKExtension::class)
class SendMoneyViewModelTest {

    @MockK
    private lateinit var mockService: MainService

    @MockK(relaxed = true)
    private lateinit var mockRepository: MainRepository

    private lateinit var viewModel: SendMoneyViewModel

    private val userID = "userID-123"
    private val currentUsername = "user@test.com"
    private val targetUsername = "recipient@test.com"
    private val initialBalance = 500.0

    @BeforeEach
    fun setUp() {
        viewModel = SendMoneyViewModel(
            target = targetUsername,
            amount = "100.0",
            username = currentUsername,
            balance = initialBalance,
            service = mockService,
            repository = mockRepository
        )
    }

    @AfterEach
    fun tearDown() {
        unmockkAll()
    }

    @Nested
    inner class Validation {
        @Test
        fun `allowSendMoney returns false if target is empty`() {
            viewModel.target.value = ""
            assertFalse(
                viewModel.allowSendMoney(),
                "Should return false for empty target."
            )
        }

        @Test
        fun `allowSendMoney returns false if target is null`() {
            viewModel.target.value = null
            assertFalse(
                viewModel.allowSendMoney(),
                "Should return false for null target."
            )
        }

        @Test
        fun `allowSendMoney returns false if amount is empty`() {
            viewModel.amount.value = ""
            assertFalse(
                viewModel.allowSendMoney(),
                "Should return false for empty amount."
            )
        }

        @Test
        fun `allowSendMoney returns false if amount is mull`() {
            viewModel.amount.value = null
            assertFalse(
                viewModel.allowSendMoney(),
                "Should return false for null amount."
            )
        }

        @Test
        fun `validTarget returns false if target is same as username`() {
            viewModel.target.value = currentUsername
            assertFalse(
                viewModel.validTarget(),
                "Should return false if target is the same as username."
            )
        }

        @Test
        fun `validTarget returns true if target is different from username`() {
            viewModel.target.value = targetUsername
            assertTrue(
                viewModel.validTarget(),
                "Should return true if target is different from username."
            )
        }
    }

    @Nested
    inner class SendMoneyAction {
        @Test
        fun `sendMoney fails if sending to own account`() = runTest {
            viewModel.target.value = currentUsername

            val result = viewModel.sendMoney(null, "userId")

            assertEquals(
                JSONPlaceHolderResponseModel.CODE_GENERIC_ERROR,
                result.code)
            coVerify(exactly = 0) {
                mockService.sendMoney(
                    any(),
                    any(),
                    any())
            }
        }

        @Test
        fun `sendMoney fails if amount is greater than balance`() = runTest {
            coEvery { mockRepository.getBalance(any()) } returns 100.0
            viewModel.amount.value = "150.0"

            val result = viewModel.sendMoney(null, "userId")

            assertEquals(
                JSONPlaceHolderResponseModel.CODE_GENERIC_ERROR,
                result.code)
            coVerify(exactly = 0) {
                mockService.sendMoney(
                    any(),
                    any(),
                    any())
            }
        }

        @ParameterizedTest(name = "Succeeds for successful calls: {0}")
        @ValueSource(
            ints = [
                200
            ]
        )
        fun `sendMoney succeeds and updates repository on successful service calls`(code: Int) = runTest {
            val userId = userID
            val targetAmount = 50.0
            viewModel.amount.value = targetAmount.toString()
            coEvery { mockRepository.getBalance(userId) } returns initialBalance
            coEvery { mockService.sendMoney(any(), any(), any()) } returns JSONPlaceHolderResponseModel(code = 200)

            val result = viewModel.sendMoney(null, userId)

            assertEquals(code, result.code)
            coVerify(exactly = 1) {
                mockRepository.deductBalance(
                    any(),
                    any())
            }
            coVerify(exactly = 1) {
                mockRepository.addNewTransaction(any())
            }
        }

        @ParameterizedTest(name = "Succeeds for failed calls: {0}")
        @ValueSource(
            ints = [
                400,
                401,
                403,
                404,
                500,
                502,
                503,
                504
            ]
        )
        fun `sendMoney fails and does not update repository on failed service call`() = runTest {
            val userId = userID
            coEvery { mockRepository.getBalance(userId) } returns initialBalance
            coEvery { mockService.sendMoney(any(), any(), any()) } returns JSONPlaceHolderResponseModel(code = 500)

            val result = viewModel.sendMoney(null, userId)

            assertEquals(500, result.code)
            coVerify(exactly = 0) {
                mockRepository.deductBalance(
                    any(),
                    any())
            }
            coVerify(exactly = 0) {
                mockRepository.addNewTransaction(any())
            }
        }
    }

}