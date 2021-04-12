package com.kvlg.emojify.data.db.history

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @author Konstantin Koval
 * @since 13.11.2020
 */
@Entity(tableName = "history_text")
data class EmojifyedTextEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val text: String,
    val isFavorite: Boolean
)