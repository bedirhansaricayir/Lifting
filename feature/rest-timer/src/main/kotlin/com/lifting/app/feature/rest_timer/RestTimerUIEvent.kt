package com.lifting.app.feature.rest_timer

import com.lifting.app.core.base.viewmodel.Event

/**
 * Created by bedirhansaricayir on 11.06.2025
 */
internal sealed interface RestTimerUIEvent : Event {
    data class StartTimer(val time: Long) : RestTimerUIEvent
    data object PauseTimer : RestTimerUIEvent
    data object ResumeTimer : RestTimerUIEvent
    data  object CancelTimer : RestTimerUIEvent

}