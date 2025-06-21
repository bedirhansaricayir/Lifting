package com.lifting.app.core.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
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
fun LiftingAnimationContainer(
    visible: Boolean,
    content: @Composable (AnimatedVisibilityScope.() -> Unit),
    modifier: Modifier = Modifier,
    enterTransition: EnterTransition = LiftingSlidingContainerDefaults.enterTransition,
    exitTransition: ExitTransition = LiftingSlidingContainerDefaults.exitTransition
) {
    AnimatedVisibility(
        visible = visible,
        modifier = modifier,
        enter = enterTransition,
        exit = exitTransition
    ) {
        content.invoke(this)
    }
}


object LiftingSlidingContainerDefaults {
    private const val DURATION_ENTER = 300

    val enterTransition
        @Composable get() = with(LocalDensity.current) {
            slideInHorizontally(
                animationSpec = tween(durationMillis = DURATION_ENTER)
            ) {
                40.dp.roundToPx()
            } + expandHorizontally(
                animationSpec = tween(durationMillis = DURATION_ENTER),
                expandFrom = Alignment.End
            ) + fadeIn(
                initialAlpha = 0.3f
            )
        }

    val exitTransition
        @Composable get() = with(LocalDensity.current) {
            slideOutHorizontally {
                40.dp.roundToPx()
            } + shrinkHorizontally() + fadeOut()
        }
}