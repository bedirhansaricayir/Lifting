package com.lifting.app.core.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import com.lifting.app.core.designsystem.LiftingTheme
import com.lifting.app.core.ui.extensions.lighterColor

/**
 * Created by bedirhansaricayir on 22.03.2025
 */

@Composable
fun LiftingChip(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = LiftingTheme.shapes.medium,
    backgroundColor: Color = LiftingTheme.colors.background.lighterColor(0.1f),
    border: BorderStroke? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    content: @Composable RowScope.() -> Unit
) {
    Surface(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        color = backgroundColor,
        shape = shape,
        border = border
    ) {
        Row(
            modifier = Modifier
                .defaultMinSize(
                    minHeight = 32.dp
                )
                .padding(LiftingTheme.dimensions.small)
                .padding(
                    end = if (trailingIcon == null) LiftingTheme.dimensions.medium else LiftingTheme.dimensions.default
                ),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            content()
            if (trailingIcon != null) {
                Spacer(Modifier.width(LiftingTheme.dimensions.extraSmall))
                trailingIcon()
            }
        }
    }
}