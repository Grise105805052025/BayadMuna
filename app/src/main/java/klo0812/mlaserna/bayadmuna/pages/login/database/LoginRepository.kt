package klo0812.mlaserna.bayadmuna.pages.login.database

import android.util.Log
import klo0812.mlaserna.base.database.BaseRepository
import klo0812.mlaserna.bayadmuna.database.AppDataBase
import klo0812.mlaserna.bayadmuna.database.entities.UserEntity
import javax.inject.Inject

class LoginRepository @Inject constructor(
    override val database: AppDataBase
) : BaseRepository<AppDataBase>(database) {

    companion object {
        val TAG: String? = LoginRepository::class.simpleName
    }

    suspend fun saveLogin(id: String, email: String) {
        Log.d(TAG, "Saving new account with id ($id) and email ($email)")
        database.userDao().insertAll(UserEntity(id, email))
    }

    suspend fun purgeLogout() {
        Log.d(TAG, "Deleting all active users.")
        database.userDao().deleteAll()
    }


}