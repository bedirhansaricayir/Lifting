package com.lifting.app.feature.rest_timer

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalWindowInfo
import com.lifting.app.feature.rest_timer.components.TimerCircle

/**
 * Created by bedirhansaricayir on 11.06.2025
 */
@Composable
internal fun RestTimerScreen(
    state: RestTimerUIState,
    onEvent: (RestTimerUIEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    RestTimerScreenContent(
        state = state,
        onEvent = onEvent,
        modifier = modifier
    )
}

@Composable
private fun RestTimerScreenContent(
    state: RestTimerUIState,
    onEvent: (RestTimerUIEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    val windowInfo = LocalWindowInfo.current
    val screenWidthDp = windowInfo.containerSize.width
    val screenHeightDp = windowInfo.containerSize.height

    Column(modifier = Modifier.fillMaxWidth()) {
        Box(modifier = Modifier.fillMaxWidth()) {
            TimerCircle(
                modifier = modifier,
                screenWidthDp = screenWidthDp,
                screenHeightDp = screenHeightDp,
                time = state.timeString ?: "",
                timerState = state.timerState,
                elapsedTime = state.elapsedTime ?: 1L,
                totalTime = state.totalTime ?: 1L,
                onClickResume = { onEvent(RestTimerUIEvent.ResumeTimer) },
                onClickPause = { onEvent(RestTimerUIEvent.PauseTimer) },
                onClickCancel = { onEvent(RestTimerUIEvent.CancelTimer) },
                onClickStart = { onEvent(RestTimerUIEvent.StartTimer(it)) },
            )
        }
    }
}