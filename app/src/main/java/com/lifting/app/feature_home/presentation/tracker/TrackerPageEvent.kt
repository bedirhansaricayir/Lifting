package com.lifting.app.feature_home.presentation.tracker

import com.lifting.app.feature_home.presentation.tracker.components.FilterChip
import com.lifting.app.feature_home.presentation.tracker.components.SortBy
import com.lifting.app.feature_home.presentation.tracker.components.TimeRange
import java.time.LocalDate


sealed class TrackerPageEvent {

    data class OnDialogButtonClicked(val localDate: LocalDate,val data: Float, val desc: String) : TrackerPageEvent()

    data class OnTimeRangeClicked(val sortBy: SortBy,val timeRange: TimeRange): TrackerPageEvent()

    data class OnSortByClicked(val sortBy: SortBy,val timeRange: TimeRange): TrackerPageEvent()

    data class OnFilterChipClicked(val filterChipGroup: MutableList<FilterChip>): TrackerPageEvent()
}
