package com.lifting.app.core.navigation

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.navigation.BottomSheetNavigator
import androidx.compose.material.navigation.ModalBottomSheetLayout
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun MainScreen(
    modifier: Modifier,
    navController: NavHostController,
    bottomSheetNavigator: BottomSheetNavigator,
    bottomBar: @Composable (NavHostController) -> Unit
) {
    ModalBottomSheetLayout(
        modifier = modifier,
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        bottomSheetNavigator = bottomSheetNavigator
    ) {
        bottomBar(navController)
    }
}