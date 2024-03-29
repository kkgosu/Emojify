package com.kvlg.emojify.data.db.history

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * @author Konstantin Koval
 * @since 13.11.2020
 */
@Database(
    entities = [EmojifyedTextEntity::class],
    version = 1,
    exportSchema = false
)
abstract class HistoryTextDatabase : RoomDatabase() {
    abstract fun getHistoryTextDao(): HistoryTextDao
}