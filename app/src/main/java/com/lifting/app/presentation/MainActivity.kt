package com.lifting.app.presentation

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.runtime.collectAsState
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import com.lifting.app.navigation.graphs.RootNavGraph
import com.lifting.app.presentation.onboarding.SplashViewModel
import com.lifting.app.theme.FitnessAppTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var splashViewModel: SplashViewModel
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FitnessAppTheme {
                val state = splashViewModel.startDestination.collectAsState().value
                WindowCompat.setDecorFitsSystemWindows(window, false)
                if (state != null) {
                    RootNavGraph(navController = rememberNavController(), startDestination = state)
                }
            }
        }
    }
}