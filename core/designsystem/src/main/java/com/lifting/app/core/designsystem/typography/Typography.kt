package com.lifting.app.core.designsystem.typography

/**
 * Created by bedirhansaricayir on 14.07.2024
 */

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.lifting.app.core.designsystem.R

@Immutable
class LiftingTypography {
    private val fontFamily: FontFamily = FontFamily(
        Font(R.font.roboto_bold, weight = FontWeight.Bold),
        Font(R.font.roboto_black, weight = FontWeight.Black),
        Font(R.font.roboto_black_italic, weight = FontWeight.Black, style = FontStyle.Italic),
        Font(R.font.roboto_bold_italic, weight = FontWeight.Bold, style = FontStyle.Italic),
        Font(R.font.roboto_italic, style = FontStyle.Italic),
        Font(R.font.roboto_light_italic, weight = FontWeight.Light, style = FontStyle.Italic),
        Font(R.font.roboto_medium, weight = FontWeight.Medium),
        Font(R.font.roboto_medium_italic, weight = FontWeight.Medium, style = FontStyle.Italic),
        Font(R.font.roboto_regular, weight = FontWeight.Normal),
        Font(R.font.roboto_thin, weight = FontWeight.Thin),
        Font(R.font.roboto_thin_italic, weight = FontWeight.Thin, style = FontStyle.Italic),
    )

    val header1: TextStyle
        @Composable
        get() = TextStyle(
            fontFamily = fontFamily,
            fontSize = 24.sp,
            fontWeight = FontWeight.Normal,
            letterSpacing = 0.sp,
        )
    val header2: TextStyle
        @Composable
        get() = TextStyle(
            fontFamily = fontFamily,
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium,
            letterSpacing = 0.15.sp,
        )
    val subtitle1: TextStyle
        @Composable
        get() = TextStyle(
            fontFamily = fontFamily,
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            letterSpacing = 0.15.sp,
        )
    val subtitle2: TextStyle
        @Composable
        get() = TextStyle(
            fontFamily = fontFamily,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            letterSpacing = 0.1.sp,
        )
    val button: TextStyle
        @Composable
        get() = TextStyle(
            fontFamily = fontFamily,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            letterSpacing = 0.25.sp
        )
    val caption: TextStyle
        @Composable
        get() = TextStyle(
            fontFamily = fontFamily,
            fontSize = 12.sp,
            fontWeight = FontWeight.Normal,
            letterSpacing = 0.4.sp,
        )
}

internal val LocalTypography = staticCompositionLocalOf { LiftingTypography() }