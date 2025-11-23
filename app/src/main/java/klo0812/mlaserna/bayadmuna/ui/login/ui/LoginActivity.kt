package klo0812.mlaserna.bayadmuna.ui.login.ui

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.Insets
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import dagger.hilt.android.AndroidEntryPoint
import klo0812.mlaserna.bayadmuna.R
import klo0812.mlaserna.bayadmuna.ui.login.navigation.LoginNavigation
import klo0812.mlaserna.bayadmuna.utilities.ThemeChanger

@AndroidEntryPoint
class LoginActivity : AppCompatActivity(), LoginNavigation {

    enum class Fragments {
        LOGIN,
        REGISTER,
    }

    private lateinit var mFragmentContainer: FrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ThemeChanger.Companion.randomizeTheme(this)
        setContentView(R.layout.login_activity)
        initViews()
        navigate(Fragments.LOGIN)
    }

    private fun initViews() {
        mFragmentContainer = findViewById(R.id.fragment_container)
        ViewCompat.setOnApplyWindowInsetsListener(
            mFragmentContainer
        ) { v: View?, insets: WindowInsetsCompat? ->
            val systemBars: Insets = insets!!.getInsets(WindowInsetsCompat.Type.systemBars())
            v!!.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            WindowInsetsCompat.CONSUMED
        }
    }

    override fun navigate(fragment: Fragments) {
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