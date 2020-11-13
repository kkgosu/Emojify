package com.kvlg.emojify.model

/**
 * @author Konstantin Koval
 * @since 08.11.2020
 */

data class Emojis(
    val keys: List<String>
)

data class EmojiItem(
    val keywords: List<String>,
    val char: String,
    val category: String
)
