package klo0812.mlaserna.bayadmuna.ui.login.services

import com.google.firebase.auth.FirebaseAuth
import klo0812.mlaserna.base.services.BaseService
import okhttp3.OkHttpClient
import javax.inject.Inject

class LoginService @Inject constructor(
    override val httpClient: OkHttpClient,
    val firebaseAuth: FirebaseAuth,
) : BaseService(httpClient) {

    fun login(username: String, password: String) {

    }

    fun register(username: String, password: String) {

    }

}