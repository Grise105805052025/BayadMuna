package klo0812.mlaserna.bayadmuna.pages.main.ui

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import klo0812.mlaserna.base.ui.activity.BaseBindingActivity
import klo0812.mlaserna.bayadmuna.R
import klo0812.mlaserna.bayadmuna.database.AppDataBase
import klo0812.mlaserna.bayadmuna.databinding.MainActivityBinding
import klo0812.mlaserna.bayadmuna.pages.main.database.MainRepository
import klo0812.mlaserna.bayadmuna.pages.main.models.MainViewModel
import klo0812.mlaserna.bayadmuna.pages.main.models.MainViewModelFactory
import klo0812.mlaserna.bayadmuna.pages.main.models.WalletViewModel
import klo0812.mlaserna.bayadmuna.pages.main.models.WalletViewModelFactory
import klo0812.mlaserna.bayadmuna.pages.main.navigation.MainNavigation
import klo0812.mlaserna.bayadmuna.pages.main.pages.BalanceFragment
import klo0812.mlaserna.bayadmuna.pages.main.pages.SendMoneyFragment
import klo0812.mlaserna.bayadmuna.pages.main.pages.TransactionsFragment
import klo0812.mlaserna.bayadmuna.pages.main.services.MainService
import klo0812.mlaserna.bayadmuna.utilities.FakeDataGenerator
import klo0812.mlaserna.bayadmuna.utilities.ThemeChanger
import klo0812.mlaserna.bayadmuna.utilities.press
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseBindingActivity<
        MainViewModel,
        MainViewModelFactory,
        MainActivityBinding>(), MainNavigation {

    companion object {
        val TAG: String? = MainActivity::class.simpleName
    }

    enum class Navigation {
        BALANCE,
        PAYMENT,
        HISTORY
    }

    @Inject
    lateinit var okHttpClient: OkHttpClient

    @Inject
    lateinit var firebaseAuth: FirebaseAuth

    @Inject
    lateinit var database: AppDataBase

    @Inject
    lateinit var fakeDataGenerator: FakeDataGenerator

    @Inject
    lateinit var mainRepository: MainRepository

    @Inject
    lateinit var mainService: MainService

    lateinit var walletViewModel: WalletViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        ThemeChanger.randomizeTheme(this)
        super.onCreate(savedInstanceState)
    }

    override fun initViewModelFactory(): MainViewModelFactory {
        return MainViewModelFactory(progress = false, navigating = false)
    }

    override fun viewModelClass(): Class<MainViewModel> {
        return MainViewModel::class.java
    }

    override fun getFragmentLayout(): Int {
        return R.layout.main_activity
    }

    override fun initiateViews() {
        super.initiateViews()
        hideSystemBars()
        initWallet()
    }

    fun hideSystemBars() {
        val window = window
        val decorView = window.decorView
        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowInsetsControllerCompat(window, decorView).let { controller ->
            controller.hide(WindowInsetsCompat.Type.systemBars())
            controller.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    }

    fun initWallet() {
        lifecycleScope.launch {
            val walletData = withContext(Dispatchers.IO) {
                fakeDataGenerator.generateUserData(this@MainActivity, firebaseAuth.currentUser?.uid ?: "")
                database.walletDao().get(firebaseAuth.currentUser?.uid ?: "")
            }
            walletViewModel = ViewModelProvider(
                initViewModelStore(),
                WalletViewModelFactory(
                    username = walletData?.userEntity?.email ?: "",
                    balance = walletData?.balance ?: 0.0,
                    showBalance = true,
                    service = mainService,
                    repository = mainRepository
                )
            )[WalletViewModel::class]
            initLoopingBackgroundCalls()
            viewModel.selectedMenu.postValue(0)
        }
    }

    fun initLoopingBackgroundCalls() {
        lifecycleScope.launch {
            flow {
                while (true) {
                    emit(Unit)
                    delay(5000)
                }
            }.collect {
                updateBalance() // keep checking balance in the background
            }
        }
    }

    override fun initiateObservers() {
        super.initiateObservers()
        initMenuObservers()
    }

    fun initMenuObservers() {
        viewModel.selectedMenu.observe(this, {
            Log.d(TAG, "Changing menu to: $it.")
            when (it) {
                0 -> {
                    press(targetView = viewDataBinding.mMenuI1)
                    navigate(Navigation.BALANCE)
                }
                1 -> {
                    press(targetView = viewDataBinding.mMenuI2)
                    navigate(Navigation.PAYMENT)
                }
                2 -> {
                    press(targetView = viewDataBinding.mMenuI3)
                    navigate(Navigation.HISTORY)
                }
                3 -> {
                    press(targetView = viewDataBinding.mMenuI4)
                    showLogoutDialog()
                }
            }
        })
    }

    override fun updateBalance() {
        Log.d(TAG, "Updating balance...")
        lifecycleScope.launch {
            val balance = withContext(Dispatchers.IO) {
                // Ideally this is an actual functioning backend service
                mainRepository.getBalance(mainService.firebaseAuth.currentUser?.uid)
            }
            walletViewModel.balance.postValue(balance)
        }
    }

    override fun navigate(next: Navigation) {
        if (viewModel.navigating.value != true) {
            viewModel.navigating.value = true
            val nextFragment = when (next) {
                Navigation.BALANCE -> {
                    BalanceFragment()
                }
                Navigation.PAYMENT -> {
                    SendMoneyFragment()
                }
                Navigation.HISTORY -> {
                    TransactionsFragment()
                }
            }
            if (nextFragment != null) {
                lifecycleScope.launch {
                    delay(200)
                    supportFragmentManager.beginTransaction()
                        .replace(viewDataBinding.fragmentContainer.id, nextFragment)
                        .addToBackStack(next.name)
                        .commit()
                    viewModel.navigating.value = false
                }
            } else {
                viewModel.navigating.value = false
            }
        } else {
            Log.w(TAG, "Navigation still in progress.")
            viewModel.navigating.value = false
        }
    }

    override fun onBackPressed() {
        if (viewModel.navigating.value != true) {
            viewModel.navigating.value = true
            val fragmentCount = supportFragmentManager.backStackEntryCount
            if (fragmentCount <= 1) {
                showLogoutDialog()
            } else {
                super.onBackPressed()
            }
            viewModel.navigating.value = false
        } else {
            Log.w(TAG, "Navigation still in progress.")
        }
    }

    val exitDialogListener = DialogInterface.OnClickListener { dialog, which ->
        if (which == DialogInterface.BUTTON_POSITIVE) {
            dialog.dismiss()
            viewModel.navigating.value = true
            lifecycleScope.launch {
                firebaseAuth.signOut()
                delay(200)
                finish()
                viewModel.navigating.value = false
            }
        } else {
            viewModel.selectedMenu.postValue(viewModel.lastFragment.value)
            dialog.dismiss()
        }
    }

    private fun showLogoutDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Are you sure you want to logout?")
        builder.setPositiveButton(R.string.response_yes, exitDialogListener)
        builder.setNegativeButton(R.string.response_no, exitDialogListener)
        builder.setCancelable(false)
        builder.create().show()
    }

}