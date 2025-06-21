package com.lifting.app.core.ui.components.in_app_notification

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

/**
 * Created by bedirhansaricayir on 20.06.2025
 */

interface NotificationManager {
    fun send(data: LiftingInAppNotificationData)
    fun dismiss(id: String)
    val currentNotification: Flow<LiftingInAppNotificationData?>
}

internal class DefaultNotificationManager : NotificationManager {
    private val _queue = MutableStateFlow<List<LiftingInAppNotificationData>>(emptyList())
    private val _current = MutableStateFlow<LiftingInAppNotificationData?>(null)
    override val currentNotification: StateFlow<LiftingInAppNotificationData?> = _current


    override fun send(data: LiftingInAppNotificationData) {
        _queue.update { it + data }
        if (_current.value == null) dequeue()
    }

    override fun dismiss(id: String) {
        if (_current.value?.id == id) {
            _current.value = null
        }
        _queue.update { it.filter { it.id != id } }
        dequeue()
    }

    private fun dequeue() {
        val next = _queue.value.firstOrNull() ?: return
        _queue.update { it.drop(1) }
        _current.value = next
    }
}

val LocalNotificationManager = staticCompositionLocalOf<NotificationManager> {
    error("No NotificationManager provided")
}

@Composable
fun NotificationManagerHost(
    manager: NotificationManager = remember { DefaultNotificationManager() },
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(LocalNotificationManager provides manager) {
        Box {
            content()

            val notification by manager.currentNotification.collectAsState(null)
            if (notification != null) {
                LiftingInAppNotification(
                    notification = notification!!,
                    onDismiss = { manager.dismiss(it) }
                )
            }
        }
    }
}
