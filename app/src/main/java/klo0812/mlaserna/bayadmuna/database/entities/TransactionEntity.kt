package klo0812.mlaserna.bayadmuna.database.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TransactionEntity(
    @PrimaryKey
    val id: String,
    @Embedded
    val userEntity: UserEntity,
    val target: String,
    val amount: Double,
    val date: Long
)