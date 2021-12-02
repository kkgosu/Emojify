package com.kvlg.emojify.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController

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
fun EmojifyerTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(
        color = Color.Transparent,
        darkIcons = !isSystemInDarkTheme()
    )
    systemUiController.setNavigationBarColor(
        color = Color.Transparent,
        darkIcons = !isSystemInDarkTheme()
    )

    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes
    ) {
        CompositionLocalProvider(
            LocalRippleTheme provides JetNewsRippleTheme,
            content = content
        )
    }
}

private object JetNewsRippleTheme : RippleTheme {
    // Here you should return the ripple color you want
    // and not use the defaultRippleColor extension on RippleTheme.
    // Using that will override the ripple color set in DarkMode
    // or when you set light parameter to false
    @Composable
    override fun defaultColor(): Color = MaterialTheme.colors.primary

    @Composable
    override fun rippleAlpha(): RippleAlpha = RippleTheme.defaultRippleAlpha(
        MaterialTheme.colors.primary,
        lightTheme = isSystemInDarkTheme()
    )
}