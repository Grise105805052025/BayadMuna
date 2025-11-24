package klo0812.mlaserna.bayadmuna.pages.main.models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import klo0812.mlaserna.bayadmuna.pages.main.database.MainRepository
import klo0812.mlaserna.bayadmuna.pages.main.services.MainService

class WalletViewModelFactory(
    val username: String,
    val balance: Double,
    val showBalance: Boolean,
    val service: MainService,
    val repository: MainRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WalletViewModel::class.java)) {
            return WalletViewModel(
                username = username,
                balance = balance,
                showBalance = showBalance,
                service = service,
                repository = repository
            ) as T
        }
        throw IllegalArgumentException("Unable to convert to BalanceViewModel!")
    }

}