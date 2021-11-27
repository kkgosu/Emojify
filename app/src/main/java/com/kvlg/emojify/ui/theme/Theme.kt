package com.kvlg.emojify.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

/**
 * @author Konstantin Koval
 * @since 27.11.2021
 */
private val DarkColorPalette = darkColors(
    primary = Gray_600,
    onPrimary = White,
    background = Gray_900,
    surface = Gray_800,
    onSurface = White,
    onBackground = Purple_a200,
)

private val LightColorPalette = lightColors(
    primary = Purple_500,
    onPrimary = White,
    surface = Gray_100,
    onSurface = Gray_500,
    background = White,
    onBackground = Black
)

@Composable
fun EmojifyerTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable() () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}