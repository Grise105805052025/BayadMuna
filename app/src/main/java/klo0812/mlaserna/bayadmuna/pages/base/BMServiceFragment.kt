package klo0812.mlaserna.bayadmuna.pages.base

import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import klo0812.mlaserna.base.ui.fragment.BaseBindingFragment
import klo0812.mlaserna.base.ui.models.BaseFragmentViewModel
import okhttp3.OkHttpClient
import javax.inject.Inject

abstract class BMServiceFragment<
        A : BaseFragmentViewModel<*, *>,
        B : ViewModelProvider.Factory,
        C : ViewDataBinding> : BaseBindingFragment<A, B, C>() {

    @Inject
    lateinit var okHttpClient: OkHttpClient

    @Inject
    lateinit var firebaseAuth: FirebaseAuth

    fun showSnackBarMessage(message: String?, duration: Int = Snackbar.LENGTH_LONG) {
        if (lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED) && message != null) {
            Snackbar.make(viewDataBinding.root, message, duration).show()
        } else {
            // do something
        }
    }

}