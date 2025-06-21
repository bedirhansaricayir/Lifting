package com.lifting.app.core.ui.components.in_app_notification

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.lifting.app.core.designsystem.LiftingTheme
import com.lifting.app.core.ui.components.LiftingButton
import com.lifting.app.core.ui.components.LiftingButtonType
import kotlinx.coroutines.delay

/**
 * Created by bedirhansaricayir on 19.06.2025
 */

@Composable
fun LiftingInAppNotification(
    notification: LiftingInAppNotificationData,
    onDismiss: (String) -> Unit
) {
    val totalTime = notification.duration
    var transition by remember { mutableFloatStateOf(0f) }
    var remainingTime by remember { mutableLongStateOf(totalTime) }

    val progress by animateFloatAsState(
        targetValue = remainingTime / totalTime.toFloat(),
        animationSpec = tween(300),
        label = "notificationProgress"
    )
    val transitionAnimation by animateFloatAsState(
        targetValue = transition,
        animationSpec = tween(300),
        label = "NotificationFade"
    )

    val remainingAsText by remember(progress) {
        mutableIntStateOf((progress * notification.duration / 1000).toInt())
    }

    LaunchedEffect(notification.id) {
        transition = 1f
        if (notification.showTimer) {
            val start = System.currentTimeMillis()
            while (true) {
                val elapsed = System.currentTimeMillis() - start
                val rem = (totalTime - elapsed).coerceAtLeast(0L)
                remainingTime = rem
                if (rem <= 0L) {
                    onDismiss(notification.id)
                    transition = 0f
                    break
                }
                delay(100L)
            }
        } else {
            delay(notification.duration)
            onDismiss(notification.id)
            transition = 0f
        }
    }

    Box(
        modifier = Modifier
            .windowInsetsPadding(WindowInsets.safeDrawing)
            .padding(horizontal = LiftingTheme.dimensions.medium)
            .shadow(
                elevation = LiftingTheme.dimensions.medium,
                shape = LiftingTheme.shapes.medium
            )
            .background(LiftingInAppNotificationDefaults.containerColor(notification.type))
            .padding(LiftingTheme.dimensions.medium)
            .fillMaxWidth()
            .graphicsLayer {
                alpha = transitionAnimation
                translationY = -20f * (1f - transitionAnimation)
            }
    ) {
        Row(
            verticalAlignment = if (notification.showTimer) Alignment.Top else Alignment.CenterVertically,
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = notification.message,
                color = LiftingInAppNotificationDefaults.textColor(notification.type),
                style = LiftingTheme.typography.subtitle2.copy(fontWeight = FontWeight.SemiBold)
            )

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (notification.showTimer) {
                    Box(
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            progress = { progress },
                            color = LiftingTheme.colors.primary,
                            trackColor = LiftingTheme.colors.primary.copy(0.5f),
                            gapSize = 0.dp
                        )
                        Text(
                            text = remainingAsText.toString(),
                            color = LiftingInAppNotificationDefaults.textColor(notification.type)
                        )
                    }
                }
                if (notification.actionLabel != null && notification.onAction != null) {
                    LiftingButton(
                        modifier = Modifier.align(Alignment.End),
                        buttonType = LiftingButtonType.TextButton(
                            text = notification.actionLabel,
                        ),
                        onClick = notification.onAction
                    )
                }
            }
        }
    }
}