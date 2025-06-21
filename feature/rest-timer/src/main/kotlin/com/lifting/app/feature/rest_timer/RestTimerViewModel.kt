package com.lifting.app.feature.rest_timer

import androidx.lifecycle.viewModelScope
import com.lifting.app.core.base.viewmodel.BaseViewModel
import com.lifting.app.core.service.RestTimerRepository
import com.lifting.app.core.service.getFormattedStopWatchTime
import com.lifting.app.core.service.isRunning
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import javax.inject.Inject

/**
 * Created by bedirhansaricayir on 11.06.2025
 */
@HiltViewModel
internal class RestTimerViewModel @Inject constructor(
    private val restTimerRepository: RestTimerRepository
) : BaseViewModel<RestTimerUIState, RestTimerUIEvent, RestTimerUIEffect>() {

    override fun setInitialState(): RestTimerUIState = RestTimerUIState()

    override fun handleEvents(event: RestTimerUIEvent) {
        when (event) {
            RestTimerUIEvent.CancelTimer -> cancelTimer()
            RestTimerUIEvent.PauseTimer -> pauseTimer()
            RestTimerUIEvent.ResumeTimer -> resumeTimer()
            is RestTimerUIEvent.StartTimer -> startTimer(event.time)
        }
    }

    init {
        updateUIState()
    }

    private fun updateUIState() {
        combine(
            restTimerRepository.getTimerState(),
            restTimerRepository.getElapsedTimeMillis(),
            restTimerRepository.getTotalTimeMillis(),
            restTimerRepository.getElapsedTimeMillisEverySeconds()
        ) { timerState, elapsedTime, totalTime, elapsedTimeMillisEverySeconds ->
            updateState { currentState ->
                currentState.copy(
                    timerState = timerState,
                    totalTime = totalTime,
                    elapsedTime = if (timerState.isRunning()) elapsedTime else null,
                    timeString = if (timerState.isRunning()) getFormattedStopWatchTime(ms = elapsedTimeMillisEverySeconds, spaces = false) else null
                )
            }
        }.launchIn(viewModelScope)
    }

    private fun cancelTimer() = restTimerRepository.cancelTimer()
    private fun pauseTimer() = restTimerRepository.pauseTimer()
    private fun resumeTimer() = restTimerRepository.resumeTimer()
    private fun startTimer(time: Long) = restTimerRepository.startTimer(time)
}