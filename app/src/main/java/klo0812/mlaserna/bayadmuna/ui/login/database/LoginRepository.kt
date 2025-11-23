package klo0812.mlaserna.bayadmuna.ui.login.database

import klo0812.mlaserna.base.database.BaseRepository
import klo0812.mlaserna.bayadmuna.database.AppDataBase
import javax.inject.Inject

class LoginRepository @Inject constructor(
    val database: AppDataBase
) : BaseRepository<AppDataBase>(database) {

}