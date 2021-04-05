package com.kvlg.emojify.domain

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.preference.PreferenceManager
import com.kvlg.emojify.utils.Theme

/**
 * @author Konstantin Koval
 * @since 01.04.2021
 */
class AppSettings(
    context: Context
) {
    private var prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    fun swapThemes() {
        prefs.edit {
            putBoolean(THEME_PREFS, !prefs.getBoolean(THEME_PREFS, true))
        }
    }

    fun currentTheme(): Theme = if (prefs.getBoolean(THEME_PREFS, true)) Theme.LIGHT else Theme.DARK

    companion object {
        private const val THEME_PREFS = "theme"
    }
}