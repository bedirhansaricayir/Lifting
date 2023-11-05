package com.lifting.app.common.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

val transition = (fadeIn(animationSpec = tween(220, delayMillis = 90)) +
        scaleIn(initialScale = 0.92f, animationSpec = tween(220, delayMillis = 90)))
    .togetherWith(fadeOut(animationSpec = tween(90)))

@Composable
fun AnimatedContentSwitcher(
    loadingState: Boolean,
    transitionSpec: ContentTransform = transition,
    contentLoading: @Composable () -> Unit,
    contentLoaded: @Composable () -> Unit

) {
    AnimatedContent(
        loadingState,
        transitionSpec = {
            transitionSpec
        },
        label = "Animated Content"
    ) { state ->
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            when (state) {
                true -> contentLoading()
                false -> contentLoaded()
            }
        }
    }
}