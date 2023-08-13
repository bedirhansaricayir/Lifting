package com.lifting.app.feature_home.presentation.tracker

import com.lifting.app.feature_home.presentation.tracker.components.SortBy
import com.lifting.app.feature_home.presentation.tracker.components.TimeRange
import java.time.LocalDate
import java.util.Date


sealed class TrackerPageEvent {

    data class OnDialogButtonClicked(val localDate: LocalDate,val bw: Float, val cj: Float?, val snatch: Float?) : TrackerPageEvent()

    data class OnTimeRangeClicked(val timeRange: TimeRange): TrackerPageEvent()

    data class OnSortByClicked(val sortBy: SortBy): TrackerPageEvent()

}
