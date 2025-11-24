package klo0812.mlaserna.bayadmuna.pages.main.models

import klo0812.mlaserna.bayadmuna.extensions.InstantTaskExecutorExtension
import klo0812.mlaserna.bayadmuna.pages.main.models.MainViewModel
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(InstantTaskExecutorExtension::class)
class MainViewModelTest {

    private lateinit var viewModel: MainViewModel

    @BeforeEach
    fun setUp() {
        viewModel = MainViewModel(
            progress = false,
            navigating = false,
            selectedMenu = -1,
            lastFragment = -1
        )
    }

    @Nested
    inner class MenuSelection {
        @Test
        fun `selectMenu01 updates selectedMenu and lastFragment to 0`() {
            viewModel.selectMenu01()

            assertEquals(
                0,
                viewModel.selectedMenu.value,
                "selectedMenu should be 0.")
            assertEquals(
                0,
                viewModel.lastFragment.value,
                "lastFragment should be 0.")
        }

        @Test
        fun `selectMenu02 updates selectedMenu and lastFragment to 1`() {
            viewModel.selectMenu02()

            assertEquals(
                1,
                viewModel.selectedMenu.value,
                "selectedMenu should be 1.")
            assertEquals(
                1,
                viewModel.lastFragment.value,
                "lastFragment should be 1.")
        }

        @Test
        fun `selectMenu03 updates selectedMenu and lastFragment to 2`() {
            viewModel.selectMenu03()

            assertEquals(
                2,
                viewModel.selectedMenu.value,
                "selectedMenu should be 2.")
            assertEquals(
                2,
                viewModel.lastFragment.value,
                "lastFragment should be 2.")
        }

        @Test
        fun `selectMenu04 updates selectedMenu to 3 but does not change lastFragment`() {
            viewModel.lastFragment.value = 1

            viewModel.selectMenu04()

            assertEquals(
                3,
                viewModel.selectedMenu.value,
                "selectedMenu should be 3.")
            assertEquals(
                1,
                viewModel.lastFragment.value,
                "lastFragment should remain unchanged.")
        }
    }

}