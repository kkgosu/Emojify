package com.kvlg.emojify.data.db.current

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

/**
 * @author Konstantin Koval
 * @since 12.04.2021
 */
@Dao
interface CurrentStateDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveCurrentState(stateEntity: CurrentStateEntity)

    @Query("SELECT * FROM current_state WHERE id = 0")
    fun getCurrentState(): Flow<CurrentStateEntity>

    @Query("UPDATE current_state SET currentText = \"\", pageNumber = 0 WHERE id = 0")
    fun resetState()
}