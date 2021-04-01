package com.kvlg.emojify.domain

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.preference.PreferenceManager

/**
 * @author Konstantin Koval
 * @since 01.04.2021
 */
class AppSettings(
    context: Context
) {
    private var prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    fun setTheme(isLight: Boolean) {
        prefs.edit {
            putBoolean(THEME_PREFS, isLight)
        }
    }

    fun isLightTheme(): Boolean = prefs.getBoolean(THEME_PREFS, true)

    companion object {
        private const val THEME_PREFS = "theme"
    }
}