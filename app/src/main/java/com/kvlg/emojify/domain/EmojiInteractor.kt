package com.kvlg.emojify.domain

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.kvlg.emojify.data.db.EmojifyedTextEntity
import com.kvlg.emojify.data.db.HistoryTextDao
import com.kvlg.emojify.model.EmojifyedText
import com.kvlg.emojify.model.toDomainModel
import com.kvlg.emojify.model.toEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

/**
 * @author Konstantin Koval
 * @since 13.11.2020
 */
class EmojiInteractor(
    private val historyTextDao: HistoryTextDao,
    private val dispatcher: CoroutineDispatcher
) {

    suspend fun saveText(text: String) {
        executeCoroutine {
            historyTextDao.insertText(
                EmojifyedText(text).toEntity()
            )
        }
    }

    fun getAllTexts(): LiveData<Result<List<EmojifyedText>>> {
        return executeFlow {
            historyTextDao.getAllTexts().map { list ->
                val mappedList = list.map(EmojifyedTextEntity::toDomainModel)
                Result.Success(mappedList)
            }
        }
    }

    private suspend fun <T> executeCoroutine(block: () -> T): Result<T> {
        return try {
            withContext(dispatcher) {
                block().let {
                    Result.Success(it)
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error on executing coroutine", e)
            Result.Error(e)
        }
    }

    private fun <T> executeFlow(block: () -> Flow<Result<T>>): LiveData<Result<T>> {
        return block()
            .catch { e -> emit(Result.Error(Exception(e))) }
            .flowOn(dispatcher)
            .asLiveData(dispatcher)
    }

    companion object {
        private val TAG = "EmojiInteractor"
    }
}