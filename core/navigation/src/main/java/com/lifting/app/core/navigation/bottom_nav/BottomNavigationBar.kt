package com.lifting.app.core.navigation.bottom_nav

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.lifting.app.core.designsystem.LiftingTheme

/**
 * Created by bedirhansaricayir on 13.07.2024
 */

@Composable
internal fun BottomNavigationBar(
    modifier: Modifier = Modifier,
    navController: NavHostController,
) {
    val navBackStackEntry = navController.currentBackStackEntryAsState().value
    val currentDestination = navBackStackEntry?.destination

    NavigationBar(
        modifier = modifier,
        containerColor = LiftingTheme.colors.background,
        contentColor = LiftingTheme.colors.onBackground,
    ) {
        BottomNavigationItem.entries.forEachIndexed { index, item ->
            val isSelected = currentDestination?.hierarchy?.any {
                it.hasRoute(item.route::class)
            } == true

            NavigationBarItem(
                selected = isSelected,
                label = { Text(text = item.title) },
                icon = {
                    Icon(
                        imageVector = if (isSelected) item.selectedIcon else item.unselectedIcon,
                        contentDescription = item.title,
                    )
                },
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = LiftingTheme.colors.onBackground,
                    selectedTextColor = LiftingTheme.colors.onBackground,
                    indicatorColor = LiftingTheme.colors.background,
                    unselectedIconColor = LiftingTheme.colors.onBackground.copy(alpha = 0.5f),
                    unselectedTextColor = LiftingTheme.colors.onBackground.copy(alpha = 0.5f),
                )
            )
        }
    }
}