package klo0812.mlaserna.bayadmuna.database.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class WalletEntity(
    @PrimaryKey
    val id: String,
    @Embedded
    val userEntity: UserEntity,
    val balance: Double,
)