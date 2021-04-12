package com.kvlg.emojify.data.db.current

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * @author Konstantin Koval
 * @since 12.04.2021
 */
@Database(
    entities = [CurrentStateEntity::class],
    version = 1,
    exportSchema = false
)
abstract class CurrentStateDatabase : RoomDatabase() {
    abstract fun getCurrentStateDao(): CurrentStateDao
}