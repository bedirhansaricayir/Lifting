package com.lifting.app.feature_player.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun PlayerSliderDuration(
    modifier: Modifier = Modifier,
    progress: Float = 0f
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = Color.Black.copy(0.3f),
                shape = RoundedCornerShape(8.dp)
            )
    ) {
        LinearProgressIndicator(
            modifier = Modifier.fillMaxWidth(),
            progress = progress,
            trackColor = Color.Gray,
            color = Color.White
        )
    }
}