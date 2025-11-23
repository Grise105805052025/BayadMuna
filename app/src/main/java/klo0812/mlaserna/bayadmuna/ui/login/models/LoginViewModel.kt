package klo0812.mlaserna.bayadmuna.ui.login.models

import androidx.lifecycle.MutableLiveData
import klo0812.mlaserna.base.ui.models.BaseViewModel
import klo0812.mlaserna.bayadmuna.database.AppDataBase
import klo0812.mlaserna.bayadmuna.ui.login.database.LoginRepository
import klo0812.mlaserna.bayadmuna.ui.login.services.LoginService

class LoginViewModel(
    username: String,
    password: String,
    service: LoginService,
    repository: LoginRepository
) : BaseViewModel<LoginService, AppDataBase>(
    service,
    repository
) {

    // Let's use MutableLiveData for direct databinding
    val username: MutableLiveData<String> = MutableLiveData(username)
    val password: MutableLiveData<String> = MutableLiveData(password)

    fun login() {
        service?.login(username.value!!, password.value!!)
    }

}