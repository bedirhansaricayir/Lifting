package com.lifting.app.core.ui.components.in_app_notification

import com.lifting.app.core.common.utils.generateUUID

/**
 * Created by bedirhansaricayir on 20.06.2025
 */

data class LiftingInAppNotificationData(
    val id: String = generateUUID(),
    val message: String,
    val duration: Long = 5000,
    val type: LiftingNotificationType = LiftingNotificationType.Default,
    val showTimer: Boolean = false,
    val actionLabel: String? = null,
    val onAction: (() -> Unit)? = null,
)