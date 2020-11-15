package com.kvlg.fluidlayout

/**
 * @author Konstantin Koval
 * @since 15.11.2020
 */
data class KeyboardVisibilityChanged(
    val visible: Boolean,
    val contentHeight: Int,
    val contentHeightBeforeResize: Int
)