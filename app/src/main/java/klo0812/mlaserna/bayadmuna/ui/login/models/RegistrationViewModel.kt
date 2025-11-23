package klo0812.mlaserna.bayadmuna.ui.login.models

import androidx.lifecycle.MutableLiveData
import klo0812.mlaserna.base.ui.models.BaseFragmentViewModel
import klo0812.mlaserna.bayadmuna.database.AppDataBase
import klo0812.mlaserna.bayadmuna.ui.login.database.LoginRepository
import klo0812.mlaserna.bayadmuna.ui.login.services.LoginService

class RegistrationViewModel(
    username: String,
    password: String,
    cpassword: String,
    service: LoginService,
    repository: LoginRepository
) : BaseFragmentViewModel<LoginService, AppDataBase>(
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

    fun register(): Boolean {
        if (validatePassword()) {
            service?.register(username.value!!, password.value!!)
            return true
        } else {
            return false
        }
    }

    private fun validatePassword() : Boolean {
        return if (password.value?.isEmpty() == true) {
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