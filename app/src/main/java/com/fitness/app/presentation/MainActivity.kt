package com.fitness.app.presentation

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import com.fitness.app.navigation.graphs.RootNavGraph
import com.fitness.app.presentation.onboarding.SplashViewModel
import com.fitness.app.ui.theme.FitnessAppTheme
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
                WindowCompat.setDecorFitsSystemWindows(window, false)
                val screen by splashViewModel.startDestination
                val navController = rememberNavController()
                screen?.let { RootNavGraph(navController = navController, startDestination = it) }

            }
        }
    }
}