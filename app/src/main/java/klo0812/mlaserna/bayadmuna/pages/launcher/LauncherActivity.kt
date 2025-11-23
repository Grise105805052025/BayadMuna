package klo0812.mlaserna.bayadmuna.pages.launcher

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import klo0812.mlaserna.bayadmuna.R
import klo0812.mlaserna.bayadmuna.pages.login.ui.LoginActivity
import klo0812.mlaserna.bayadmuna.utilities.press
import klo0812.mlaserna.bayadmuna.utilities.rotate
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LauncherActivity : AppCompatActivity() {

    lateinit var mLogo: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.launcher_activity)
        setupViews()
        delayAnimation()
    }

    fun setupViews() {
        mLogo = findViewById(R.id.mLogo)
    }

    fun delayAnimation() {
        lifecycleScope.launch {
            delay(1000L)
            rotate(
                targetView = mLogo,
                degrees = -90f,
                duration = 1000L,
                listener = object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        press(targetView = mLogo)
                    }
                }
            )
            delay(2000L)
            navigateToLogin()
        }
    }

    fun navigateToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

}