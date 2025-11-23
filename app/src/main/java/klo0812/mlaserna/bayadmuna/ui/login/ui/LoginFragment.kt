package klo0812.mlaserna.bayadmuna.ui.login.ui

import android.util.Log
import dagger.hilt.android.AndroidEntryPoint
import klo0812.mlaserna.bayadmuna.R
import klo0812.mlaserna.bayadmuna.databinding.FragmentLoginBinding
import klo0812.mlaserna.bayadmuna.ui.base.BMServiceFragment
import klo0812.mlaserna.bayadmuna.ui.login.database.LoginRepository
import klo0812.mlaserna.bayadmuna.ui.login.models.LoginViewModel
import klo0812.mlaserna.bayadmuna.ui.login.models.LoginViewModelFactory
import klo0812.mlaserna.bayadmuna.ui.login.services.LoginService
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : BMServiceFragment<LoginViewModel, LoginViewModelFactory, FragmentLoginBinding>() {

    companion object {
        val TAG: String? = LoginFragment::class.simpleName
    }

    @Inject lateinit var loginService: LoginService

    @Inject lateinit var loginRepository: LoginRepository

    override fun initiateObservers() {
        super.initiateObservers()
        viewModel.username.observe(viewLifecycleOwner, {
            Log.d(TAG, "Username changed to $it")
        })
        viewModel.password.observe(viewLifecycleOwner, {
            Log.d(TAG, "Password changed to $it")
        })
    }

    override fun initViewModelFactory(): LoginViewModelFactory {
        return LoginViewModelFactory(
            username = "",
            password = "",
            service = loginService,
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
        viewDataBinding.mRegister.setOnClickListener {
            mainNavigation.navigate(LoginActivity.Fragments.REGISTER)
        }
    }

}