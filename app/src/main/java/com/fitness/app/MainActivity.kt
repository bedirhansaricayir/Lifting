package com.fitness.app

import android.app.Activity
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.fitness.app.navigation.MainScreen
import com.fitness.app.navigation.Screen
import com.fitness.app.presentation.onboarding.SplashViewModel
import com.fitness.app.ui.theme.FitnessAppTheme
import com.fitness.app.ui.theme.black20
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var splashViewModel: SplashViewModel

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().setKeepOnScreenCondition {
            !splashViewModel.isLoading.value
        }
        setContent {
            FitnessAppTheme() {
                val screen by splashViewModel.startDestination
                if (screen == Screen.OnBoardingScreen.route)  StatusBarColor(color = Color.Black) else StatusBarColor(color = black20)
                val navController = rememberNavController()
                MainScreen(navController = navController, startDestination = screen)

            }
        }
    }
}

@Composable
fun StatusBarColor(color: Color) {
    val view = LocalView.current
    val window = (view.context as Activity).window
    window.statusBarColor = color.toArgb()
    window.navigationBarColor = color.toArgb()
}