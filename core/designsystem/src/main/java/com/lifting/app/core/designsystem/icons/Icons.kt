package com.lifting.app.core.designsystem.icons

/**
 * Created by bedirhansaricayir on 14.07.2024
 */

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.vector.ImageVector

@Immutable
class LiftingIcons {

    val settings: ImageVector
        @Composable
        get() = Icons.Default.Settings

    val check: ImageVector
        @Composable
        get() = Icons.Default.Check

    val done: ImageVector
        @Composable
        get() = Icons.Default.Done
}

internal val LocalIcons = staticCompositionLocalOf { LiftingIcons() }