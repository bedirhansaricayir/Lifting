package com.lifting.app.feature.calendar

import com.lifting.app.core.base.viewmodel.Event
import java.time.LocalDate


/**
 * Created by bedirhansaricayir on 05.06.2025
 */

internal sealed interface CalendarUIEvent : Event {
    data object OnBackIconClick : CalendarUIEvent
    data class OnDayClicked(val date: LocalDate) : CalendarUIEvent
    data class OnWorkoutClicked(val workoutId: String) : CalendarUIEvent
    data object OnCreateNewWorkoutClicked : CalendarUIEvent
    data object OnDialogConfirmClicked : CalendarUIEvent
    data object OnDialogDismissClicked : CalendarUIEvent
}