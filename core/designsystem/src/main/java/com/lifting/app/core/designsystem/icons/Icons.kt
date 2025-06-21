package com.lifting.app.core.designsystem.icons

/**
 * Created by bedirhansaricayir on 14.07.2024
 */

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Done
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material.icons.outlined.KeyboardArrowUp
import androidx.compose.material.icons.outlined.MailOutline
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.lifting.app.core.designsystem.R

@Immutable
class LiftingIcons {

    @Composable
    fun getIcon(icon: String): ImageVector {
        return when (icon) {
            "palette" -> palette
            "dumbbell" -> dumbbell
            "language" -> language
            "run" -> run
            else -> search
        }
    }

    val search: ImageVector
        @Composable
        get() = Icons.Default.Search

    val add: ImageVector
        @Composable
        get() = Icons.Default.Add

    val back: ImageVector
        @Composable
        get() = Icons.Default.ArrowBack

    val filter: ImageVector
        @Composable
        get() = ImageVector.vectorResource(id = R.drawable.filter_icon)

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

    val expand: ImageVector
        @Composable
        get() = ImageVector.vectorResource(id = R.drawable.expand_icon)

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
        get() = ImageVector.vectorResource(id = R.drawable.calendar_month)

    val timer: ImageVector
        @Composable
        get() = ImageVector.vectorResource(id = R.drawable.ic_timer)

    val weight: ImageVector
        @Composable
        get() = ImageVector.vectorResource(id = R.drawable.ic_weight)

    val trophy: ImageVector
        @Composable
        get() = ImageVector.vectorResource(id = R.drawable.ic_trophy)

    val replay: ImageVector
        @Composable
        get() = ImageVector.vectorResource(id = R.drawable.ic_replay)

    val grid: ImageVector
        @Composable
        get() = ImageVector.vectorResource(id = R.drawable.ic_grid)

    val list: ImageVector
        @Composable
        get() = ImageVector.vectorResource(id = R.drawable.ic_list)

    val play: ImageVector
        @Composable
        get() = Icons.Outlined.PlayArrow

    val chevronDown: ImageVector
        @Composable
        get() = Icons.Outlined.KeyboardArrowDown

    val chevronUp: ImageVector
        @Composable
        get() = Icons.Outlined.KeyboardArrowUp

    val check: ImageVector
        @Composable
        get() = Icons.Default.Check

    val dumbbell: ImageVector
        @Composable
        get() = Icons.Outlined.Dumbbell

    val run: ImageVector
        @Composable
        get() = Icons.Outlined.Run

    val folder: ImageVector
        @Composable
        get() = Icons.Outlined.Folder

    val restore: ImageVector
        @Composable
        get() = Icons.Outlined.Restore

    val thumbsUpDown: ImageVector
        @Composable
        get() = Icons.Outlined.ThumbsUpDown

    val mail: ImageVector
        @Composable
        get() = Icons.Outlined.MailOutline

    val palette: ImageVector
        @Composable
        get() = ImageVector.vectorResource(id = R.drawable.ic_palette)

    val plates: ImageVector
        @Composable
        get() = Icons.Filled.Plates

    val dialPad: ImageVector
        @Composable
        get() = Icons.Outlined.Dialpad

    val backSpace: ImageVector
        @Composable
        get() = Icons.Outlined.Backspace

    val revert: ImageVector
        @Composable
        get() = ImageVector.vectorResource(id = R.drawable.ic_revert)

    val language: ImageVector
        @Composable
        get() = ImageVector.vectorResource(id = R.drawable.ic_language)

    val pause: ImageVector
        @Composable
        get() = ImageVector.vectorResource(id = R.drawable.ic_pause)

}

internal val LocalIcons = staticCompositionLocalOf { LiftingIcons() }