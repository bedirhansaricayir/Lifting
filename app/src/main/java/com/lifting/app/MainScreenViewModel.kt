package com.lifting.app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lifting.app.core.common.utils.Constants.NONE_WORKOUT_ID
import com.lifting.app.core.datastore.PreferencesStorage
import com.lifting.app.core.model.AppLanguage
import com.lifting.app.core.model.AppTheme
import com.lifting.app.core.service.RestTimerRepository
import com.lifting.app.core.service.TimerState
import com.lifting.app.core.service.getFormattedStopWatchTime
import com.lifting.app.core.service.isRunning
import com.lifting.app.core.ui.common.AppSettings
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by bedirhansaricayir on 06.05.2025
 */

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    restTimerRepository: RestTimerRepository,
    private val preferencesStorage: PreferencesStorage
) : ViewModel() {

    val serviceState: StateFlow<ServiceState> =
        combine(
            restTimerRepository.getTimerState(),
            restTimerRepository.getElapsedTimeMillis(),
            restTimerRepository.getTotalTimeMillis(),
            restTimerRepository.getElapsedTimeMillisEverySeconds()
        ) { timerState, elapsedTime, totalTime, elapsedTimeMillisEverySeconds ->
            ServiceState(
                timerState = timerState,
                elapsedTime = if (timerState.isRunning()) elapsedTime else null,
                totalTime = totalTime,
                timeString = if (timerState.isRunning()) getFormattedStopWatchTime(ms = elapsedTimeMillisEverySeconds, spaces = false) else null
            )
        }.stateIn(viewModelScope, SharingStarted.Eagerly, ServiceState())

    val mainScreenState: StateFlow<MainScreenState> =
        combine(
            preferencesStorage.activeWorkoutId,
            preferencesStorage.appTheme,
            preferencesStorage.weightUnit,
            preferencesStorage.distanceUnit,
            preferencesStorage.appLanguage
        ) { currentWorkoutId, appTheme, weightUnit, distanceUnit, appLanguage ->
            MainScreenState(
                shouldKeepOnScreen = false,
                currentWorkoutId = currentWorkoutId,
                appTheme = appTheme,
                appSettings = AppSettings(
                    weightUnit = weightUnit,
                    distanceUnit = distanceUnit
                )
            )
        }.stateIn(viewModelScope, SharingStarted.Eagerly, MainScreenState())

    fun updateAppLanguage(appLanguage: AppLanguage) {
        viewModelScope.launch {
            preferencesStorage.setAppLanguage(appLanguage)
        }
    }
}

data class MainScreenState(
    val shouldKeepOnScreen: Boolean = true,
    val currentWorkoutId: String = NONE_WORKOUT_ID,
    val appTheme: AppTheme = AppTheme.System,
    val appSettings: AppSettings = AppSettings.defValues()
)

data class ServiceState(
    val timerState: TimerState = TimerState.EXPIRED,
    val elapsedTime: Long? = null,
    val totalTime: Long? = null,
    val timeString: String? = null
)