package klo0812.mlaserna.bayadmuna.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import klo0812.mlaserna.bayadmuna.app.APP_DATA_BASE_NAME
import klo0812.mlaserna.bayadmuna.database.daos.TransactionDao
import klo0812.mlaserna.bayadmuna.database.daos.UserDao
import klo0812.mlaserna.bayadmuna.database.daos.WalletDao
import klo0812.mlaserna.bayadmuna.database.entities.TransactionEntity
import klo0812.mlaserna.bayadmuna.database.entities.UserEntity
import klo0812.mlaserna.bayadmuna.database.entities.WalletEntity

@Database(
    entities = [
        UserEntity::class,
        TransactionEntity::class,
        WalletEntity::class
    ],
    version = 1
)
abstract class AppDataBase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun transactionDao(): TransactionDao
    abstract fun walletDao(): WalletDao

    companion object {

        @Volatile private var instance: AppDataBase? = null

        fun getInstance(context: Context): AppDataBase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDataBase {
            return Room.databaseBuilder(
                context,
                AppDataBase::class.java,
                APP_DATA_BASE_NAME
            ).build()
        }

    }

}