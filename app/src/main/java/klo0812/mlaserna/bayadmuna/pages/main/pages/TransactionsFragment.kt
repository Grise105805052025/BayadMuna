package klo0812.mlaserna.bayadmuna.pages.main.pages

import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import klo0812.mlaserna.base.ui.models.BaseActivityViewModel
import klo0812.mlaserna.base.ui.models.BaseActivityViewModelFactory
import klo0812.mlaserna.bayadmuna.R
import klo0812.mlaserna.bayadmuna.databinding.FragmentTransactionsBinding
import klo0812.mlaserna.bayadmuna.pages.base.BMServiceFragment
import klo0812.mlaserna.bayadmuna.pages.main.adapters.TransactionAdapter
import klo0812.mlaserna.bayadmuna.pages.main.adapters.TransactionItemDecoration
import klo0812.mlaserna.bayadmuna.pages.main.database.MainRepository
import klo0812.mlaserna.bayadmuna.pages.main.models.TransactionHistoryViewModel
import klo0812.mlaserna.bayadmuna.pages.main.models.TransactionHistoryViewModelFactory
import klo0812.mlaserna.bayadmuna.pages.main.navigation.MainNavigation
import klo0812.mlaserna.bayadmuna.pages.main.services.MainService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@AndroidEntryPoint
class TransactionsFragment : BMServiceFragment<
        TransactionHistoryViewModel,
        TransactionHistoryViewModelFactory,
        FragmentTransactionsBinding>() {

    @Inject
    lateinit var mainService: MainService

    @Inject
    lateinit var mainRepository: MainRepository

    @Inject
    lateinit var mainNavigation: MainNavigation

    lateinit var activityViewModel: BaseActivityViewModel

    override fun initViewModels() {
        super.initViewModels()
        activityViewModel = ViewModelProvider(
            requireActivity(),
            BaseActivityViewModelFactory(progress = false, navigating = false)
        )[BaseActivityViewModel::class.java]
    }

    override fun initViewModelFactory(): TransactionHistoryViewModelFactory {
        return TransactionHistoryViewModelFactory(
            username = "",
            transactions = arrayListOf(),
            mainService,
            mainRepository
        )
    }

    override fun viewModelClass(): Class<TransactionHistoryViewModel> {
        return TransactionHistoryViewModel::class.java
    }

    override fun getFragmentLayout(): Int {
        return R.layout.fragment_transactions
    }

    override fun initiateViews() {
        super.initiateViews()
        activityViewModel.navigating.value = false
        mainNavigation.updateBalance()
        setupRecyclerView()
        fetchTransactions()
        bindItemsToRecyclerView()
    }

    private fun setupRecyclerView() {
        val verticalSpacing = resources.getDimensionPixelSize(R.dimen.spacing_elements_medium)
        val horizontalSpacing = 0

        val itemDecoration = TransactionItemDecoration(
            verticalSpaceHeight = verticalSpacing,
            horizontalSpaceWidth = horizontalSpacing
        )

        viewDataBinding.mRecyclerView.layoutManager = LinearLayoutManager(context)
        viewDataBinding.mRecyclerView.addItemDecoration(itemDecoration)
        viewDataBinding.mSwipeRefreshLayout.setOnRefreshListener {
            fetchTransactions()
        }
    }

    private fun fetchTransactions() {
        if (activityViewModel.progress.value != true) {
            activityViewModel.progress.value = true
            lifecycleScope.launch {
                withContext(Dispatchers.IO) {
                    val result = viewModel.fetchTransactions(mainService.firebaseAuth.uid ?: "")
                    if (result.code == 200 || result.code == 201) {
                        withContext(Dispatchers.Main) {
                            bindItemsToRecyclerView()
                        }
                    } else {
                        showSnackBarMessage(
                            result.message ?:
                            context?.getString(R.string.error_generic))
                    }
                    activityViewModel.progress.postValue(false)
                }
                viewDataBinding.mSwipeRefreshLayout.isRefreshing = false
            }
        }
    }

    private fun bindItemsToRecyclerView() {
        if (viewModel.transactions.isNotEmpty()) {
            viewDataBinding.mRecyclerView.adapter = TransactionAdapter(viewModel.transactions)
            viewDataBinding.mRecyclerView.visibility = View.VISIBLE
            viewDataBinding.mEmptyMessage.visibility = View.GONE
        } else {
            viewDataBinding.mRecyclerView.visibility = View.GONE
            viewDataBinding.mEmptyMessage.visibility = View.VISIBLE
        }
    }

}