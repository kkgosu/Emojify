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

    fun switchThemes() {
        prefs.edit {
            putBoolean(THEME_PREFS, !prefs.getBoolean(THEME_PREFS, true))
        }
    }

    fun isLightTheme(): Boolean = prefs.getBoolean(THEME_PREFS, true)

    fun isFirstLaunch(): Boolean = prefs.getBoolean(IS_FIRST_LAUNCH, true)
    fun setNotFirstLaunch() = prefs.edit {
        putBoolean(IS_FIRST_LAUNCH, false)
    }

    fun isMetricsEnabled() = prefs.getBoolean(IS_METRICS_ENABLED, false)
    fun setMetricsEnabled(isEnabled: Boolean) = prefs.edit {
        putBoolean(IS_METRICS_ENABLED, isEnabled)
    }

    companion object {
        private const val THEME_PREFS = "theme"
        private const val IS_FIRST_LAUNCH = "isFirstLaunch"
        private const val IS_METRICS_ENABLED = "isMetricsEnabled"
    }
}