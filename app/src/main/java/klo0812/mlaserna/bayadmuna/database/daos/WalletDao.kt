package klo0812.mlaserna.bayadmuna.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import klo0812.mlaserna.bayadmuna.database.entities.WalletEntity

@Dao
interface WalletDao {

    @Query("SELECT * FROM WalletEntity WHERE userId = :userId")
    fun get(userId: String) : WalletEntity?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(vararg users: WalletEntity)

    @Query("DELETE FROM WalletEntity")
    fun deleteAll()

}