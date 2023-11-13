package com.lifting.app.feature_notification.domain

import com.lifting.app.feature_notification.domain.model.AlarmTime
interface AlarmScheduler {
    fun schedule(alarmTime: AlarmTime)
    fun cancel ()
}