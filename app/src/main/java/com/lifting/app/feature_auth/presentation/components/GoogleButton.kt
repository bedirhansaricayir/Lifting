package com.lifting.app.feature_auth.presentation.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.lifting.app.theme.White40
import com.lifting.app.theme.grey50
import kotlinx.coroutines.delay

@Composable
fun GoogleButton(
    modifier: Modifier = Modifier,
    text: String,
    icon: Int,
    shape: Dp = 4.dp,
    borderColor: Color = Color.LightGray,
    backgroundColor: Color = grey50,
    progressIndicatorColor: Color = Color.White,
    isClickable: Boolean,
    onClicked: () -> Unit
) {
    var clicked by remember { mutableStateOf(false) }
    LaunchedEffect(key1 = isClickable) {
        if (!isClickable) {
            delay(2000)
            clicked = false
        }
    }
    Surface(
        modifier = modifier.clickable(enabled = isClickable && !clicked) {
            clicked = true
            onClicked()
        },
        shape = RoundedCornerShape(shape),
        border = BorderStroke(width = 1.dp, color = borderColor),
        color = backgroundColor
    ) {
        Row(
            modifier = Modifier
                .padding(
                    start = 12.dp,
                    end = 16.dp,
                    top = 12.dp,
                    bottom = 12.dp
                )
                .animateContentSize(
                    animationSpec = tween(
                        durationMillis = 300,
                        easing = LinearOutSlowInEasing
                    )
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = "Google Button",
                tint = Color.Unspecified
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = text, color =  White40)
            if (clicked) {
                Spacer(modifier = Modifier.width(16.dp))
                CircularProgressIndicator(
                    modifier = Modifier
                        .height(16.dp)
                        .width(16.dp),
                    strokeWidth = 2.dp,
                    color = progressIndicatorColor
                )
            }
        }
    }
}