package com.kvlg.emojify.domain

import android.content.Context
import androidx.annotation.StringRes
import java.io.InputStream

/**
 * @author Konstantin Koval
 * @since 11.11.2020
 */
class ResourceManager(
    private val context: Context
) {

    fun getString(@StringRes id: Int): String = context.resources.getString(id)

    fun getAsset(name: String): InputStream = context.resources.assets.open(name)
}