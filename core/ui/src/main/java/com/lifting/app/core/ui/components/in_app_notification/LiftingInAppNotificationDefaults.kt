package com.lifting.app.core.ui.components.in_app_notification

import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.lifting.app.core.designsystem.LiftingTheme

/**
 * Created by bedirhansaricayir on 20.06.2025
 */

internal object LiftingInAppNotificationDefaults {
    const val ANIMATION_SPEC_MILLIS = 500
    val animationSpec
        get() = tween<Float>(durationMillis = ANIMATION_SPEC_MILLIS)

    @Composable
    fun containerColor(type: LiftingNotificationType) = when (type) {
        LiftingNotificationType.Info -> Color(0xFF2196F3)
        LiftingNotificationType.Success -> Color(0xFF4CAF50)
        LiftingNotificationType.Error -> Color(0xFFF44336)
        LiftingNotificationType.Default -> LiftingTheme.colors.surface
    }

    @Composable
    fun textColor(type: LiftingNotificationType) = when (type) {
        LiftingNotificationType.Info -> Color.White
        LiftingNotificationType.Success -> Color.White
        LiftingNotificationType.Error -> Color.White
        LiftingNotificationType.Default -> LiftingTheme.colors.onBackground
    }
}