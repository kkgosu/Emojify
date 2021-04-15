package com.kvlg.emojify.data

import com.yandex.metrica.YandexMetrica

class AnalyticsRepositoryImpl : AnalyticsRepository {
    override fun onCreateTabOpen() {
        YandexMetrica.reportEvent(CREATE_TAB_OPEN)
    }

    override fun onHistoryTabOpen() {
        YandexMetrica.reportEvent(HISTORY_TAB_OPEN)
    }

    override fun onThemeChange() {
        YandexMetrica.reportEvent(THEME_CHANGE)
    }

    override fun onShareClick() {
        YandexMetrica.reportEvent(SHARE_CLICK)
    }

    override fun onCopyClick() {
        YandexMetrica.reportEvent(COPY_CLICKED)
    }

    override fun onClearClick() {
        YandexMetrica.reportEvent(CLEAR_CLICKED)
    }

    override fun onEmojifyClick() {
        YandexMetrica.reportEvent(EMOJIFY_CLICKED)
    }

    override fun reportEvent(value: String) {
        YandexMetrica.reportEvent(value)
    }

    companion object {
        private const val CREATE_TAB_OPEN = "Create tab opened"
        private const val HISTORY_TAB_OPEN = "History tab opened"
        private const val THEME_CHANGE = "Theme changed"
        private const val SHARE_CLICK = "Share clicked"
        private const val COPY_CLICKED = "Copy clicked"
        private const val CLEAR_CLICKED = "Clear clicked"
        private const val EMOJIFY_CLICKED = "Emojify clicked"
    }
}