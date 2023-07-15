package com.lifting.app.feature_auth.presentation.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.lifting.app.R

@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier
        .fillMaxSize()
        .background(color = Color.Black), contentAlignment = Alignment.Center) {
        Image(modifier = modifier.size(100.dp),painter = painterResource(id = R.drawable.app_launcher_icon), contentDescription = "Launcher Icon")
    }
}