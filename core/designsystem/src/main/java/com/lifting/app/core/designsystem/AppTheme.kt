package com.lifting.app.core.designsystem

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.remember
import com.lifting.app.core.designsystem.colors.LiftingColors
import com.lifting.app.core.designsystem.colors.LocalColors
import com.lifting.app.core.designsystem.colors.updateColorsFrom
import com.lifting.app.core.designsystem.dimensions.LiftingDimensions
import com.lifting.app.core.designsystem.dimensions.LocalDimensions
import com.lifting.app.core.designsystem.icons.LiftingIcons
import com.lifting.app.core.designsystem.icons.LocalIcons
import com.lifting.app.core.designsystem.shapes.LiftingShapes
import com.lifting.app.core.designsystem.shapes.LocalShapes
import com.lifting.app.core.designsystem.typography.LiftingTypography
import com.lifting.app.core.designsystem.typography.LocalTypography

/**
 * Created by bedirhansaricayir on 14.07.2024
 */

@Composable
fun LiftingTheme(
    colors: LiftingColors = LiftingTheme.colors,
    dimensions: LiftingDimensions = LiftingTheme.dimensions,
    typography: LiftingTypography = LiftingTheme.typography,
    shapes: LiftingShapes = LiftingTheme.shapes,
    icons: LiftingIcons = LiftingTheme.icons,
    content: @Composable () -> Unit
) {
    val rememberedColors = remember { colors.copy() }
        .apply { updateColorsFrom(colors) }

    CompositionLocalProvider(
        LocalColors provides rememberedColors,
        LocalDimensions provides dimensions,
        LocalTypography provides typography,
        LocalShapes provides shapes,
        LocalIcons provides icons,
        content = content
    )
}


object LiftingTheme {

    val colors: LiftingColors
        @Composable
        @ReadOnlyComposable
        get() = LocalColors.current

    val typography: LiftingTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalTypography.current

    val dimensions: LiftingDimensions
        @Composable
        @ReadOnlyComposable
        get() = LocalDimensions.current

    val shapes: LiftingShapes
        @Composable
        @ReadOnlyComposable
        get() = LocalShapes.current

    val icons: LiftingIcons
        @Composable
        @ReadOnlyComposable
        get() = LocalIcons.current

}