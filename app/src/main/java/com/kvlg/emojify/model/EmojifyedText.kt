package com.kvlg.emojify.model

import com.kvlg.emojify.data.db.EmojifyedTextEntity

/**
 * @author Konstantin Koval
 * @since 13.11.2020
 */
data class EmojifyedText(
    val text: String,
    val isFavorite: Boolean = false
)

fun EmojifyedText.toEntity() =
    EmojifyedTextEntity(
        text = text,
        isFavorite = isFavorite
    )

fun EmojifyedTextEntity.toDomainModel() =
    EmojifyedText(
        text, isFavorite
    )