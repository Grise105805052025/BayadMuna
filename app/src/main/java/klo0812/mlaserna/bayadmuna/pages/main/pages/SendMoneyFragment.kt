package klo0812.mlaserna.bayadmuna.pages.main.pages

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import klo0812.mlaserna.base.ui.models.BaseActivityViewModel
import klo0812.mlaserna.base.ui.models.BaseActivityViewModelFactory
import klo0812.mlaserna.bayadmuna.R
import klo0812.mlaserna.bayadmuna.databinding.FragmentSendMoneyBinding
import klo0812.mlaserna.bayadmuna.pages.base.BMServiceFragment
import klo0812.mlaserna.bayadmuna.pages.login.ui.LoginFragment.Companion.TAG
import klo0812.mlaserna.bayadmuna.pages.main.database.MainRepository
import klo0812.mlaserna.bayadmuna.pages.main.models.SendMoneyViewModel
import klo0812.mlaserna.bayadmuna.pages.main.models.SendMoneyViewModelFactory
import klo0812.mlaserna.bayadmuna.pages.main.models.WalletViewModel
import klo0812.mlaserna.bayadmuna.pages.main.models.WalletViewModelFactory
import klo0812.mlaserna.bayadmuna.pages.main.navigation.MainNavigation
import klo0812.mlaserna.bayadmuna.pages.main.services.MainService
import klo0812.mlaserna.bayadmuna.utilities.formatMoney
import klo0812.mlaserna.bayadmuna.utilities.hideKeyboard
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class SendMoneyFragment : BMServiceFragment<SendMoneyViewModel, SendMoneyViewModelFactory, FragmentSendMoneyBinding>() {

    @Inject
    lateinit var mainService: MainService

    @Inject
    lateinit var mainRepository: MainRepository

    @Inject
    lateinit var mainNavigation: MainNavigation

    lateinit var walletViewModel: WalletViewModel
    lateinit var activityViewModel: BaseActivityViewModel

    override fun initViewModels() {
        walletViewModel = ViewModelProvider(
            requireActivity(),
            WalletViewModelFactory(
                username = "",
                balance = 0.0,
                showBalance = true,
                service = mainService,
                repository = mainRepository
            )
        )[WalletViewModel::class.java]
        super.initViewModels()
        activityViewModel = ViewModelProvider(
            requireActivity(),
            BaseActivityViewModelFactory(progress = false, navigating = false)
        )[BaseActivityViewModel::class.java]
    }

    override fun initViewModelFactory(): SendMoneyViewModelFactory {
        return SendMoneyViewModelFactory(
            target = "",
            amount = "",
            username = walletViewModel.username.value ?: "",
            balance = walletViewModel.balance.value ?: 0.0,
            mainService,
            mainRepository
        )
    }

    override fun viewModelClass(): Class<SendMoneyViewModel> {
        return SendMoneyViewModel::class.java
    }

    override fun getFragmentLayout(): Int {
        return R.layout.fragment_send_money
    }

    override fun initiateViews() {
        super.initiateViews()
        activityViewModel.navigating.value = false
        mainNavigation.updateBalance()
        setupSendMoney()
    }

    fun setupSendMoney() {
        viewDataBinding.mSend.setOnClickListener {
            (requireActivity() as AppCompatActivity).hideKeyboard()
            if (activityViewModel.progress.value != true) {
                activityViewModel.progress.value = true
                lifecycleScope.launch {
                    withContext(Dispatchers.IO) {
                        val result = viewModel.sendMoney(context, mainService.firebaseAuth.uid ?: "")
                        if (result.code == 200 || result.code == 201) {
                            showSnackBarMessage(
                                context?.getString(R.string.message_send_money_success,
                                    result.body,
                                    result.title)
                            )
                            viewModel.target.postValue("")
                            viewModel.amount.postValue("")
                        } else {
                            showSnackBarMessage(
                                result.message ?:
                                context?.getString(R.string.error_generic))
                        }
                        activityViewModel.progress.postValue(false)
                    }
                }
            }
        }
    }

    override fun initiateObservers() {
        super.initiateObservers()
        walletViewModel.balance.observe(viewLifecycleOwner, {
            Log.d(TAG, "Balance changed to $it.")
            viewModel.balanceString.value = formatMoney(it)
        })
        viewModel.target.observe(viewLifecycleOwner, {
            Log.d(TAG, "Target changed to $it.")
            viewDataBinding.mSend.isEnabled = viewModel.allowSendMoney()
        })
        viewModel.amount.observe(viewLifecycleOwner, {
            Log.d(TAG, "Password changed to $it.")
            viewDataBinding.mSend.isEnabled = viewModel.allowSendMoney()
        })
    }

}