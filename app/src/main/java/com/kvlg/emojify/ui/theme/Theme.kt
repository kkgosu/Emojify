package com.kvlg.emojify.ui.theme

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlin.Pair as LightDarkColor

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
    val toolbarBackground = LightDarkColor(LightColorPalette.toolbarBackground, DarkColorPalette.toolbarBackground).animate(isDarkTheme = darkTheme)
    val toolbarOnBackground =
        LightDarkColor(LightColorPalette.toolbarOnBackground, DarkColorPalette.toolbarOnBackground).animate(isDarkTheme = darkTheme)
    val text = LightDarkColor(LightColorPalette.text, DarkColorPalette.text).animate(isDarkTheme = darkTheme)
    val hintText = LightDarkColor(LightColorPalette.hintText, DarkColorPalette.hintText).animate(isDarkTheme = darkTheme)
    val background0 = LightDarkColor(LightColorPalette.background0, DarkColorPalette.background0).animate(isDarkTheme = darkTheme)
    val background1 = LightDarkColor(LightColorPalette.background1, DarkColorPalette.background1).animate(isDarkTheme = darkTheme)
    val background2 = LightDarkColor(LightColorPalette.background2, DarkColorPalette.background2).animate(isDarkTheme = darkTheme)
    val tabInactive = LightDarkColor(LightColorPalette.tabInactive, DarkColorPalette.tabInactive).animate(isDarkTheme = darkTheme)
    val tabActive = LightDarkColor(LightColorPalette.tabActive, DarkColorPalette.tabActive).animate(isDarkTheme = darkTheme)
    val pointer = LightDarkColor(LightColorPalette.pointer, DarkColorPalette.pointer).animate(isDarkTheme = darkTheme)
    val mainButtonText = LightDarkColor(LightColorPalette.mainButtonText, DarkColorPalette.mainButtonText).animate(isDarkTheme = darkTheme)
    val secondaryButtonText =
        LightDarkColor(LightColorPalette.secondaryButtonText, DarkColorPalette.secondaryButtonText).animate(isDarkTheme = darkTheme)
    val editTextBackground =
        LightDarkColor(LightColorPalette.editTextBackground, DarkColorPalette.editTextBackground).animate(isDarkTheme = darkTheme)


    val colors = EmojifyerColors(
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
        isLight = !darkTheme
    )

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

@Composable
private fun LightDarkColor<Color, Color>.animate(isDarkTheme: Boolean): Color = animateColorAsState(
    targetValue = if (isDarkTheme) second else first,
    animationSpec = tween(durationMillis = 1000)
).value