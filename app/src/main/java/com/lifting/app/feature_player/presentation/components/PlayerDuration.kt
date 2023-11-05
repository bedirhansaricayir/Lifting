package com.lifting.app.feature_player.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun PlayerDuration(
    modifier: Modifier = Modifier,
    currentPosition: String
) {
    Box(
        modifier = modifier
            .padding(8.dp)
            .background(
                color = Color.Black.copy(0.3f),
                shape = RoundedCornerShape(8.dp)
            )
            .padding(8.dp)
    ) {
        Text(
            text = currentPosition, color = Color.White
        )
    }
}