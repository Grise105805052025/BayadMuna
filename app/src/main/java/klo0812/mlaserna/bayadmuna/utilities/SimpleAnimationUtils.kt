package klo0812.mlaserna.bayadmuna.utilities

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.View

fun rotate(targetView: View, degrees: Float, duration: Long, listener: Animator.AnimatorListener? = null) {
    targetView.animate().rotation(degrees).setListener(listener).setDuration(duration).start()
}

fun press(targetView: View) {
    val scaleDownX = ObjectAnimator.ofFloat(targetView, "scaleX", 1f, 0.9f)
    val scaleDownY = ObjectAnimator.ofFloat(targetView, "scaleY", 1f, 0.9f)
    val scaleDown = AnimatorSet()
    scaleDown.playTogether(scaleDownX, scaleDownY)
    scaleDown.setDuration(100)

    val scaleUpX = ObjectAnimator.ofFloat(targetView, "scaleX", 0.9f, 1f)
    val scaleUpY = ObjectAnimator.ofFloat(targetView, "scaleY", 0.9f, 1f)
    val scaleUp = AnimatorSet()
    scaleUp.playTogether(scaleUpX, scaleUpY)
    scaleUp.setDuration(100)

    scaleDown.addListener(object : AnimatorListenerAdapter() {
        override fun onAnimationEnd(animation: Animator) {
            scaleUp.start()
        }
    })
    scaleDown.start()
}