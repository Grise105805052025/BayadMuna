package klo0812.mlaserna.bayadmuna.pages.main.database

import klo0812.mlaserna.base.database.BaseRepository
import klo0812.mlaserna.bayadmuna.database.AppDataBase
import klo0812.mlaserna.bayadmuna.database.entities.TransactionEntity
import klo0812.mlaserna.bayadmuna.database.entities.UserEntity
import javax.inject.Inject

class MainRepository @Inject constructor(
    override val database: AppDataBase
) : BaseRepository<AppDataBase>(database) {

    companion object {
        val TAG: String? = MainRepository::class.simpleName
    }

    fun getUser(userID: String): UserEntity {
        return database.userDao().get(userID)
    }

    fun getBalance(userID: String?): Double? {
        if (userID == null) return null
        return database.walletDao().get(userID)?.balance
    }

    fun deductBalance(userID: String?, deduction: Double): Double? {
        if (userID == null) return null
        val wallet = database.walletDao().get(userID)
        if (wallet != null) {
            val balance = wallet.balance
            wallet.balance = balance.minus(deduction)
            database.walletDao().update(wallet)
            return balance
        } else {
            return null
        }
    }

    fun addNewTransaction(transaction: TransactionEntity) {
        database.transactionDao().insert(transaction)
    }

    fun getTransactions(userID: String?): List<TransactionEntity> {
        if (userID == null) return arrayListOf()
        return database.transactionDao().getAll(userID)
    }

}