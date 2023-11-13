package com.lifting.app.feature_notification.presentation

data class NotificationSettingsState(
    val notificationPermissionIsGranted: Boolean = false,
    val notificationIsExist: Boolean = false
)