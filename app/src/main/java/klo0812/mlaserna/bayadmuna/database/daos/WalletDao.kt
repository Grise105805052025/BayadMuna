package klo0812.mlaserna.bayadmuna.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import klo0812.mlaserna.bayadmuna.database.entities.WalletEntity

@Dao
interface WalletDao {

    @Query("SELECT * FROM WalletEntity WHERE userId = :userId")
    fun get(userId: String) : WalletEntity?

    @Update
    fun update(vararg wallet: WalletEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(vararg wallets: WalletEntity)

    @Query("DELETE FROM WalletEntity")
    fun deleteAll()

}