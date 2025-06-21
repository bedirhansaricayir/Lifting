package com.lifting.app.feature.calendar.components

import androidx.compose.runtime.Immutable
import java.io.Serializable
import java.time.LocalDate

/**
 * Created by bedirhansaricayir on 06.06.2025
 */

/**
 * Represents a day on the calendar.
 *
 * @param date the date for this day.
 * @param position the [DayPosition] for this day.
 */
@Immutable
data class CalendarDay(val date: LocalDate, val position: DayPosition) : Serializable