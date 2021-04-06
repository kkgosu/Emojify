package com.kvlg.emojify

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.kvlg.emojify.domain.AppSettings

/**
 * @author Konstantin Koval
 * @since 06.04.2021
 */
class MainViewModel @ViewModelInject constructor(
    private val appSettings: AppSettings
) : ViewModel() {

    fun getCurrentTheme() = appSettings.currentTheme()

    fun swapThemes() = appSettings.swapThemes()
}