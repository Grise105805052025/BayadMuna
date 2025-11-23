package klo0812.mlaserna.bayadmuna.ui.login.models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import klo0812.mlaserna.bayadmuna.ui.login.database.LoginRepository
import klo0812.mlaserna.bayadmuna.ui.login.services.LoginService

class RegistrationViewModelFactory(
    val username: String,
    val password: String,
    val cpassword: String,
    val service: LoginService,
    val repository: LoginRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegistrationViewModel::class.java)) {
            return RegistrationViewModel(
                username = username,
                password = password,
                cpassword = cpassword,
                service = service,
                repository = repository
            ) as T
        }
        throw IllegalArgumentException("Unable to convert to RegistrationViewModel!")
    }

}