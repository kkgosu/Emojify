package com.kvlg.emojify.domain

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.kvlg.emojify.data.db.current.CurrentStateDao
import com.kvlg.emojify.model.State
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
 * @since 12.04.2021
 */
class CurrentStateInteractor(
    private val currentStateDao: CurrentStateDao,
    private val dispatcher: CoroutineDispatcher
) {
    suspend fun saveCurrentState(page: Int, text: String) = executeCoroutine {
        currentStateDao.saveCurrentState(
            State(page, text).toEntity()
        )
    }

    fun getCurrentState(): LiveData<Result<State>> = executeFlow {
        currentStateDao.getCurrentState().map {
            Result.Success(it.toDomainModel())
        }
    }

    fun resetState() = currentStateDao.resetState()

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
        private val TAG = "CurrentStateInteractor"
    }
}