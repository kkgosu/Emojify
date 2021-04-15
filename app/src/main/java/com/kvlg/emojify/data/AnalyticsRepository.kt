package com.kvlg.emojify.data

/**
 * @author Konstantin Koval
 * @since 15.04.2021
 */
interface AnalyticsRepository {
    fun onCreateTabOpen()

    fun onHistoryTabOpen()

    fun onThemeChange()

    fun onShareClick()

    fun onCopyClick()

    fun onClearClick()

    fun onEmojifyClick()

    fun reportEvent(value: String)
}