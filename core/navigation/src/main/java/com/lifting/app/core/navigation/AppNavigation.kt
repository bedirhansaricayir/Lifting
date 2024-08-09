package com.lifting.app.core.navigation


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.lifting.app.core.navigation.bottom_nav.BottomNavigationBar
@Composable
fun AppNavigationWithBottomBar(
    navController: NavHostController,
    appNavigation: @Composable (navController: NavHostController) -> Unit
) {
    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController)
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = it.calculateBottomPadding())
        ) {
            appNavigation(navController)
        }
    }
}

