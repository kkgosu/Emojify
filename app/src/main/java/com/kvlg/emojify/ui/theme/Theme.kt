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
    val toolbarBackground: Color,
    val toolbarOnBackground: Color,
    val text: Color,
    val hintText: Color,
    val background0: Color,
    val background1: Color,
    val background2: Color,
    val tabInactive: Color,
    val tabActive: Color,
    val pointer: Color,
    val mainButtonText: Color,
    val secondaryButtonText: Color,
    val editTextBackground: Color,
    val isLight: Boolean
)

fun emojifyerDarkColors(
    toolbarBackground: Color,
    toolbarOnBackground: Color,
    text: Color,
    hintText: Color,
    background0: Color,
    background1: Color,
    background2: Color,
    tabInactive: Color,
    tabActive: Color,
    pointer: Color,
    mainButtonText: Color,
    secondaryButtonText: Color,
    editTextBackground: Color
): EmojifyerColors = EmojifyerColors(
    toolbarBackground = toolbarBackground,
    toolbarOnBackground = toolbarOnBackground,
    text = text,
    hintText = hintText,
    background0 = background0,
    background1 = background1,
    background2 = background2,
    tabInactive = tabInactive,
    tabActive = tabActive,
    pointer = pointer,
    mainButtonText = mainButtonText,
    secondaryButtonText = secondaryButtonText,
    editTextBackground = editTextBackground,
    isLight = false
)

fun emojifyerLightColors(
    toolbarBackground: Color,
    toolbarOnBackground: Color,
    text: Color,
    hintText: Color,
    background0: Color,
    background1: Color,
    background2: Color,
    tabInactive: Color,
    tabActive: Color,
    pointer: Color,
    mainButtonText: Color,
    secondaryButtonText: Color,
    editTextBackground: Color,
): EmojifyerColors = EmojifyerColors(
    toolbarBackground = toolbarBackground,
    toolbarOnBackground = toolbarOnBackground,
    text = text,
    hintText = hintText,
    background0 = background0,
    background1 = background1,
    background2 = background2,
    tabInactive = tabInactive,
    tabActive = tabActive,
    pointer = pointer,
    mainButtonText = mainButtonText,
    secondaryButtonText = secondaryButtonText,
    editTextBackground = editTextBackground,
    isLight = true
)

private val LightColorPalette = emojifyerLightColors(
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

private val DarkColorPalette = emojifyerDarkColors(
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