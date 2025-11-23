package klo0812.mlaserna.bayadmuna.app

import android.app.Application
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BayadMunaApp : Application() {

    companion object {
        val TAG: String? = BayadMunaApp::class.simpleName

        @Volatile private var firebaseAuth: FirebaseAuth? = null

        fun getFirebaseAuth (): FirebaseAuth {
            return firebaseAuth ?: synchronized(this) {
                firebaseAuth ?: FirebaseAuth.getInstance().also { firebaseAuth = it }
            }
        }
    }

    override fun onCreate() {
        super.onCreate()
    }

}