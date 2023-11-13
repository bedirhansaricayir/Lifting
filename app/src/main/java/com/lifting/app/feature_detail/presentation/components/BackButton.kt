package com.lifting.app.feature_detail.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.lifting.app.common.util.noRippleClickable

@Composable
fun BackButton(
    modifier: Modifier = Modifier,
    onBackNavigationIconClicked: () -> Unit
) {
    Box(
        modifier = modifier
            .padding(8.dp)
            .background(
                color = Color.LightGray.copy(0.3f),
                shape = CircleShape
            )
            .padding(8.dp)
            .noRippleClickable {
                onBackNavigationIconClicked.invoke()
            }
    ) {
        Icon(
            imageVector = Icons.Default.KeyboardArrowLeft,
            contentDescription = "",
            tint = Color.White
        )
    }
}