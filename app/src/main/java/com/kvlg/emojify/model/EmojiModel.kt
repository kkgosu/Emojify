package com.kvlg.emojify.model

import com.google.gson.annotations.SerializedName

/**
 * @author Konstantin Koval
 * @since 08.11.2020
 */

data class Emojis(
    @SerializedName("keys") val keys: List<String>
)

data class EmojiItem(
    @SerializedName("keywords") val keywords: List<String>,
    @SerializedName("char") val char: String,
    @SerializedName("category") val category: String
)

data class EmojiItem2(
    @SerializedName("emoji") val emoji: String,
    @SerializedName("description") val description: String,
    @SerializedName("aliases") val aliases: List<String>,
    @SerializedName("tags") val tags: List<String>
)
