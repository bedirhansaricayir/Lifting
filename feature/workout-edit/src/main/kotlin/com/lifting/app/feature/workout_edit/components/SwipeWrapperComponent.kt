package com.lifting.app.feature.workout_edit.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissState
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.Icon
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.lifting.app.core.designsystem.LiftingTheme

/**
 * Created by bedirhansaricayir on 09.02.2025
 */

@OptIn(ExperimentalMaterialApi::class)
@Composable
internal fun SwipeableContainerComponent(
    modifier: Modifier,
    bgColor: Color,
    onSwipeDelete: () -> Unit,
    content: @Composable RowScope.() -> Unit
) {
    val coroutine = rememberCoroutineScope()

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


    fun startDeletingAnimation() {
        deletingAnimStarted = true;
    }

    val dismissState = rememberDismissState(
        confirmStateChange = {
            if (it != DismissValue.Default) {
//                handleOnSwiped()
                startDeletingAnimation();
                true
            } else {
                false
            }
        }
    )

    SwipeToDismiss(
        modifier = modifier,
        state = dismissState,
        directions = setOf(
//            DismissDirection.StartToEnd,
            DismissDirection.EndToStart
        ),
        dismissThresholds = {
            FractionalThreshold(0.5f)
        },
        background = {
            SetItemBgLayout(
                bgColor = bgColor,
                dismissState = dismissState,
                alpha = alphaAnim,
            )
        },
        dismissContent = {
            Row(
                modifier = Modifier.alpha(
                    1f - (with(dismissState.progress) {
                        if (from == DismissValue.Default && to != DismissValue.Default) {
                            fraction
                        } else {
                            0f
                        }
                    })
                )
            ) {
                content()
            }
        }
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
internal fun SetItemBgLayout(
    bgColor: Color,
    alpha: Float,
    dismissState: DismissState,
) {
    val direction =
        dismissState.dismissDirection ?: DismissDirection.EndToStart

    Box(
        Modifier
            .fillMaxSize()
            .alpha(alpha)
            .background(
                if (direction == DismissDirection.EndToStart) LiftingTheme.colors.error.copy(
                    alpha = 0.10f
                ) else bgColor
            )
            .padding(horizontal = 20.dp),
    ) {
        val alignment = Alignment.CenterEnd

        val scale by animateFloatAsState(
            if (dismissState.targetValue == DismissValue.Default) 0.75f else 1f
        )

        Icon(
            Icons.Outlined.Delete,
            tint = LiftingTheme.colors.error,
            contentDescription = "Delete",
            modifier = Modifier
                .scale(scale)
                .align(alignment)
        )

    }
}