package com.kvlg.emojify

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.yandex.metrica.YandexMetrica
import com.yandex.metrica.YandexMetricaConfig
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

        val config = YandexMetricaConfig.newConfigBuilder(BuildConfig.APP_METRICA_API_KEY)
            .withLogs()
            .withStatisticsSending(true)
            .build()
        YandexMetrica.activate(this, config)
        YandexMetrica.enableActivityAutoTracking(this)
    }
}