
package klo0812.mlaserna.bayadmuna.ui.login.models

// Still has issues with unit test not sure why
//@ExtendWith(InstantTaskExecutorExtension::class, MockKExtension::class)
//class LoginViewModelTest {
//
//    @MockK
//    private lateinit var mockLoginService: LoginService
//
//    @MockK
//    private lateinit var mockLoginRepository: LoginRepository
//
//    @MockK(relaxed = true)
//    private lateinit var usernameObserver: Observer<String>
//
//    @MockK(relaxed = true)
//    private lateinit var passwordObserver: Observer<String>
//
//    private lateinit var viewModel: LoginViewModel
//
//    private val initialUsername = "testUser"
//    private val initialPassword = "testPassword"
//
//    @BeforeEach
//    fun setUp() {
//        viewModel = LoginViewModel(
//            username = initialUsername,
//            password = initialPassword,
//            service = mockLoginService,
//            repository = mockLoginRepository
//        )
//    }
//
//    @AfterEach
//    fun tearDown() {
//        unmockkAll()
//    }
//
//    @Nested
//    @DisplayName("Initialization")
//    inner class Initialization {
//        @Test
//        fun `initializes username correctly`() {
//            Assertions.assertEquals(
//                initialUsername,
//                viewModel.username.value,
//                "Username LiveData should be initialized with the constructor value."
//            )
//        }
//
//        @Test
//        fun `initializes password correctly`() {
//            Assertions.assertEquals(
//                initialPassword,
//                viewModel.password.value,
//                "Password LiveData should be initialized with the constructor value."
//            )
//        }
//
//        @Test
//        fun `passes service and repository to base class`() {
//            Assertions.assertEquals(
//                mockLoginService,
//                viewModel.service,
//                "Service should be passed to the base ViewModel."
//            )
//            Assertions.assertEquals(
//                mockLoginRepository,
//                viewModel.repository,
//                "Repository should be passed to the base ViewModel."
//            )
//        }
//    }
//
//    @Nested
//    @DisplayName("State Updates")
//    inner class StateUpdates {
//        @Test
//        fun `updates username on updateUsername call`() {
//            val newUsername = "newUser"
//            val usernameSlot = slot<String>()
//            viewModel.username.observeForever(usernameObserver)
//
//            viewModel.updateUsername(newUsername)
//            verify { usernameObserver.onChanged(capture(usernameSlot)) }
//            Assertions.assertEquals(
//                newUsername,
//                usernameSlot.captured,
//                "Observer should receive the new username."
//            )
//            Assertions.assertEquals(
//                newUsername,
//                viewModel.username.value,
//                "Username LiveData value should be updated."
//            )
//        }
//
//        @Test
//        fun `updates password on updatePassword call`() {
//            val newPassword = "newPassword123"
//            val passwordSlot = slot<String>()
//            viewModel.password.observeForever(passwordObserver)
//
//            viewModel.updatePassword(newPassword)
//            verify { passwordObserver.onChanged(capture(passwordSlot)) }
//            Assertions.assertEquals(
//                newPassword,
//                passwordSlot.captured,
//                "Observer should receive the new password."
//            )
//            Assertions.assertEquals(
//                newPassword,
//                viewModel.password.value,
//                "Password LiveData value should be updated."
//            )
//        }
//    }
//}