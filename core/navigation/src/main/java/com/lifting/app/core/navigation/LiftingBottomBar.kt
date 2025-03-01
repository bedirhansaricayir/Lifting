package com.lifting.app.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.lifting.app.core.navigation.bottom_nav.BottomNavigationBar

@Composable
fun LiftingBottomBar(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    BottomNavigationBar(
        modifier = modifier,
        navController = navController
    )
}