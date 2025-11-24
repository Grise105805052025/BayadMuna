package klo0812.mlaserna.bayadmuna.pages.main.models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import klo0812.mlaserna.bayadmuna.pages.main.database.MainRepository
import klo0812.mlaserna.bayadmuna.pages.main.services.MainService

class SendMoneyViewModelFactory(
    val target: String,
    val amount: String,
    val username: String,
    val balance: Double,
    val service: MainService,
    val repository: MainRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SendMoneyViewModel::class.java)) {
            return SendMoneyViewModel(
                target = target,
                amount = amount,
                username = username,
                balance = balance,
                service = service,
                repository = repository
            ) as T
        }
        throw IllegalArgumentException("Unable to convert to SendMoneyViewModel!")
    }

}