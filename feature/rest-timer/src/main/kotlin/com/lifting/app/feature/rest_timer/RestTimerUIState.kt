package com.lifting.app.feature.rest_timer

import com.lifting.app.core.base.viewmodel.State
import com.lifting.app.core.service.TimerState

/**
 * Created by bedirhansaricayir on 11.06.2025
 */

internal data class RestTimerUIState(
    val timerState: TimerState = TimerState.EXPIRED,
    val totalTime: Long? = null,
    val elapsedTime: Long? = null,
    val timeString: String? = null
) : State
