package klo0812.mlaserna.base.database

import androidx.room.RoomDatabase

/**
 * All repositories should have at least one local Room database.
 *
 * @author Roy M
 * @version 1.0
 * @since 2025-11-23
 */
open class BaseRepository<T : RoomDatabase>(
    database: T,
)