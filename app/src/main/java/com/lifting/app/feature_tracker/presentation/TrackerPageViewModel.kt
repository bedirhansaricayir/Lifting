package com.lifting.app.feature_tracker.presentation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lifting.app.feature_tracker.domain.model.AnalysisSortBy
import com.lifting.app.feature_tracker.domain.model.AnalysisTimeRange
import com.lifting.app.feature_tracker.domain.model.ChartState
import com.lifting.app.feature_tracker.domain.use_case.AddAnalysisDataUseCase
import com.lifting.app.feature_tracker.domain.use_case.CheckExistSameDateUseCase
import com.lifting.app.feature_tracker.domain.use_case.GetAllAnalysisDataUseCase
import com.lifting.app.feature_tracker.domain.use_case.GetAnalysisDataUseCase
import com.lifting.app.feature_tracker.presentation.components.FilterChip
import com.lifting.app.feature_tracker.presentation.components.SortBy
import com.lifting.app.feature_tracker.presentation.components.TimeRange
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class TrackerPageViewModel @Inject constructor(
    private val getAllAnalysisDataUseCase: GetAllAnalysisDataUseCase,
    private val getAnalysisDataUseCase: GetAnalysisDataUseCase,
    private val addAnalysisDataUseCase: AddAnalysisDataUseCase,
    private val checkExistSameDateUseCase: CheckExistSameDateUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(TrackerPageUiState())
    val state: StateFlow<TrackerPageUiState> = _state.asStateFlow()

    private val currentDate: LocalDate = LocalDate.now()

    fun onEvent(event: TrackerPageEvent) {
        when (event) {
            is TrackerPageEvent.OnSaveButtonClicked -> {
               val isExist = checkExist(event.chartState.date)
                if (isExist?.date != event.chartState.date) addToChartData(event.chartState)
                else setExistErrorState(true)
            }

            is TrackerPageEvent.OnDialogUpdateButtonClicked -> {
                addToChartData(event.chartState)
            }

            is TrackerPageEvent.OnTimeRangeClicked -> {
                setChipSelection(timeRange = event.timeRange)
                getChartData(event.sortBy,event.timeRange)
            }

            is TrackerPageEvent.OnSortByClicked -> {
                getSortBy(event.sortBy)
                getChartData(event.sortBy,event.timeRange)
            }

            is TrackerPageEvent.OnFilterChipClicked -> {
                setFilterChipSelection(event.filterChipGroup)
            }

            is TrackerPageEvent.UserViewedTheError -> {
                setExistErrorState(false)
            }
            is TrackerPageEvent.SetFilterBottomSheetClicked -> {
                setFilterBottomSheet(true)
            }
            is TrackerPageEvent.SetFilterBottomSheetOnDismiss -> {
                setFilterBottomSheet(false)
            }
            is TrackerPageEvent.AddToChartBottomSheetClicked -> {
                setAddToChartBottomSheet(true)
            }
            is TrackerPageEvent.AddToChartBottomSheetOnDismiss -> {
                setAddToChartBottomSheet(false)
            }

        }
    }

    init {
        getChartDataByFilter(currentDate.minusDays(TimeRange.SEVEN_DAYS.minusDay),currentDate)
    }


    private fun addToChartData(chartState: ChartState) {
        viewModelScope.launch {
            addAnalysisDataUseCase.invoke(chartState)
        }
    }

    private fun checkExist(selectedDate: LocalDate): ChartState? {
        return checkExistSameDateUseCase.invoke(selectedDate)
    }

    private fun getChartData(sortBy: SortBy, timeRange: TimeRange) {
        when(sortBy) {
            SortBy.RECORD -> getChartDataByRecords(timeRange.minusDay.toInt())
            SortBy.DATE -> getChartDataByFilter(currentDate.minusDays(timeRange.minusDay),currentDate)
        }
    }
    private fun getChartDataByRecords(takeLast: Int) {
        viewModelScope.launch {
            getAllAnalysisDataUseCase.invoke().collect { response ->
                _state.value = _state.value.copy(
                    chartState = response.takeLast(takeLast)
                )
            }
        }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun getChartDataByFilter(startDate: LocalDate, endDate: LocalDate) {
        viewModelScope.launch {
            getAnalysisDataUseCase.invoke(startDate, endDate).collect { response ->
                _state.value = _state.value.copy(
                    chartState = response
                )
            }
        }
    }

    private fun getSortBy(sortBy: SortBy) {
        when(sortBy){
            SortBy.DATE -> _state.value = _state.value.copy(sortBy = AnalysisSortBy.DATE)
            SortBy.RECORD -> _state.value = _state.value.copy(sortBy = AnalysisSortBy.RECORD)
        }
    }
    private fun setChipSelection(timeRange: TimeRange) {
        when (timeRange) {
            TimeRange.SEVEN_DAYS -> _state.value = _state.value.copy(timeRange = AnalysisTimeRange.TIMERANGE_7DAYS)
            TimeRange.THIRTY_DAYS -> _state.value = _state.value.copy(timeRange = AnalysisTimeRange.TIMERANGE_30DAYS)
            TimeRange.SIXTY_DAYS -> _state.value = _state.value.copy(timeRange = AnalysisTimeRange.TIMERANGE_60DAYS)
            TimeRange.NINETY_DAYS -> _state.value = _state.value.copy(timeRange = AnalysisTimeRange.TIMERANGE_90DAYS)
            TimeRange.ONE_YEAR -> _state.value = _state.value.copy(timeRange = AnalysisTimeRange.TIMERANGE_1YEAR)
        }
    }

    private fun setFilterChipSelection(filterChip: MutableList<FilterChip>) {
        _state.value.selectedFilterChip.clear()
        val updatedSelectedChips = _state.value.selectedFilterChip.toMutableStateList()

        filterChip.forEach { selectedChip ->
            if (selectedChip !in updatedSelectedChips) {
                updatedSelectedChips.add(selectedChip)
            }
        }

        _state.value = _state.value.copy(selectedFilterChip = updatedSelectedChips)
    }

    private fun setExistErrorState(boolean: Boolean) {
        _state.value = _state.value.copy(
            isExistSameDateError = boolean
        )
    }

    private fun setFilterBottomSheet(boolean: Boolean) {
        _state.value = _state.value.copy(
            setFilterBottomSheet = boolean
        )
    }

    private fun setAddToChartBottomSheet(boolean: Boolean) {
        _state.value = _state.value.copy(
            addToChartBottomSheet = boolean
        )
    }

}