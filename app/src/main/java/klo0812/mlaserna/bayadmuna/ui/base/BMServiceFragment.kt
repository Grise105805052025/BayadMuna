package klo0812.mlaserna.bayadmuna.ui.base

import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import klo0812.mlaserna.base.ui.fragment.BaseBindingFragment
import klo0812.mlaserna.base.ui.models.BaseFragmentViewModel
import klo0812.mlaserna.bayadmuna.ui.login.navigation.LoginNavigation
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

    @Inject
    lateinit var mainNavigation: LoginNavigation

}