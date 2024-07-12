package com.lifting.app.core.navigation

import androidx.compose.material.navigation.ModalBottomSheetLayout
import androidx.compose.material.navigation.rememberBottomSheetNavigator
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController

@Composable
fun MainScreen() {
    val bottomSheetNavigator = rememberBottomSheetNavigator()
    val navController = rememberNavController(bottomSheetNavigator)

    ModalBottomSheetLayout(
        bottomSheetNavigator = bottomSheetNavigator
    ) {
        AppNavigationWithBottomBar(navController = navController)
    }
}