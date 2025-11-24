package klo0812.mlaserna.bayadmuna.pages.main.models

import klo0812.mlaserna.base.ui.models.BaseFragmentViewModel
import klo0812.mlaserna.bayadmuna.database.AppDataBase
import klo0812.mlaserna.bayadmuna.pages.main.database.MainRepository
import klo0812.mlaserna.bayadmuna.pages.main.services.MainService

open class TransactionHistoryViewModel(
    val username: String,
    val transactions: ArrayList<TransactionItemViewModel> = arrayListOf(),
    service: MainService,
    repository: MainRepository
) : BaseFragmentViewModel<MainService, AppDataBase>(
    service,
    repository
) {

    suspend fun fetchTransactions(userId: String): JSONPlaceHolderResponseModel {
        val result = (service as MainService).fetchTransactions()
        if (result.code == 200 || result.code == 201) {
            transactions.clear()
            val transactionsFromDb = (repository as MainRepository).getTransactions(userId)
            for (transaction in transactionsFromDb) {
                transactions.add(
                    TransactionItemViewModel(
                        target = transaction.target,
                        amount = transaction.amount,
                        date = transaction.date
                    )
                )
            }
        }
        return result
    }

}