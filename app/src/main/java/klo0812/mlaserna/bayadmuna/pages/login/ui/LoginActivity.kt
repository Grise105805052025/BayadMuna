package klo0812.mlaserna.bayadmuna.pages.login.ui

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.app.AlertDialog
import androidx.core.graphics.Insets
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import klo0812.mlaserna.base.ui.activity.BaseBindingActivity
import klo0812.mlaserna.base.ui.models.BaseActivityViewModel
import klo0812.mlaserna.base.ui.models.BaseActivityViewModelFactory
import klo0812.mlaserna.bayadmuna.R
import klo0812.mlaserna.bayadmuna.databinding.LoginActivityBinding
import klo0812.mlaserna.bayadmuna.pages.login.navigation.LoginNavigation
import klo0812.mlaserna.bayadmuna.pages.main.ui.MainActivity
import klo0812.mlaserna.bayadmuna.utilities.ThemeChanger
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginActivity : BaseBindingActivity<
        BaseActivityViewModel,
        BaseActivityViewModelFactory,
        LoginActivityBinding>(), LoginNavigation {

    companion object {
        val TAG: String? = LoginActivity::class.simpleName
    }

    enum class Navigation {
        LOGIN,
        REGISTER,
        MAIN,
    }

    private lateinit var mFragmentContainer: FrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        ThemeChanger.randomizeTheme(this)
        super.onCreate(savedInstanceState)
    }

    override fun initViewModelFactory(): BaseActivityViewModelFactory {
        return BaseActivityViewModelFactory(progress = false, navigating = false)
    }

    override fun viewModelClass(): Class<BaseActivityViewModel> {
        return BaseActivityViewModel::class.java
    }

    override fun getFragmentLayout(): Int {
        return R.layout.login_activity
    }

    override fun initiateViews() {
        super.initiateViews()
        mFragmentContainer = findViewById(R.id.fragment_container)
        ViewCompat.setOnApplyWindowInsetsListener(
            mFragmentContainer
        ) { v: View?, insets: WindowInsetsCompat? ->
            val systemBars: Insets = insets!!.getInsets(WindowInsetsCompat.Type.systemBars())
            v!!.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            WindowInsetsCompat.CONSUMED
        }
        navigate(Navigation.LOGIN)
    }

    override fun navigate(next: Navigation) {
        if (viewModel.navigating.value != true) {
            viewModel.navigating.value = true
            if (next == Navigation.MAIN) {
                navigateToMain()
                return
            }
            val nextFragment = when (next) {
                Navigation.LOGIN -> {
                    LoginFragment()
                }
                Navigation.REGISTER -> {
                    RegistrationFragment()
                }
                Navigation.MAIN -> {
                    null
                }
            }
            if (nextFragment != null) {
                lifecycleScope.launch {
                    delay(200)
                    supportFragmentManager.beginTransaction()
                        .replace(mFragmentContainer.id, nextFragment)
                        .addToBackStack(next.name)
                        .commit()
                }
            }
        } else {
            Log.w(TAG, "Navigation still in progress.")
        }
    }

    fun navigateToMain() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onBackPressed() {
        if (viewModel.navigating.value != true) {
            viewModel.navigating.value = true
            val fragmentCount = supportFragmentManager.backStackEntryCount
            if (fragmentCount <= 1) {
                showExitDialog()
            } else {
                super.onBackPressed()
            }
            viewModel.navigating.value = false
        } else {
            Log.w(TAG, "Navigation still in progress.")
        }
    }

    val exitDialogListener = DialogInterface.OnClickListener { _, which ->
        if (which == DialogInterface.BUTTON_POSITIVE) {
            finish()
        }
    }

    private fun showExitDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Are you sure you want to exit?")
        builder.setPositiveButton(R.string.response_yes, exitDialogListener)
        builder.setNegativeButton(R.string.response_no, exitDialogListener)
        builder.setCancelable(false)
        builder.create().show()
    }

}