package klo0812.mlaserna.bayadmuna.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import klo0812.mlaserna.bayadmuna.database.entities.TransactionEntity

@Dao
interface TransactionDao {

    @Query("SELECT * FROM TransactionEntity WHERE userId = :userId")
    fun get(userId: String) : TransactionEntity

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(vararg users: TransactionEntity)

    @Query("DELETE FROM TransactionEntity")
    fun deleteAll()

}