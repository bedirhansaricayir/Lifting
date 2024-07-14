package com.lifting.app.core.designsystem.shapes

/**
 * Created by bedirhansaricayir on 14.07.2024
 */

import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.dp

@Immutable
class LiftingShapes {

    val small: CornerBasedShape
        @Composable
        get() = RoundedCornerShape(4.dp)

    val medium: CornerBasedShape
        @Composable
        get() = RoundedCornerShape(8.dp)

    val large: CornerBasedShape
        @Composable
        get() = RoundedCornerShape(12.dp)

    val xLarge: CornerBasedShape
        @Composable
        get() = RoundedCornerShape(16.dp)
}

internal val LocalShapes = staticCompositionLocalOf { LiftingShapes() }