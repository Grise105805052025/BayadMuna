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


}