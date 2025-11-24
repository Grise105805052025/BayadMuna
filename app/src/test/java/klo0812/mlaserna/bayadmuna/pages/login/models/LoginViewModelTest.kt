package klo0812.mlaserna.bayadmuna.pages.login.models

import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.unmockkAll
import io.mockk.verify
import klo0812.mlaserna.bayadmuna.extensions.InstantTaskExecutorExtension
import klo0812.mlaserna.bayadmuna.pages.login.database.LoginRepository
import klo0812.mlaserna.bayadmuna.pages.login.services.LoginAndRegistrationService
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(InstantTaskExecutorExtension::class, MockKExtension::class)
class LoginViewModelTest {

    @MockK
    private lateinit var mockLoginService: LoginAndRegistrationService

    @MockK
    private lateinit var mockLoginRepository: LoginRepository

    private lateinit var viewModel: LoginViewModel

    private val initialUsername = "testUser"
    private val initialPassword = "testPassword"

    @BeforeEach
    fun setUp() {
        every { mockLoginService.login(any(), any(), any()) } returns mockk()

        viewModel = LoginViewModel(
            username = initialUsername,
            password = initialPassword,
            service = mockLoginService,
            repository = mockLoginRepository
        )
    }

    @AfterEach
    fun tearDown() {
        unmockkAll()
    }

    @Nested
    inner class Initialization {
        @Test
        fun `initializes username correctly`() {
            assertEquals(
                initialUsername,
                viewModel.username.value,
                "Supplied username should match."
            )
        }

        @Test
        fun `initializes password correctly`() {
            assertEquals(
                initialPassword,
                viewModel.password.value,
                "Supplied password should match."
            )
        }

        @Test
        fun `passes service and repository to base class`() {
            assertEquals(
                mockLoginService,
                viewModel.service,
                "Supplied service should match."
            )
            assertEquals(
                mockLoginRepository,
                viewModel.repository,
                "Supplied repository should match."
            )
        }
    }

    @Nested
    inner class InputValidation {
        @Test
        fun `allowLogin returns false when username is empty`() {
            viewModel.username.value = ""
            assertFalse(
                viewModel.allowLogin(),
                "Should return false for empty username."
            )
        }

        @Test
        fun `allowLogin returns false when username is null`() {
            viewModel.username.value = null
            assertFalse(
                viewModel.allowLogin(),
                "Should return false for null username."
            )
        }

        @Test
        fun `allowLogin returns false when password is empty`() {
            viewModel.password.value = ""
            assertFalse(
                viewModel.allowLogin(),
                "Should return false for empty password."
            )
        }

        @Test
        fun `allowLogin returns false when password is null`() {
            viewModel.password.value = null
            assertFalse(
                viewModel.allowLogin(),
                "Should return false for null password."
            )
        }

        @Test
        fun `allowLogin returns true for non-empty username and password`() {
            viewModel.username.value = initialUsername
            viewModel.password.value = initialPassword
            assertTrue(
                viewModel.allowLogin(),
                "Should return true with valid username and password."
            )
        }
    }

    @Nested
    inner class LoginAction {

        @MockK
        private lateinit var mockListener: OnCompleteListener<AuthResult>

        @Test
        fun `login returns false and does not call service if credentials are invalid`() {
            viewModel.username.value = ""
            viewModel.password.value = initialPassword

            val result = viewModel.login(mockListener)

            assertFalse(
                result,
                "Login should return false for invalid inputs."
            )
            verify(exactly = 0) {
                mockLoginService.login(
                    any(),
                    any(),
                    any())
            }
        }

        @Test
        fun `login returns true and calls service with correct credentials if inputs are valid`() {
            val expectedUser = initialUsername
            val expectedPass = initialPassword
            viewModel.username.value = expectedUser
            viewModel.password.value = expectedPass

            val result = viewModel.login(mockListener)

            assertTrue(
                result,
                "Login should return true for valid inputs."
            )
            verify(exactly = 1) {
                mockLoginService.login(
                    expectedUser,
                    expectedPass,
                    mockListener)
            }
        }
    }

}