package com.lifting.app.feature_home.presentation

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.*
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import com.github.mikephil.charting.utils.Utils
import com.lifting.app.feature_home.presentation.onboarding.SplashViewModel
import com.lifting.app.navigation.graphs.RootNavGraph
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
             var isPremiumUser by remember { mutableStateOf(false) }
            FitnessAppTheme(isPremiumUser = isPremiumUser) {
                val state = splashViewModel.startDestination.collectAsState().value
                WindowCompat.setDecorFitsSystemWindows(window, false)
                Utils.init(this)
                actionBar?.hide()
                if (!state.isNullOrEmpty()) {
                    RootNavGraph(navController = rememberNavController(), startDestination = state, isPremiumUser = { isPremiumUser = it})
                }
            }
        }
    }
}