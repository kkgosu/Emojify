package com.kvlg.emojify.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController

/**
 * @author Konstantin Koval
 * @since 27.11.2021
 */

class EmojifyerColors(
    toolbarBackground: Color,
    toolbarOnBackground: Color,
    text: Color,
    background0: Color,
    background1: Color,
    background2: Color,
    tabInactive: Color,
    tabActive: Color,
    pointer: Color,
    mainButton: Color,
    secondaryButton: Color,
    isLight: Boolean
)

fun emojifyerDarkColors(
    toolbarBackground: Color,
    toolbarOnBackground: Color,
    text: Color,
    background0: Color,
    background1: Color,
    background2: Color,
    tabInactive: Color,
    tabActive: Color,
    pointer: Color,
    mainButton: Color,
    secondaryButton: Color,
): EmojifyerColors = EmojifyerColors(
    toolbarBackground,
    toolbarOnBackground,
    text,
    background0,
    background1,
    background2,
    tabInactive,
    tabActive,
    pointer,
    mainButton,
    secondaryButton,
    isLight = false
)

fun emojifyerLightColors(
    toolbarBackground: Color,
    toolbarOnBackground: Color,
    text: Color,
    background0: Color,
    background1: Color,
    background2: Color,
    tabInactive: Color,
    tabActive: Color,
    pointer: Color,
    mainButton: Color,
    secondaryButton: Color,
): EmojifyerColors = EmojifyerColors(
    toolbarBackground,
    toolbarOnBackground,
    text,
    background0,
    background1,
    background2,
    tabInactive,
    tabActive,
    pointer,
    mainButton,
    secondaryButton,
    isLight = true
)

private val LightColorPalette = emojifyerLightColors(
    toolbarBackground = White,
    toolbarOnBackground = Purple_500,
    text = Black,
    background0 = White,
    background1 = White,
    background2 = Gray_100,
    tabActive = Purple_500,
    tabInactive = Gray_600,
    pointer = Purple_500,
    mainButton = Purple_500,
    secondaryButton = White
)

private val DarkColorPalette = emojifyerDarkColors(
    toolbarBackground = Gray_900,
    toolbarOnBackground = Purple_500,
    text = White,
    background0 = Black,
    background1 = Gray_900,
    background2 = Gray_800,
    tabActive = White,
    tabInactive = Gray_500,
    pointer = White,
    mainButton = Purple_500,
    secondaryButton = White
)

private val LocalEmojifyerColors = compositionLocalOf<EmojifyerColors> {
    LightColorPalette
}

@Composable
fun EmojifyerTheme(darkTheme: Boolean, content: @Composable () -> Unit) {
    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(
        color = Color.Transparent,
        darkIcons = !darkTheme
    )
    systemUiController.setNavigationBarColor(
        color = Color.Transparent,
        darkIcons = !darkTheme
    )

    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }
    CompositionLocalProvider(LocalEmojifyerColors provides colors) {
        MaterialTheme(
            typography = Typography,
            shapes = Shapes,
            content = content
        )
    }
}

object EmojifyerTheme {
    val colors: EmojifyerColors
        @Composable get() = LocalEmojifyerColors.current
}