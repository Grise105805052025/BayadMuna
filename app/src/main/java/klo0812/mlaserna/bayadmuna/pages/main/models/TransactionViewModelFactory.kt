package klo0812.mlaserna.bayadmuna.pages.main.models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import klo0812.mlaserna.bayadmuna.pages.main.database.MainRepository
import klo0812.mlaserna.bayadmuna.pages.main.services.MainService

open class TransactionHistoryViewModelFactory(
    val username: String,
    val transactions: ArrayList<TransactionItemViewModel>,
    val service: MainService,
    val repository: MainRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TransactionHistoryViewModel::class.java)) {
            return TransactionHistoryViewModel(
                username = username,
                transactions = transactions,
                service = service,
                repository = repository
            ) as T
        }
        throw IllegalArgumentException("Unable to convert to TransactionHistoryViewModel!")
    }

}