package klo0812.mlaserna.bayadmuna.ui.login.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.hilt.android.AndroidEntryPoint
import klo0812.mlaserna.bayadmuna.R
import klo0812.mlaserna.bayadmuna.databinding.FragmentRegistrationBinding
import klo0812.mlaserna.bayadmuna.ui.base.BMServiceFragment
import klo0812.mlaserna.bayadmuna.ui.login.database.LoginRepository
import klo0812.mlaserna.bayadmuna.ui.login.models.RegistrationViewModel
import klo0812.mlaserna.bayadmuna.ui.login.models.RegistrationViewModelFactory
import klo0812.mlaserna.bayadmuna.ui.login.services.LoginService
import javax.inject.Inject

@AndroidEntryPoint
class RegistrationFragment : BMServiceFragment<RegistrationViewModel, RegistrationViewModelFactory, FragmentRegistrationBinding>() {

    companion object {
        val TAG: String? = RegistrationFragment::class.simpleName
    }

    @Inject lateinit var loginService: LoginService

    @Inject lateinit var loginRepository: LoginRepository

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun initiateObservers() {
        super.initiateObservers()
        viewModel.username.observe(viewLifecycleOwner, {
            Log.d(TAG, "Username changed to $it")
        })
        viewModel.password.observe(viewLifecycleOwner, {
            Log.d(TAG, "Password changed to $it")
        })
        viewModel.cpassword.observe(viewLifecycleOwner, {
            Log.d(TAG, "Confirm password changed to $it")
        })
    }

    override fun initiateViews() {
        super.initiateViews()
        viewDataBinding.mRegister.setOnClickListener {
            viewModel.register()
        }
        viewDataBinding.mBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    override fun initViewModelFactory(): RegistrationViewModelFactory {
        return RegistrationViewModelFactory(
            username = "",
            password = "",
            cpassword = "",
            service = loginService,
            repository = loginRepository
        )
    }

    override fun viewModelClass(): Class<RegistrationViewModel> {
        return RegistrationViewModel::class.java
    }

    override fun getFragmentLayout(): Int {
        return R.layout.fragment_registration
    }

}