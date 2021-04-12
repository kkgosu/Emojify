package com.kvlg.emojify.model

import com.kvlg.emojify.data.db.current.CurrentStateEntity

/**
 * @author Konstantin Koval
 * @since 12.04.2021
 */
data class State(
    val page: Int = 0,
    val text: String = ""
)

fun State.toEntity() = CurrentStateEntity(
    id = 0,
    pageNumber = page,
    currentText = text
)

fun CurrentStateEntity.toDomainModel() = State(
    page = pageNumber,
    text = currentText
)
