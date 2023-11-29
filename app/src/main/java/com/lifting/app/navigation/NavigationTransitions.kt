package com.lifting.app.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn

private const val FADE_IN_DURATION = 300
private const val SLIDE_INTO_CONTAINER_DURATION = 300

fun EnterTransition(
    fadeInDuration: Int = FADE_IN_DURATION,
    fadeInEasing: Easing = LinearEasing,
    slideIntoContainerDuration: Int = SLIDE_INTO_CONTAINER_DURATION,
    slideIntoContainerEasing: Easing = EaseIn,
    towards: AnimatedContentTransitionScope.SlideDirection = AnimatedContentTransitionScope.SlideDirection.Down
): EnterTransition {
    return fadeIn(
        animationSpec = tween(
            fadeInDuration, easing = fadeInEasing
        )
    )
}
