package com.lifting.app

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.*
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.media3.common.util.UnstableApi
import androidx.navigation.compose.rememberNavController
import com.github.mikephil.charting.utils.Utils
import com.lifting.app.feature_home.presentation.onboarding.SplashViewModel
import com.lifting.app.navigation.graphs.RootNavGraph
import com.lifting.app.theme.FitnessAppTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
@UnstableApi
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var splashViewModel: SplashViewModel
    @Inject
    lateinit var appUpdateController: AppUpdateController

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appUpdateController.initRemoteConfig()
        setContent {
            var isPremiumUser by remember { mutableStateOf(false) }
            FitnessAppTheme(isPremiumUser = isPremiumUser) {
                val state = splashViewModel.startDestination.collectAsState().value
                WindowCompat.setDecorFitsSystemWindows(window, false)
                actionBar?.hide()
                Utils.init(this)
                if (!state.isNullOrEmpty()) {
                    RootNavGraph(
                        navController = rememberNavController(),
                        startDestination = state,
                        isPremiumUser = { isPremiumUser = it })
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        appUpdateController.resumeUpdate()
    }

    override fun onDestroy() {
        super.onDestroy()
        appUpdateController.unregisterListener()
    }
}