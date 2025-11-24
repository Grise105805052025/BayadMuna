package klo0812.mlaserna.bayadmuna.pages.login.models

import androidx.lifecycle.MutableLiveData
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import klo0812.mlaserna.base.ui.models.BaseFragmentViewModel
import klo0812.mlaserna.bayadmuna.database.AppDataBase
import klo0812.mlaserna.bayadmuna.pages.login.database.LoginRepository
import klo0812.mlaserna.bayadmuna.pages.login.services.LoginAndRegistrationService
import klo0812.mlaserna.bayadmuna.utilities.validateString

class RegistrationViewModel(
    username: String,
    password: String,
    cpassword: String,
    service: LoginAndRegistrationService,
    repository: LoginRepository
) : BaseFragmentViewModel<LoginAndRegistrationService, AppDataBase>(
    service,
    repository
) {

    companion object {
        const val PWD_REGEX = "^(?=.*[A-Z])(?=.*[!@#\$%^&*(),.?\":{}|<>])(?=.*[0-9]).{8,}\$"
    }

    // Let's use MutableLiveData for direct databinding
    val username: MutableLiveData<String> = MutableLiveData(username)
    val password: MutableLiveData<String> = MutableLiveData(password)
    val cpassword: MutableLiveData<String> = MutableLiveData(cpassword)

    fun allowRegister(): Boolean {
        return validateUserName() && validateString(password.value) && validateString(cpassword.value)
    }

    fun register(listener: OnCompleteListener<AuthResult>): Boolean {
        if (validateUserName() && validatePassword()) {
            service?.register(username.value!!, password.value!!, listener)
            return true
        } else {
            return false
        }
    }

    private fun validateUserName() : Boolean {
        return validateString(username.value)
    }

    private fun validatePassword() : Boolean {
        //TODO: Make this more elegant
        return if (validateString(password.value) && validateString(cpassword.value)) {
            false
        } else if (password.value != cpassword.value) {
            false
        } else {
            val password = password.value ?: return false
            if (!PWD_REGEX.toRegex().matches(password)) {
                if (password.length < 8) {
                    return false
                }
                if (!password.any { it.isUpperCase() }) {
                    return false
                }
                if (!password.any { it.isDigit() }) {
                    return false
                }
                if (!password.any { it in "!@#\$%^&*(),.?\":{}|<>" }) {
                    return false
                }
            }
            return true
        }
    }

}