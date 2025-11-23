package klo0812.mlaserna.bayadmuna.pages.login.ui

import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import klo0812.mlaserna.base.ui.models.BaseActivityViewModel
import klo0812.mlaserna.base.ui.models.BaseActivityViewModelFactory
import klo0812.mlaserna.bayadmuna.R
import klo0812.mlaserna.bayadmuna.databinding.FragmentRegistrationBinding
import klo0812.mlaserna.bayadmuna.pages.base.BMServiceFragment
import klo0812.mlaserna.bayadmuna.pages.login.database.LoginRepository
import klo0812.mlaserna.bayadmuna.pages.login.models.RegistrationViewModel
import klo0812.mlaserna.bayadmuna.pages.login.models.RegistrationViewModelFactory
import klo0812.mlaserna.bayadmuna.pages.login.services.LoginAndRegistrationService
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class RegistrationFragment : BMServiceFragment<RegistrationViewModel, RegistrationViewModelFactory, FragmentRegistrationBinding>() {

    companion object {
        val TAG: String? = RegistrationFragment::class.simpleName
    }

    @Inject lateinit var loginAndRegistrationService: LoginAndRegistrationService

    @Inject lateinit var loginRepository: LoginRepository

    lateinit var activityViewModel: BaseActivityViewModel

    override fun initViewModels() {
        super.initViewModels()
        activityViewModel = ViewModelProvider(
            requireActivity(),
            BaseActivityViewModelFactory(progress = false, navigating = false)
        )[BaseActivityViewModel::class.java]
    }

    override fun initViewModelFactory(): RegistrationViewModelFactory {
        return RegistrationViewModelFactory(
            username = "",
            password = "",
            cpassword = "",
            service = loginAndRegistrationService,
            repository = loginRepository
        )
    }

    override fun viewModelClass(): Class<RegistrationViewModel> {
        return RegistrationViewModel::class.java
    }

    override fun getFragmentLayout(): Int {
        return R.layout.fragment_registration
    }

    override fun initiateViews() {
        super.initiateViews()
        activityViewModel.navigating.value = false
        setupRegister()
        viewDataBinding.mBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    fun setupRegister() {
        viewDataBinding.mRegister.setOnClickListener {
            if (activityViewModel.progress.value != true) {
                activityViewModel.progress.value = true
                lifecycleScope.launch {
                    val result = viewModel.register( { task ->
                        if (task.isSuccessful) {
                            requireActivity().onBackPressed()
                            showSnackBarMessage(getString(R.string.message_registration_success, viewModel.username.value))
                        } else {
                            showSnackBarMessage(task.exception?.message)
                        }
                        activityViewModel.progress.value = false
                    })
                    if (!result) {
                        delay(200)
                        //TODO: Update fields to show incompleteness
                        showSnackBarMessage(getString(R.string.dscp_password_requirements))
                        activityViewModel.progress.value = false
                    }
                }
            }
        }
    }

    override fun initiateObservers() {
        super.initiateObservers()
        viewModel.username.observe(viewLifecycleOwner, {
            Log.d(TAG, "Username changed to $it")
            viewDataBinding.mRegister.isEnabled = viewModel.allowRegister()
        })
        viewModel.password.observe(viewLifecycleOwner, {
            Log.d(TAG, "Password changed to $it")
            viewDataBinding.mRegister.isEnabled = viewModel.allowRegister()
        })
        viewModel.cpassword.observe(viewLifecycleOwner, {
            Log.d(TAG, "Confirm password changed to $it")
            viewDataBinding.mRegister.isEnabled = viewModel.allowRegister()
        })
    }

}