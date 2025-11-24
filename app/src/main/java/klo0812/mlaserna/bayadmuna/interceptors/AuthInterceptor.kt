package klo0812.mlaserna.bayadmuna.interceptors

import android.app.Activity
import android.content.Intent
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import klo0812.mlaserna.bayadmuna.R
import klo0812.mlaserna.bayadmuna.pages.login.ui.LoginActivity
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val activity: Activity,
    private val firebaseAuth: FirebaseAuth
) : Interceptor {

    //TODO: Integrate sometime
    override fun intercept(chain: Interceptor.Chain): Response {
        val currentUser = firebaseAuth.currentUser
        var request = chain.request()

        if (currentUser != null) {
            // User is authenticated
            val idToken = try {
                // Get the ID token synchronously (use await() to block until it's available)
                runBlocking {
                    currentUser.getIdToken(true).await().token
                }
            } catch (e: Exception) {
                // Handle the error (e.g., user might have been signed out, token request failed)
                // Redirect the user to login screen
                relog()
                return chain.proceed(request)
            }
            // Add the Authorization header to the request
            request = request.newBuilder()
                .addHeader("Authorization", "Bearer $idToken")
                .build()
        } else {
            relog()
        }

        return chain.proceed(request)
    }

    fun relog() {
        Toast.makeText(activity, R.string.error_relog_auth, Toast.LENGTH_SHORT).show()
        activity.startActivity(Intent(activity, LoginActivity::class.java))
        activity.finish()
    }

}