package com.kvlg.emojify.data.db.current

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @author Konstantin Koval
 * @since 12.04.2021
 */
@Entity(tableName = "current_state")
data class CurrentStateEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int = 0,
    val pageNumber: Int,
    val currentText: String
)
