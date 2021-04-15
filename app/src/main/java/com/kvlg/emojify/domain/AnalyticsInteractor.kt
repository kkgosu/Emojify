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
        analyticsRepository.onCreateTabOpen()
    }

    fun onHistoryTabOpen() {
        analyticsRepository.onHistoryTabOpen()
    }

    fun onThemeChange() {
        analyticsRepository.onThemeChange()
    }

    fun onShareClick() {
        analyticsRepository.onShareClick()
    }

    fun onCopyClick() {
        analyticsRepository.onCopyClick()
    }

    fun onClearClick() {
        analyticsRepository.onClearClick()
    }

    fun onEmojifyClick() {
        analyticsRepository.onEmojifyClick()
    }

    fun reportEvent(value: String) {
        analyticsRepository.reportEvent(value)
    }
}