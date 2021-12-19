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

val LightColorPalette = emojifyerLightColors(
    toolbarBackground = White,
    toolbarOnBackground = Purple_500,
    text = Black,
    hintText = Gray_500,
    background0 = White,
    background1 = White,
    background2 = Gray_100,
    tabActive = Purple_500,
    tabInactive = Gray_600,
    pointer = Purple_500,
    mainButtonText = White,
    secondaryButtonText = Purple_500,
    editTextBackground = Gray_100
)

val DarkColorPalette = emojifyerDarkColors(
    toolbarBackground = Gray_900,
    toolbarOnBackground = Purple_500,
    text = White,
    hintText = Gray_500,
    background0 = Black,
    background1 = Gray_900,
    background2 = Gray_800,
    tabActive = White,
    tabInactive = Gray_500,
    pointer = White,
    mainButtonText = White,
    secondaryButtonText = White,
    editTextBackground = Gray_800
)

val LocalEmojifyerColors = compositionLocalOf<EmojifyerColors> {
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