package klo0812.mlaserna.bayadmuna.pages.main.services

import com.google.firebase.auth.FirebaseAuth
import com.google.gson.Gson
import klo0812.mlaserna.base.services.BaseService
import klo0812.mlaserna.bayadmuna.pages.main.models.JSONPlaceHolderResponseModel
import klo0812.mlaserna.bayadmuna.utilities.formatMoney
import klo0812.mlaserna.bayadmuna.utilities.generateRandomId
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject

class MainService @Inject constructor(
    override val httpClient: OkHttpClient,
    val firebaseAuth: FirebaseAuth,
) : BaseService(httpClient) {

    companion object {
        val TAG: String? = MainService::class.simpleName
        const val BASE_URL = "https://jsonplaceholder.typicode.com/"
        const val URL_FAKE_POST = BASE_URL + "posts"
    }

    fun sendMoney(userId: String, target: String, amount: Double): JSONPlaceHolderResponseModel {
        val requestBody = FormBody.Builder()
            .add("id", generateRandomId())
            .add("userId", userId)
            .add("title", target)
            .add("body", formatMoney(amount))
            .build()
        val request = buildRequest(URL_FAKE_POST, requestBody)
        try {
            val response: Response = httpClient.newCall(request).execute()
            if (!response.isSuccessful) {
                return JSONPlaceHolderResponseModel(
                    code = response.code,
                    message = response.message
                )
            }
            val body = response.body.string()
            val result = Gson().fromJson(body, JSONPlaceHolderResponseModel::class.java)
            result.code = response.code
            result.message = response.message
            return result
        } catch (e: IOException) {
            return JSONPlaceHolderResponseModel(
                exception = e
            )
        }
    }

    fun fetchTransactions(): JSONPlaceHolderResponseModel {
        val request = buildRequest(URL_FAKE_POST + "/1") // just to simulate a call and nothing else
        try {
            val response: Response = httpClient.newCall(request).execute()
            if (!response.isSuccessful) {
                return JSONPlaceHolderResponseModel(
                    code = response.code,
                    message = response.message
                )
            }
            val body = response.body.string()
            val result = Gson().fromJson(body, JSONPlaceHolderResponseModel::class.java)
            result.code = response.code
            result.message = response.message
            return result
        } catch (e: IOException) {
            return JSONPlaceHolderResponseModel(
                exception = e
            )
        }
    }

    private fun buildRequest(url: String, post: RequestBody? = null): Request {
        if (post != null) {
            return Request.Builder()
                .url(url)
                .post(post)
                .build()
        } else {
            return Request.Builder()
                .url(url)
                .build()
        }
    }

}