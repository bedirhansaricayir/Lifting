package com.lifting.app.core.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.lifting.app.core.navigation.bottom_nav.BottomNavigationBar

@Composable
fun ScaffoldWithBottomBar(
    navController: NavHostController,
    appNavigation: @Composable (padding: PaddingValues) -> Unit
) {
    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                modifier = Modifier,
                navController = navController
            )
        }
    ) { paddingValues ->
        appNavigation(paddingValues)
    }
}

