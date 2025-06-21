package com.lifting.app.feature.history

import com.lifting.app.core.base.viewmodel.Event

/**
 * Created by bedirhansaricayir on 08.03.2025
 */

internal sealed interface HistoryUIEvent : Event {
    data object OnCalendarClicked : HistoryUIEvent
    data class OnWorkoutClicked(val workoutId: String) : HistoryUIEvent
}