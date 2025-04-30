package com.lifting.app.core.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Created by bedirhansaricayir on 18.08.2024
 */

@Composable
fun LiftingIconButton(
    modifier: Modifier = Modifier,
    imageVector: ImageVector? = null,
    painterRes: Painter? = null,
    contentDescription: String,
    enabled: Boolean = true,
    tint: Color,
    onClick: () -> Unit

) {
    val alpha by animateFloatAsState(targetValue = if (enabled) 1f else 0.5f, label = "")

    IconButton(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier.alpha(alpha)
    ) {
        imageVector?.let {
            Icon(
                imageVector = it,
                contentDescription = contentDescription,
                tint = tint
            )
        } ?: painterRes?.let {
            Icon(
                painter = it,
                contentDescription = contentDescription,
                tint = tint
            )
        }
    }
}