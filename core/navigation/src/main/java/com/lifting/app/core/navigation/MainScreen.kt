package com.lifting.app.core.navigation

import androidx.compose.material.navigation.BottomSheetNavigator
import androidx.compose.material.navigation.ModalBottomSheetLayout
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

@Composable
fun MainScreen(
    navController: NavHostController,
    bottomSheetNavigator: BottomSheetNavigator,
    appNavigationWithBottomBar: @Composable (NavHostController) -> Unit
) {
    ModalBottomSheetLayout(
        bottomSheetNavigator = bottomSheetNavigator
    ) {
        appNavigationWithBottomBar(navController)
    }
}