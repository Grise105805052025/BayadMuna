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
import net.bytebuddy.matcher.ElementMatchers.any
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

@ExtendWith(InstantTaskExecutorExtension::class, MockKExtension::class)
class RegistrationViewModelTest {

    @MockK
    private lateinit var mockService: LoginAndRegistrationService

    @MockK
    private lateinit var mockRepository: LoginRepository

    private lateinit var viewModel: RegistrationViewModel
    
    private val initialUsername = "testUser"
    private val initialPassword = "testPassword"
    private val validPassword = "testPassword1!"
    
    @BeforeEach
    fun setUp() {
        every { mockService.register(any(), any(), any()) } returns mockk()

        viewModel = RegistrationViewModel(
            username = initialUsername,
            password = initialPassword,
            cpassword = initialPassword,
            service = mockService,
            repository = mockRepository
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
        fun `initializes cpassword correctly`() {
            assertEquals(
                initialPassword,
                viewModel.cpassword.value,
                "Supplied cpassword should match."
            )
        }

        @Test
        fun `passes service and repository to base class`() {
            assertEquals(
                mockService,
                viewModel.service,
                "Supplied service should match."
            )
            assertEquals(
                mockRepository,
                viewModel.repository,
                "Supplied repository should match."
            )
        }
    }

    @Nested
    inner class InputValidation {
        @Test
        fun `allowRegister returns false when username is empty`() {
            viewModel.username.value = ""
            assertFalse(
                viewModel.allowRegister(),
                "Should return false for empty username.")
        }

        @Test
        fun `allowRegister returns false when username is null`() {
            viewModel.username.value = null
            assertFalse(
                viewModel.allowRegister(),
                "Should return false for null username.")
        }

        @Test
        fun `allowRegister returns false when password is empty`() {
            viewModel.password.value = ""
            assertFalse(
                viewModel.allowRegister(),
                "Should return false for empty password."
            )
        }

        @Test
        fun `allowRegister returns false when password is null`() {
            viewModel.password.value = null
            assertFalse(
                viewModel.allowRegister(),
                "Should return false for null password."
            )
        }

        @Test
        fun `allowRegister returns false when cpassword is empty`() {
            viewModel.cpassword.value = ""
            assertFalse(
                viewModel.allowRegister(),
                "Should return false for empty password."
            )
        }

        @Test
        fun `allowRegister returns false when cpassword is null`() {
            viewModel.cpassword.value = null
            assertFalse(
                viewModel.allowRegister(),
                "Should return false for null password."
            )
        }
    }

    @Nested
    inner class PasswordValidation {
        @ParameterizedTest(name = "Fails for weak password: {0}")
        @ValueSource(
            strings = [
                "pass",         // Too short
                "password1!",   // No uppercase
                "PASSWORD1",    // No special character
                "PASSWORD!"     // No digit
            ]
        )
        fun `validatePassword returns false for passwords that do not meet complexity requirements`(password: String) {
            viewModel.password.value = password
            viewModel.cpassword.value = password
            assertFalse(
                viewModel.register(mockk()),
                "Should return false for invalid password: $password"
            )
        }

        @Test
        fun `validatePassword returns true for a strong, matching password`() {
            viewModel.password.value = validPassword
            viewModel.cpassword.value = validPassword
            assertTrue(
                viewModel.register(mockk()),
                "Should return true for a valid password."
            )
        }
    }

    @Nested
    inner class RegistrationAction {
        @MockK
        private lateinit var mockListener: OnCompleteListener<AuthResult>

        @Test
        fun `register returns false and does not call service if inputs are invalid`() {
            viewModel.username.value = ""

            val result = viewModel.register(mockListener)

            assertFalse(
                result,
                "Register should return false for invalid inputs."
            )
            verify(exactly = 0) {
                mockService.register(
                    any(),
                    any(),
                    any())
            }
        }

        @Test
        fun `register returns true and calls service with correct credentials if inputs are valid`() {
            val expectedUser = initialUsername
            val expectedPass = validPassword
            viewModel.username.value = expectedUser
            viewModel.password.value = expectedPass
            viewModel.cpassword.value = expectedPass

            val result = viewModel.register(mockListener)

            assertTrue(
                result,
                "Register should return true for valid inputs."
            )
            verify(exactly = 1) {
                mockService.register(
                    expectedUser,
                    expectedPass,
                    mockListener)
            }
        }
    }

}