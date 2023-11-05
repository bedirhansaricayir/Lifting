package com.lifting.app.feature_player.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.lifting.app.common.util.noRippleClickable

@Composable
fun PlayerPlaybackSpeed(
    modifier: Modifier = Modifier,
    onPlaybackSpeedChanged: (playbackSpeed: Float) -> Unit
) {
    var playbackSpeed by remember { mutableFloatStateOf(1f) }

    Box(
        modifier = modifier
            .padding(8.dp)
            .background(
                color = Color.Black.copy(0.3f),
                shape = RoundedCornerShape(8.dp)
            )
            .padding(8.dp)
            .noRippleClickable {
                if (playbackSpeed < 2f) {
                    playbackSpeed += 0.5f
                } else {
                    playbackSpeed = 0.5f
                }
                onPlaybackSpeedChanged(playbackSpeed)
            }
    ) {
        Text(
            text = "$playbackSpeed x", color = Color.White
        )
    }
}