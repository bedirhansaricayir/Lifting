package com.lifting.app.feature.calendar.components

import androidx.compose.runtime.Immutable
import java.time.DayOfWeek

@Immutable
internal data class CalendarInfo(
    val indexCount: Int,
    private val firstDayOfWeek: DayOfWeek? = null,
    private val outDateStyle: OutDateStyle? = null,
)