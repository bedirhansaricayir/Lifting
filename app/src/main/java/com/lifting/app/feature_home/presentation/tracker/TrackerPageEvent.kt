package com.lifting.app.feature_home.presentation.tracker

import com.lifting.app.feature_home.presentation.tracker.components.TimeRange
import java.time.LocalDate
import java.util.Date


sealed class TrackerPageEvent {

    data class OnDialogButtonClicked(val localDate: LocalDate,val bw: Float) : TrackerPageEvent()

    data class OnTimeRangeClicked(val timeRange: TimeRange): TrackerPageEvent()

}
