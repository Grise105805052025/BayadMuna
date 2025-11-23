package klo0812.mlaserna.bayadmuna.ui.login.ui

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.app.AlertDialog
import androidx.core.graphics.Insets
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import dagger.hilt.android.AndroidEntryPoint
import klo0812.mlaserna.base.ui.activity.BaseBindingActivity
import klo0812.mlaserna.base.ui.models.BaseActivityViewModel
import klo0812.mlaserna.base.ui.models.BaseActivityViewModelFactory
import klo0812.mlaserna.bayadmuna.R
import klo0812.mlaserna.bayadmuna.databinding.LoginActivityBinding
import klo0812.mlaserna.bayadmuna.ui.login.navigation.LoginNavigation
import klo0812.mlaserna.bayadmuna.utilities.ThemeChanger

@AndroidEntryPoint
class LoginActivity : BaseBindingActivity<
        BaseActivityViewModel,
        BaseActivityViewModelFactory,
        LoginActivityBinding>(), LoginNavigation {

    enum class Fragments {
        LOGIN,
        REGISTER,
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
        navigate(Fragments.LOGIN)
    }

    override fun navigate(fragment: Fragments) {
        viewModel.navigating.value = true
        val nextFragment = when (fragment) {
            Fragments.LOGIN -> {
                LoginFragment()
            }
            Fragments.REGISTER -> {
                RegistrationFragment()
            }
        }
        supportFragmentManager.beginTransaction()
            .replace(mFragmentContainer.id, nextFragment)
            .addToBackStack(nextFragment.tag)
            .commit()
    }

    override fun onBackPressed() {
        val fragmentCount = supportFragmentManager.backStackEntryCount
        if (fragmentCount == 0) {
            showExitDialog()
        } else {
            super.onBackPressed()
        }
    }

    val exitDialogListener = DialogInterface.OnClickListener { dialog, which ->
        if (which == DialogInterface.BUTTON_POSITIVE) {
            finish()
        }
    }

    private fun showExitDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Are you sure you want to exit?")
        builder.setPositiveButton(R.string.response_yes, exitDialogListener)
        builder.setNegativeButton(R.string.response_no, exitDialogListener)
        builder.create().show()
    }

}