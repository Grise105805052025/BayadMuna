package klo0812.mlaserna.bayadmuna.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import klo0812.mlaserna.bayadmuna.app.APP_DATA_BASE_NAME
import klo0812.mlaserna.bayadmuna.database.daos.UserDao
import klo0812.mlaserna.bayadmuna.database.entities.UserEntity

@Database(
    entities = [
        UserEntity::class
    ],
    version = 1
)
abstract class AppDataBase : RoomDatabase() {

    abstract fun userDao(): UserDao

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