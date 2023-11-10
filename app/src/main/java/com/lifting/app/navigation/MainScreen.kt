package com.lifting.app.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.media3.common.util.UnstableApi
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.lifting.app.navigation.graphs.NavGraph


@RequiresApi(Build.VERSION_CODES.O)
@UnstableApi
@Composable
fun MainScreen(navController: NavHostController = rememberNavController(),isPremiumUser: (Boolean) -> Unit) {
    Scaffold(bottomBar = { BottomNavigationBar(navController = navController) }) {
        Box(
            modifier = Modifier.padding(
                start = 0.dp,
                end = 0.dp,
                top = 0.dp,
                bottom = it.calculateBottomPadding()
            )
        ) {
            NavGraph(navController = navController, isPremiumUser = isPremiumUser)
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController, modifier: Modifier = Modifier) {
    val screens = listOf(
        Screen.HomeScreen,
        Screen.TrackerScreen,
        Screen.CalculatorScreen
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val bottomBarDestination = screens.any{
        it.route == currentDestination?.route
    }

    if (bottomBarDestination) {
        NavigationBar(containerColor = MaterialTheme.colorScheme.surface) {
            screens.forEach {
                AddItem(
                    screen = it,
                    currentDestination = currentDestination,
                    navController = navController
                )
            }
        }
    }
}

@Composable
fun RowScope.AddItem(
    screen: Screen,
    currentDestination: NavDestination?,
    navController: NavHostController
) {
    val selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val current = navBackStackEntry?.destination
    NavigationBarItem(
        colors = NavigationBarItemDefaults.colors(
            selectedIconColor = MaterialTheme.colorScheme.onSecondaryContainer,
            unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
            selectedTextColor = MaterialTheme.colorScheme.onSurface,
            unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
            indicatorColor = MaterialTheme.colorScheme.secondaryContainer
        ),
        label = {
            Text(
                text = stringResource(screen.title),
                fontSize = 12.sp
            )
        },
        icon = {
            Icon(
                painter = painterResource(id = screen.icon!!),
                contentDescription = "Navigation Icon"
            )
        },
        selected = selected,
        onClick = {
            if (screen.route != current?.route){
                navController.navigate(screen.route) {
                    popUpTo(navController.graph.findStartDestination().id)
                    launchSingleTop = true
                }
            }
        }
    )
}