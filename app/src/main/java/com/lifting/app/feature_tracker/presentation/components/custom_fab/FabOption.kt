package com.lifting.app.feature_tracker.presentation.components.custom_fab

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color

@Immutable
interface FabOption {
    @Stable
    val iconTint: Color
    @Stable val backgroundTint: Color
    @Stable val showLabel: Boolean
}

private class FabOptionImpl(
    override val iconTint: Color,
    override val backgroundTint: Color,
    override val showLabel: Boolean
) : FabOption

@Composable
fun FabOption(
    backgroundTint: Color = MaterialTheme.colorScheme.primaryContainer,
    iconTint: Color = MaterialTheme.colorScheme.onPrimaryContainer,
    showLabel: Boolean = false
): FabOption = FabOptionImpl(iconTint, backgroundTint, showLabel)