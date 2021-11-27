package com.kvlg.emojify.ui.main

import androidx.lifecycle.ViewModel
import com.kvlg.emojify.domain.AppSettings
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * @author Konstantin Koval
 * @since 06.04.2021
 */
@HiltViewModel
class MainViewModel @Inject constructor(
    private val appSettings: AppSettings
) : ViewModel() {

    fun getCurrentTheme() = appSettings.currentTheme()

    fun swapThemes() = appSettings.swapThemes()
}