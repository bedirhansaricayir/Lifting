package com.lifting.app.feature_notification.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lifting.app.feature_home.data.local.datastore.DataStoreRepository
import com.lifting.app.feature_notification.domain.repository.AlarmScheduler
import com.lifting.app.feature_notification.domain.model.AlarmTime
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationSettingsViewModel @Inject constructor(
    private val alarmScheduler: AlarmScheduler,
    private val dataStore: DataStoreRepository
) : ViewModel() {

    private val _state = MutableStateFlow(NotificationSettingsState())
    val state: StateFlow<NotificationSettingsState> = _state.asStateFlow()

    fun onEvent(event: NotificationSettingsEvent) {
        when(event) {
            is NotificationSettingsEvent.OnAlarmScheduled -> {
                schedule(event.alarmTime)
            }
            is NotificationSettingsEvent.OnPermissionAllowed -> {
                saveNotificationPermission()
                isExistNotificationPermission()
            }
            is NotificationSettingsEvent.OnNotificationSwitchStateChanged -> {
                if (_state.value.notificationIsExist) {
                    cancel()
                }
                setNotificationState(!_state.value.notificationIsExist)

            }

        }
    }

    init {
        isExistNotificationPermission()
        isExistNotification()
    }

    private fun schedule(alarmTime: AlarmTime) {
        alarmScheduler.schedule(alarmTime)
    }

    private fun cancel() {
        alarmScheduler.cancel()
    }

    private fun saveNotificationPermission() {
        viewModelScope.launch(Dispatchers.IO) {
            dataStore.saveNotificationPermission(true)
        }
    }

    private fun isExistNotificationPermission() {
        viewModelScope.launch {
            dataStore.readNotificationPermissionState().collect { isGranted ->
                _state.value = _state.value.copy(
                    notificationPermissionIsGranted = isGranted
                )
            }
        }
    }

    private fun setNotificationState(boolean: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStore.saveNotificationState(boolean)
        }
    }

    private fun isExistNotification() {
        viewModelScope.launch {
            dataStore.readNotificationState().collect { isExist ->
                _state.value = _state.value.copy(
                    notificationIsExist = isExist
                )
            }
        }
    }
}