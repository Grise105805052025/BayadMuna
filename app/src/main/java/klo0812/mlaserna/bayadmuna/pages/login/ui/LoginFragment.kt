package klo0812.mlaserna.bayadmuna.pages.login.ui

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import klo0812.mlaserna.base.ui.models.BaseActivityViewModel
import klo0812.mlaserna.base.ui.models.BaseActivityViewModelFactory
import klo0812.mlaserna.bayadmuna.R
import klo0812.mlaserna.bayadmuna.databinding.FragmentLoginBinding
import klo0812.mlaserna.bayadmuna.pages.base.BMServiceFragment
import klo0812.mlaserna.bayadmuna.pages.login.database.LoginRepository
import klo0812.mlaserna.bayadmuna.pages.login.models.LoginViewModel
import klo0812.mlaserna.bayadmuna.pages.login.models.LoginViewModelFactory
import klo0812.mlaserna.bayadmuna.pages.login.navigation.LoginNavigation
import klo0812.mlaserna.bayadmuna.pages.login.services.LoginAndRegistrationService
import klo0812.mlaserna.bayadmuna.utilities.hideKeyboard
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : BMServiceFragment<LoginViewModel, LoginViewModelFactory, FragmentLoginBinding>() {

    companion object {
        val TAG: String? = LoginFragment::class.simpleName
    }

    @Inject
    lateinit var loginAndRegistrationService: LoginAndRegistrationService

    @Inject
    lateinit var loginRepository: LoginRepository

    @Inject
    lateinit var loginNavigation: LoginNavigation

    lateinit var activityViewModel: BaseActivityViewModel

    override fun initViewModels() {
        super.initViewModels()
        activityViewModel = ViewModelProvider(
            requireActivity(),
            BaseActivityViewModelFactory(progress = false, navigating = false)
        )[BaseActivityViewModel::class.java]
    }

    override fun initViewModelFactory(): LoginViewModelFactory {
        return LoginViewModelFactory(
            username = "",
            password = "",
            service = loginAndRegistrationService,
            repository = loginRepository
        )
    }

    override fun viewModelClass(): Class<LoginViewModel> {
        return LoginViewModel::class.java
    }

    override fun getFragmentLayout(): Int {
        return R.layout.fragment_login
    }

    override fun initiateViews() {
        super.initiateViews()
        activityViewModel.navigating.value = false
        setupLogin()
        viewDataBinding.mRegister.setOnClickListener {
            loginNavigation.navigate(LoginActivity.Navigation.REGISTER)
        }
    }

    fun setupLogin() {
        viewDataBinding.mLogin.setOnClickListener {
            (requireActivity() as AppCompatActivity).hideKeyboard()
            if (activityViewModel.progress.value != true) {
                activityViewModel.progress.value = true
                lifecycleScope.launch {
                    val result = viewModel.login( { task ->
                        if (task.isSuccessful) {
                            loginSuccess(
                                task.result.user?.uid ?: "",
                                task.result.user?.email ?: ""
                            )
                        } else {
                            showSnackBarMessage(task.exception?.message)
                            activityViewModel.progress.value = false
                        }
                    })
                    if (!result) {
                        delay(200)
                        //TODO: Update fields to show incompleteness
                        activityViewModel.progress.value = false
                    }
                }
            }
        }
    }

    fun loginSuccess(id: String, email: String) {
        if (id.isEmpty() || email.isEmpty()) {
            showSnackBarMessage(getString(R.string.error_generic))
            return
        }
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                (viewModel.repository as LoginRepository).purgeLogout() // in case old data is intact
                (viewModel.repository as LoginRepository).saveLogin(
                    id,
                    email
                )
            }
            loginNavigation.navigate(LoginActivity.Navigation.MAIN)
            activityViewModel.progress.value = false
        }
    }

    override fun initiateObservers() {
        super.initiateObservers()
        viewModel.username.observe(viewLifecycleOwner, {
            Log.d(TAG, "Username changed to $it.")
            viewDataBinding.mLogin.isEnabled = viewModel.allowLogin()
        })
        viewModel.password.observe(viewLifecycleOwner, {
            Log.d(TAG, "Password changed to $it.")
            viewDataBinding.mLogin.isEnabled = viewModel.allowLogin()
        })
    }

}