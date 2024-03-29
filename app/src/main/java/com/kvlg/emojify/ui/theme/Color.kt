package com.kvlg.emojify.ui.theme

import androidx.compose.ui.graphics.Color

/**
 * @author Konstantin Koval
 * @since 27.11.2021
 */
val Purple_200 = Color(0xFFBB86FC)
val Purple_500 = Color(0xFF6200EE)
val Purple_700 = Color(0xFF3700B3)
val Purple_a200 = Color(0xFF7c4dff)
val Purple_a400 = Color(0xFF651fff)
val Purple_a700 = Color(0xFF6200ea)
val Teal_200 = Color(0xFF03DAC5)
val Teal_700 = Color(0xFF018786)
val Black = Color(0xFF000000)
val White = Color(0xFFFFFFFF)

val Gray_100 = Color(0xFFf5f5f5)
val Gray_200 = Color(0xFFeeeeee)
val Gray_300 = Color(0xFFe0e0e0)
val Gray_400 = Color(0xFFbdbdbd)
val Gray_500 = Color(0xFF9e9e9e)
val Gray_600 = Color(0xFF757575)
val Gray_700 = Color(0xFF616161)
val Gray_800 = Color(0xFF424242)
val Gray_900 = Color(0xFF212121)

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
): EmojifyerColors {
    return EmojifyerColors(
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
}

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
