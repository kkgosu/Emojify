package com.kvlg.emojify.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController

/**
 * @author Konstantin Koval
 * @since 27.11.2021
 */

@Stable
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
) {
    var toolbarBackground by mutableStateOf(toolbarBackground, structuralEqualityPolicy())
        internal set
    var toolbarOnBackground by mutableStateOf(toolbarOnBackground, structuralEqualityPolicy())
        internal set
    var text by mutableStateOf(text, structuralEqualityPolicy())
        internal set
    var background0 by mutableStateOf(background0, structuralEqualityPolicy())
        internal set
    var background1 by mutableStateOf(background1, structuralEqualityPolicy())
        internal set
    var background2 by mutableStateOf(background2, structuralEqualityPolicy())
        internal set
    var tabInactive by mutableStateOf(tabInactive, structuralEqualityPolicy())
        internal set
    var tabActive by mutableStateOf(tabActive, structuralEqualityPolicy())
        internal set
    var pointer by mutableStateOf(pointer, structuralEqualityPolicy())
        internal set
    var mainButton by mutableStateOf(mainButton, structuralEqualityPolicy())
        internal set
    var secondaryButton by mutableStateOf(secondaryButton, structuralEqualityPolicy())
        internal set
    var isLight by mutableStateOf(isLight, structuralEqualityPolicy())
        internal set

    /**
     * Returns a copy of this Colors, optionally overriding some of the values.
     */
    fun copy(
        primary: Color = this.toolbarBackground,
        primaryVariant: Color = this.toolbarOnBackground,
        secondary: Color = this.text,
        secondaryVariant: Color = this.background0,
        background: Color = this.background1,
        surface: Color = this.background2,
        error: Color = this.tabInactive,
        onPrimary: Color = this.tabActive,
        onSecondary: Color = this.pointer,
        onBackground: Color = this.mainButton,
        onSurface: Color = this.secondaryButton,
        isLight: Boolean = this.isLight
    ): EmojifyerColors = EmojifyerColors(
        primary,
        primaryVariant,
        secondary,
        secondaryVariant,
        background,
        surface,
        error,
        onPrimary,
        onSecondary,
        onBackground,
        onSurface,
        isLight
    )

    override fun toString(): String {
        return "Colors(" +
                "primary=$toolbarBackground, " +
                "primaryVariant=$toolbarOnBackground, " +
                "secondary=$text, " +
                "secondaryVariant=$background0, " +
                "background=$background1, " +
                "surface=$background2, " +
                "error=$tabInactive, " +
                "onPrimary=$tabActive, " +
                "onSecondary=$pointer, " +
                "onBackground=$mainButton, " +
                "onSurface=$secondaryButton, " +
                "isLight=$isLight)"
    }
}

fun darkColors(
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

fun lightColors(
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

private val LightColorPalette = lightColors(
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

private val DarkColorPalette = darkColors(
    toolbarBackground = Gray_900,
    toolbarOnBackground = Purple_500,
    text = White,
    background0 = Black,
    background1 = Gray_900,
    background2 = Gray_800,
    tabActive = Purple_500,
    tabInactive = Gray_600,
    pointer = Purple_500,
    mainButton = Purple_500,
    secondaryButton = White
)

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

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes
    ) {
        content()
    }
}
