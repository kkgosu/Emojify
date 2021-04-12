package com.kvlg.emojify

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import dagger.hilt.android.HiltAndroidApp

/**
 * @author Konstantin Koval
 * @since 11.11.2020
 */
@HiltAndroidApp
class EmojifyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }

    override fun onTerminate() {
        super.onTerminate()
        currentPage = 0
        currentText = null
    }
}