package com.lifting.app.feature_home.presentation.tracker

import com.lifting.app.feature_home.domain.model.ChartState
import com.lifting.app.feature_home.presentation.tracker.components.FilterChip
import com.lifting.app.feature_home.presentation.tracker.components.SortBy
import com.lifting.app.feature_home.presentation.tracker.components.TimeRange


sealed class TrackerPageEvent {

    data class OnSaveButtonClicked(val chartState: ChartState): TrackerPageEvent()

    data class OnTimeRangeClicked(val sortBy: SortBy,val timeRange: TimeRange): TrackerPageEvent()

    data class OnSortByClicked(val sortBy: SortBy,val timeRange: TimeRange): TrackerPageEvent()

    data class OnFilterChipClicked(val filterChipGroup: MutableList<FilterChip>): TrackerPageEvent()

    object UserViewedTheError: TrackerPageEvent()

    data class OnDialogUpdateButtonClicked(val chartState: ChartState): TrackerPageEvent()
}
