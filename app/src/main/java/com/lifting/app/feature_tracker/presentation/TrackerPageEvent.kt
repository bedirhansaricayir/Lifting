package com.lifting.app.feature_tracker.presentation

import com.lifting.app.feature_tracker.domain.model.ChartState
import com.lifting.app.feature_tracker.presentation.components.FilterChip
import com.lifting.app.feature_tracker.presentation.components.SortBy
import com.lifting.app.feature_tracker.presentation.components.TimeRange


sealed class TrackerPageEvent {

    data class OnSaveButtonClicked(val chartState: ChartState): TrackerPageEvent()

    data class OnTimeRangeClicked(val sortBy: SortBy, val timeRange: TimeRange): TrackerPageEvent()

    data class OnSortByClicked(val sortBy: SortBy, val timeRange: TimeRange): TrackerPageEvent()

    data class OnFilterChipClicked(val filterChipGroup: MutableList<FilterChip>): TrackerPageEvent()

    object UserViewedTheError: TrackerPageEvent()

    data class OnDialogUpdateButtonClicked(val chartState: ChartState): TrackerPageEvent()

    object SetFilterBottomSheetClicked : TrackerPageEvent()
    object SetFilterBottomSheetOnDismiss : TrackerPageEvent()

    object AddToChartBottomSheetClicked : TrackerPageEvent()
    object AddToChartBottomSheetOnDismiss : TrackerPageEvent()

}
