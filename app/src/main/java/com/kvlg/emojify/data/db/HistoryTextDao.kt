package com.kvlg.emojify.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

/**
 * @author Konstantin Koval
 * @since 13.11.2020
 */
@Dao
interface HistoryTextDao {

    @Query("SELECT * FROM history_text ORDER BY id DESC")
    fun getAllTexts(): Flow<List<EmojifyedTextEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertText(textEntity: EmojifyedTextEntity)

    @Query("DELETE FROM history_text WHERE id NOT IN (SELECT id FROM history_text ORDER BY id DESC LIMIT 10)")
    fun checkLimitAndDelete()
}