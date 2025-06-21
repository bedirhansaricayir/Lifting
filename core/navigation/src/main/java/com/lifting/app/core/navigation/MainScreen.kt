package com.lifting.app.core.navigation

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.navigation.BottomSheetNavigator
import androidx.compose.material.navigation.ModalBottomSheetLayout
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun BottomSheetLayout(
    modifier: Modifier = Modifier,
    bottomSheetNavigator: BottomSheetNavigator,
    content: @Composable () -> Unit
) {
    ModalBottomSheetLayout(
        modifier = modifier,
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        bottomSheetNavigator = bottomSheetNavigator
    ) {
        content()
    }
}

val LocalNavHostController =
    staticCompositionLocalOf<NavHostController> { error("No NavController found!") }

@Composable
fun NavHostControllerHost(
    navController: NavHostController,
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(LocalNavHostController provides navController) {
        content()
    }
}