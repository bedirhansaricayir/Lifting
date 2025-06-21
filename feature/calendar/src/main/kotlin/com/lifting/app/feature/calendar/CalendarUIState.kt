package com.lifting.app.feature.calendar

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import com.lifting.app.core.base.viewmodel.State
import com.lifting.app.core.model.WorkoutWithExtraInfo
import java.time.LocalDate
import java.time.YearMonth

/**
 * Created by bedirhansaricayir on 06.06.2025
 */


@Immutable
internal data class CalendarUIState(
    val workoutDays: List<LocalDate> = listOf(),
    val workouts: List<WorkoutWithExtraInfo> = listOf(),
    val calendarConfig: CalendarConfig = CalendarConfig(),
    val selectedDay: LocalDate? = LocalDate.now(),
    val showActiveWorkoutDialog: Boolean = false
) : State

@Stable
internal data class CalendarConfig(
    val currentMonth: YearMonth = YearMonth.now(),
    val startMonth: YearMonth = currentMonth.minusMonths(50),
    val endMonth: YearMonth = currentMonth.plusMonths(50)
)