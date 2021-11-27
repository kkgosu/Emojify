package com.kvlg.emojify.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.kvlg.emojify.R

/**
 * @author Konstantin Koval
 * @since 27.11.2021
 */

val Futura = FontFamily(
    Font(R.font.futurabook),
    Font(R.font.futurabold, FontWeight.ExtraBold),
    Font(R.font.futuramedium, FontWeight.Medium),
    Font(R.font.futuraheavy, FontWeight.Bold),
)

val Typography = Typography(
    defaultFontFamily = Futura
)