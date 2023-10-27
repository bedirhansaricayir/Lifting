package com.lifting.app.feature_tracker.presentation.calendar

import com.lifting.app.feature_tracker.domain.model.ChartState
import java.time.LocalDate

data class CalendarUiState(
    val currDateViewed : String = "",
    val currMonthViewed : String = "",
    val chartState: List<ChartState> = emptyList(),
    val localDate: LocalDate = LocalDate.now(),
    val selectedDayValue: ChartState? = null
)
