package klo0812.mlaserna.bayadmuna.pages.login.services

import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import klo0812.mlaserna.base.services.BaseService
import okhttp3.OkHttpClient
import javax.inject.Inject

class LoginAndRegistrationService @Inject constructor(
    override val httpClient: OkHttpClient,
    val firebaseAuth: FirebaseAuth,
) : BaseService(httpClient) {

    companion object {
        val TAG: String? = LoginAndRegistrationService::class.simpleName
    }

    fun login(username: String, password: String, listener: OnCompleteListener<AuthResult>) {
        firebaseAuth.signInWithEmailAndPassword(
            username,
            password).addOnCompleteListener(listener)
    }

    fun register(username: String, password: String, listener: OnCompleteListener<AuthResult>) {
        firebaseAuth.createUserWithEmailAndPassword(
            username,
            password).addOnCompleteListener(listener)
    }

}