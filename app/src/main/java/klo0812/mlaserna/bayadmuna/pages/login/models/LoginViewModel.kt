package klo0812.mlaserna.bayadmuna.pages.login.models

import androidx.lifecycle.MutableLiveData
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import klo0812.mlaserna.base.ui.models.BaseFragmentViewModel
import klo0812.mlaserna.bayadmuna.database.AppDataBase
import klo0812.mlaserna.bayadmuna.pages.login.database.LoginRepository
import klo0812.mlaserna.bayadmuna.pages.login.services.LoginAndRegistrationService

class LoginViewModel(
    username: String,
    password: String,
    service: LoginAndRegistrationService,
    repository: LoginRepository
) : BaseFragmentViewModel<LoginAndRegistrationService, AppDataBase>(
    service,
    repository
) {

    // Let's use MutableLiveData for direct databinding
    val username: MutableLiveData<String> = MutableLiveData(username)
    val password: MutableLiveData<String> = MutableLiveData(password)

    fun allowLogin(): Boolean {
        return validateUserName() && validatePassword()
    }

    fun login(listener: OnCompleteListener<AuthResult>): Boolean {
        if (validateUserName() && validatePassword()) {
            this@LoginViewModel.service?.login(username.value!!, password.value!!, listener)
            return true
        } else {
            return false
        }
    }

    private fun validateUserName() : Boolean {
        return username.value?.isEmpty() != true
    }

    private fun validatePassword() : Boolean {
        return password.value?.isEmpty() != true
    }

}