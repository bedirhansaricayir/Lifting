package com.lifting.app.core.designsystem.icons

/**
 * Created by bedirhansaricayir on 14.07.2024
 */

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Done
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Info
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import com.lifting.app.core.designsystem.R

@Immutable
class LiftingIcons {

    val search: ImageVector
        @Composable
        get() = Icons.Default.Search

    val add: ImageVector
        @Composable
        get() = Icons.Default.Add

    val back: ImageVector
        @Composable
        get() = Icons.Default.ArrowBack

    val filter: Painter
        @Composable
        get() = painterResource(id = R.drawable.filter_icon)

    val clear: ImageVector
        @Composable
        get() = Icons.Default.Clear

    val close: ImageVector
        @Composable
        get() = Icons.Default.Close

    val done: ImageVector
        @Composable
        get() = Icons.Default.Done

    val forward: ImageVector
        @Composable
        get() = Icons.Default.KeyboardArrowRight

    val expand: Painter
        @Composable
        get() = painterResource(id = R.drawable.expand_icon)

    val moreVert: ImageVector
        @Composable
        get() = Icons.Default.MoreVert

    val outlinedDone: ImageVector
        @Composable
        get() = Icons.Outlined.Done

    val edit: ImageVector
        @Composable
        get() = Icons.Outlined.Edit

    val delete: ImageVector
        @Composable
        get() = Icons.Outlined.Delete

    val info: ImageVector
        @Composable
        get() = Icons.Outlined.Info

    val calendar: ImageVector
        @Composable
        get() = Icons.Outlined.DateRange
}

internal val LocalIcons = staticCompositionLocalOf { LiftingIcons() }