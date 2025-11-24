package klo0812.mlaserna.bayadmuna.pages.main.pages

import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint
import klo0812.mlaserna.base.ui.models.BaseActivityViewModel
import klo0812.mlaserna.base.ui.models.BaseActivityViewModelFactory
import klo0812.mlaserna.bayadmuna.R
import klo0812.mlaserna.bayadmuna.databinding.FragmentBalanceBinding
import klo0812.mlaserna.bayadmuna.pages.base.BMServiceFragment
import klo0812.mlaserna.bayadmuna.pages.main.database.MainRepository
import klo0812.mlaserna.bayadmuna.pages.main.models.WalletViewModel
import klo0812.mlaserna.bayadmuna.pages.main.models.WalletViewModelFactory
import klo0812.mlaserna.bayadmuna.pages.main.services.MainService
import javax.inject.Inject

@AndroidEntryPoint
class BalanceFragment : BMServiceFragment<WalletViewModel, WalletViewModelFactory, FragmentBalanceBinding>() {

    @Inject
    lateinit var mainService: MainService

    @Inject
    lateinit var mainRepository: MainRepository

    lateinit var activityViewModel: BaseActivityViewModel

    override fun initViewModels() {
        super.initViewModels()
        activityViewModel = ViewModelProvider(
            requireActivity(),
            BaseActivityViewModelFactory(progress = false, navigating = false)
        )[BaseActivityViewModel::class.java]
    }

    override fun initViewModelFactory(): WalletViewModelFactory {
        return WalletViewModelFactory(
            username = "",
            balance = 0.0,
            showBalance = true,
            mainService,
            mainRepository
        )
    }

    override fun viewModelClass(): Class<WalletViewModel> {
        return WalletViewModel::class.java
    }

    override fun getFragmentLayout(): Int {
        return R.layout.fragment_balance
    }

    override fun initiateViews() {
        super.initiateViews()
        activityViewModel.navigating.value = false
        updateTitle()
    }

    private fun updateTitle() {
        viewDataBinding.mTitle.setText(getString(R.string.title_main, viewModel.username.value))
    }

    override fun initiateObservers() {
        super.initiateObservers()
        viewModel.showBalance.observe(viewLifecycleOwner, {
            viewDataBinding.mShowHideAmount.setText(
                if (!it) R.string.action_amount_show else R.string.action_amount_hide
            )
        })
    }

}