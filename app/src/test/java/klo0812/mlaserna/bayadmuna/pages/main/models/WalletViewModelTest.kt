package klo0812.mlaserna.bayadmuna.pages.main.models

import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import klo0812.mlaserna.bayadmuna.extensions.InstantTaskExecutorExtension
import klo0812.mlaserna.bayadmuna.pages.main.database.MainRepository
import klo0812.mlaserna.bayadmuna.pages.main.models.WalletViewModel
import klo0812.mlaserna.bayadmuna.pages.main.services.MainService
import klo0812.mlaserna.bayadmuna.utilities.formatMoney
import klo0812.mlaserna.bayadmuna.utilities.hideMoney
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(InstantTaskExecutorExtension::class, MockKExtension::class)
class WalletViewModelTest {

    @MockK
    private lateinit var mockService: MainService

    @MockK
    private lateinit var mockRepository: MainRepository

    private lateinit var viewModel: WalletViewModel

    private val initialUsername = "testUser"
    private val initialBalance = 1500.00

    @BeforeEach
    fun setUp() {
        viewModel = WalletViewModel(
            username = initialUsername,
            balance = initialBalance,
            showBalance = true,
            service = mockService,
            repository = mockRepository
        )
    }

    @Nested
    inner class BalanceVisibility {
        @Test
        fun `toggleBalanceVisibility changes showBalance from true to false`() {
            viewModel.showBalance.value = true
            viewModel.toggleBalanceVisibility()
            assertFalse(
                viewModel.showBalance.value!!,
                "showBalance should be false after toggle."
            )
        }

        @Test
        fun `toggleBalanceVisibility changes showBalance from false to true`() {
            viewModel.showBalance.value = false
            viewModel.toggleBalanceVisibility()
            assertTrue(
                viewModel.showBalance.value!!,
                "showBalance should be true after toggle."
            )
        }

        @Test
        fun `checkMoneyVisibility shows formatted money when showBalance is true`() {
            viewModel.showBalance.value = true
            viewModel.checkMoneyVisibility()
            assertEquals(
                formatMoney(initialBalance),
                viewModel.balanceString.value,
                "balanceString should be show correct formatting when visible."
            )
        }

        @Test
        fun `checkMoneyVisibility shows hidden money when showBalance is false`() {
            viewModel.showBalance.value = false
            viewModel.checkMoneyVisibility()
            assertEquals(
                hideMoney(initialBalance),
                viewModel.balanceString.value,
                "balanceString should be show correct formatting when hidden."
            )
        }
    }

}