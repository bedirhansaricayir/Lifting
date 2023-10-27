package com.lifting.app.feature_tracker.presentation.calendar

import java.time.LocalDate

sealed class CalendarEvent {
    data class OnDayClicked(val localDate : LocalDate) : CalendarEvent()
}
