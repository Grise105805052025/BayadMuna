package klo0812.mlaserna.bayadmuna.ui.login.models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import klo0812.mlaserna.bayadmuna.ui.login.database.LoginRepository
import klo0812.mlaserna.bayadmuna.ui.login.services.LoginService

class LoginViewModelFactory(
    val username: String,
    val password: String,
    val service: LoginService,
    val repository: LoginRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(
                username = username,
                password = password,
                service = service,
                repository = repository
            ) as T
        }
        throw IllegalArgumentException("Unable to convert to LoginViewModel!")
    }

}