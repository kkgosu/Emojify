package com.kvlg.emojify.domain

import com.kvlg.emojify.data.AnalyticsRepository

/**
 * @author Konstantin Koval
 * @since 15.04.2021
 */
class AnalyticsInteractor(
    private val analyticsRepository: AnalyticsRepository
) {

    fun onCreateTabOpen() {

    }

    fun onHistoryTabOpen() {

    }

    fun onThemeChange() {

    }

    fun onShareClick() {

    }

    fun onCopyClick() {

    }

    fun onClearClick() {

    }

    fun onEmojifyClick() {

    }

    fun reportEvent(value: String) {
        analyticsRepository.reportEvent(value)
    }
}