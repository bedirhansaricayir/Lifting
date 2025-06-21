package com.lifting.app.core.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import com.lifting.app.core.designsystem.LiftingTheme

/**
 * Created by bedirhansaricayir on 22.03.2025
 */

@Composable
fun LiftingChip(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = LiftingTheme.shapes.medium,
    containerColor: Color = LiftingTheme.colors.primary,
    contentColor: Color = LiftingTheme.colors.onPrimary,
    border: BorderStroke? = null,
    content: @Composable () -> Unit
) {
    Surface(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        color = containerColor,
        contentColor = contentColor,
        shape = shape,
        border = border
    ) {
        content()
    }
}