package com.lifting.app.feature_player.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.lifting.app.R
import com.lifting.app.common.util.noRippleClickable

@Composable
fun PlayerBackButton(
    modifier: Modifier = Modifier,
    onBackNavigationIconClicked: () -> Unit
) {
    Box(
        modifier = modifier
            .padding(8.dp)
            .background(
                color = Color.Black.copy(0.3f),
                shape = CircleShape
            )
            .padding(8.dp)
            .noRippleClickable {
                onBackNavigationIconClicked.invoke()
            }
    ) {
        Icon(
            painter = painterResource(id = R.drawable.baseline_arrow_back_ios_new_24),
            contentDescription = "",
            tint = Color.White
        )
    }
}