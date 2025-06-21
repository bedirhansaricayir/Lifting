package com.lifting.app.core.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.Icon
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxState
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.lifting.app.core.common.extensions.EMPTY
import com.lifting.app.core.designsystem.LiftingTheme

/**
 * Created by bedirhansaricayir on 09.02.2025
 */

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun LiftingSwipeToDismissBox(
    modifier: Modifier,
    containerColor: Color,
    onSwipeDelete: () -> Unit,
    content: @Composable RowScope.() -> Unit
) {

    var deletingAnimStarted by remember() {
        mutableStateOf(false);
    }

    val alphaAnim: Float by animateFloatAsState(
        if (deletingAnimStarted) 0f else 1f,
        animationSpec = tween(250),
        finishedListener = {
            onSwipeDelete()
        }
    )

    val state = rememberSwipeToDismissBoxState(
        initialValue = SwipeToDismissBoxValue.Settled,
        confirmValueChange = {
            if (it != SwipeToDismissBoxValue.Settled) {
                deletingAnimStarted = true
                true
            } else {
                false
            }
        },
        positionalThreshold = { it * .25f }
    )

    SwipeToDismissBox(
        modifier = modifier,
        state = state,
        enableDismissFromEndToStart = true,
        enableDismissFromStartToEnd = false,
        backgroundContent = {
            BackgroundContent(
                containerColor = containerColor,
                state = state,
                alpha = alphaAnim,
            )
        }
    ) {
        Row(
            modifier = Modifier.alpha(
                1f - (with(state) {
                    if (currentValue == SwipeToDismissBoxValue.Settled && targetValue != SwipeToDismissBoxValue.Settled) {
                        progress
                    } else {
                        0f
                    }
                })
            )
        ) {
            content()
        }
    }
}

@Composable
private fun BackgroundContent(
    containerColor: Color,
    alpha: Float,
    state: SwipeToDismissBoxState,
) {
    val direction = state.dismissDirection

    Box(
        Modifier
            .fillMaxSize()
            .alpha(alpha)
            .background(
                if (direction == SwipeToDismissBoxValue.EndToStart) LiftingTheme.colors.error.copy(
                    alpha = 0.10f
                ) else containerColor
            )
            .padding(horizontal = 20.dp),
    ) {
        val scale by animateFloatAsState(
            if (state.targetValue == SwipeToDismissBoxValue.Settled) 0.75f else 1f
        )

        Icon(
            imageVector = LiftingTheme.icons.delete,
            tint = LiftingTheme.colors.error,
            contentDescription = String.EMPTY,
            modifier = Modifier
                .scale(scale)
                .align(Alignment.CenterEnd)
        )
    }
}