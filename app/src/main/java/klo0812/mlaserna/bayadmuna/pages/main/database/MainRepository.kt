package klo0812.mlaserna.bayadmuna.pages.main.database

import klo0812.mlaserna.base.database.BaseRepository
import klo0812.mlaserna.bayadmuna.database.AppDataBase
import javax.inject.Inject

class MainRepository @Inject constructor(
    override val database: AppDataBase
) : BaseRepository<AppDataBase>(database) {

    companion object {
        val TAG: String? = MainRepository::class.simpleName
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


}