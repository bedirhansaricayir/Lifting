package com.lifting.app.core.navigation.bottom_nav

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.ui.graphics.vector.ImageVector
import com.lifting.app.core.navigation.screens.NavBarScreen

/**
 * Created by bedirhansaricayir on 13.07.2024
 */

internal enum class BottomNavigationItem(
    val route: NavBarScreen,
    val title: String,
    val unselectedIcon: ImageVector,
    val selectedIcon: ImageVector
) {
    DASHBOARD(
        NavBarScreen.Dashboard,
        "Dashboard",
        Icons.Outlined.Home,
        Icons.Filled.Home
    ),
    HISTORY(
        NavBarScreen.History,
        "History",
        Icons.Outlined.Check,
        Icons.Filled.Check
    ),
    WORKOUT(
        NavBarScreen.Workout,
        "Workout",
        Icons.Outlined.PlayArrow,
        Icons.Filled.PlayArrow
    ),
    EXERCISES(
        NavBarScreen.Exercises,
        "Exercises",
        Icons.Outlined.Add,
        Icons.Filled.Add
    ),
    SETTINGS(
        NavBarScreen.Settings,
        "Settings",
        Icons.Outlined.Menu,
        Icons.Filled.Menu
    )
}