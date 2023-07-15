package com.lifting.app.presentation

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import com.lifting.app.navigation.graphs.RootNavGraph
import com.lifting.app.theme.FitnessAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FitnessAppTheme() {
                WindowCompat.setDecorFitsSystemWindows(window, false)
                RootNavGraph(navController = rememberNavController())

            }
        }
    }
}