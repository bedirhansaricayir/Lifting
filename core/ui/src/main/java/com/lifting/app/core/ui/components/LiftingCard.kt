package com.lifting.app.core.ui.components

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import com.lifting.app.core.designsystem.LiftingTheme

/**
 * Created by bedirhansaricayir on 02.06.2025
 */

@Composable
fun LiftingCard(
    modifier: Modifier = Modifier,
    shape: Shape = LiftingTheme.shapes.medium,
    colors: CardColors = CardDefaults.cardColors(containerColor = LiftingTheme.colors.surface),
    onClick: () -> Unit = {},
    content: @Composable (ColumnScope.() -> Unit)
) {
    Card(
        modifier = modifier,
        shape = shape,
        colors = colors,
        onClick = onClick,
        content = content
    )
}