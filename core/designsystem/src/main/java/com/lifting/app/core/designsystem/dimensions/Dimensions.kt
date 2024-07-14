package com.lifting.app.core.designsystem.dimensions

/**
 * Created by bedirhansaricayir on 14.07.2024
 */

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Immutable
class LiftingDimensions {
    val default: Dp
        @Composable
        get() = 0.dp
    val extraSmall: Dp
        @Composable
        get() = 4.dp
    val small: Dp
        @Composable
        get() = 8.dp
    val medium: Dp
        @Composable
        get() = 12.dp
    val large: Dp
        @Composable
        get() = 16.dp
    val xLarge: Dp
        @Composable
        get() = 20.dp

}

internal val LocalDimensions = staticCompositionLocalOf { LiftingDimensions() }