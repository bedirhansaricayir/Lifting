package com.lifting.app.core.designsystem.colors

/**
 * Created by bedirhansaricayir on 14.07.2024
 */

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.runtime.structuralEqualityPolicy
import androidx.compose.ui.graphics.Color

@Stable
class LiftingColors(
    primary: Color,
    onPrimary: Color,
    secondary: Color,
    onSecondary: Color,
    background: Color,
    onBackground: Color,
    error: Color,
    onError: Color,
    isLight: Boolean
) {
    var primary by mutableStateOf(primary, structuralEqualityPolicy())
        internal set
    var onPrimary by mutableStateOf(onPrimary, structuralEqualityPolicy())
        internal set
    var secondary by mutableStateOf(secondary, structuralEqualityPolicy())
        internal set
    var onSecondary by mutableStateOf(onSecondary, structuralEqualityPolicy())
        internal set
    var background by mutableStateOf(background, structuralEqualityPolicy())
        internal set
    var onBackground by mutableStateOf(onBackground, structuralEqualityPolicy())
        internal set
    var error by mutableStateOf(error, structuralEqualityPolicy())
        internal set
    var onError by mutableStateOf(onError, structuralEqualityPolicy())
        internal set
    var isLight by mutableStateOf(isLight, structuralEqualityPolicy())
        internal set

    fun copy(
        primary: Color = this.primary,
        onPrimary: Color = this.onPrimary,
        secondary: Color = this.secondary,
        onSecondary: Color = this.onSecondary,
        background: Color = this.background,
        onBackground: Color = this.onBackground,
        error: Color = this.error,
        onError: Color = this.onError,
        isLight: Boolean = this.isLight
    ): LiftingColors = LiftingColors(
        primary = primary,
        onPrimary = onPrimary,
        secondary = secondary,
        onSecondary = onSecondary,
        background = background,
        onBackground = onBackground,
        error = error,
        onError = onError,
        isLight = isLight
    )
}

fun lightLiftingColors(
    primary: Color = Color.Blue,
    onPrimary: Color = Color.White,
    secondary: Color = Color(0xFF03DA6C),
    onSecondary: Color = Color(0xFFB3C33E),
    background: Color = Color(0xFFF5F5F5),
    onBackground: Color = Color(0xFF212121),
    error: Color = Color.Red,
    onError: Color = Color.White,
): LiftingColors = LiftingColors(
    primary = primary,
    onPrimary = onPrimary,
    secondary = secondary,
    onSecondary = onSecondary,
    background = background,
    onBackground = onBackground,
    error = error,
    onError = onError,
    isLight = true
)

fun darkLiftingColors(
    primary: Color = Color(0xFF4a5df9),
    onPrimary: Color = Color(0xFFffffff),
    secondary: Color = Color(0xFF03DA6C),
    onSecondary: Color = Color(0xFFB3C33E),
    background: Color = Color(0xFF0c1014),
    onBackground: Color = Color(0xFFf8f9f9),
    error: Color = Color.Red,
    onError: Color = Color.White,
): LiftingColors = LiftingColors(
    primary = primary,
    onPrimary = onPrimary,
    secondary = secondary,
    onSecondary = onSecondary,
    background = background,
    onBackground = onBackground,
    error = error,
    onError = onError,
    isLight = false
)

internal fun LiftingColors.updateColorsFrom(other: LiftingColors) {
    primary = other.primary
    onPrimary = other.onPrimary
    secondary = other.secondary
    onSecondary = other.onSecondary
    background = other.background
    onBackground = other.onBackground
    error = error
    onError = onError
    isLight = other.isLight
}

internal val LocalColors = staticCompositionLocalOf { darkLiftingColors() }