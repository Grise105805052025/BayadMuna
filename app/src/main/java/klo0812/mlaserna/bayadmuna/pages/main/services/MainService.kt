package klo0812.mlaserna.bayadmuna.pages.main.services

import com.google.firebase.auth.FirebaseAuth
import klo0812.mlaserna.base.services.BaseService
import okhttp3.OkHttpClient
import javax.inject.Inject

class MainService @Inject constructor(
    override val httpClient: OkHttpClient,
    val firebaseAuth: FirebaseAuth,
) : BaseService(httpClient) {

    companion object {
        val TAG: String? = MainService::class.simpleName
    }

}