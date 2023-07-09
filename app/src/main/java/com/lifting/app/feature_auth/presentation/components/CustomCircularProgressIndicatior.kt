package com.lifting.app.feature_auth.presentation.components

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun CustomCircularProgressIndicatior(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.background,
    thickness: Dp = 2.5.dp
) {
    CircularProgressIndicator(modifier = modifier, color = color, strokeWidth = thickness)
}