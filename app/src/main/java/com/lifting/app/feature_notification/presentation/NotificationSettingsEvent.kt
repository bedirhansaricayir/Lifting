package com.lifting.app.feature_notification.presentation

import com.lifting.app.feature_notification.domain.model.AlarmTime

sealed class NotificationSettingsEvent {
    data class OnAlarmScheduled(val alarmTime: AlarmTime): NotificationSettingsEvent()
    object OnPermissionAllowed: NotificationSettingsEvent()
    object OnNotificationSwitchStateChanged: NotificationSettingsEvent()
}
