package com.lifting.app.core.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp

/**
 * Created by bedirhansaricayir on 01.05.2025
 */

@Composable
fun HorizontalSlidingAnimationContainer(
    visible: Boolean,
    content: @Composable (AnimatedVisibilityScope.() -> Unit),
    modifier: Modifier = Modifier
) {
    val density = LocalDensity.current

    AnimatedVisibility(
        visible = visible,
        modifier = modifier,
        enter = slideInHorizontally(
            animationSpec = tween(durationMillis = DURATION_ENTER)
        ) {
            with(density) { 40.dp.roundToPx() }
        } + expandHorizontally(
            animationSpec = tween(durationMillis = DURATION_ENTER),
            expandFrom = Alignment.End
        ) + fadeIn(
            initialAlpha = 0.3f
        ),
        exit = slideOutHorizontally {
            with(density) { 40.dp.roundToPx() }
        } + shrinkHorizontally() + fadeOut()
    ) {
        content.invoke(this)
    }
}

private const val DURATION_ENTER = 300