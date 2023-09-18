package com.lifting.app.feature_home.presentation.tracker

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lifting.app.feature_home.data.local.entity.AnalysisDataEntity
import com.lifting.app.feature_home.domain.model.AnalysisSortBy
import com.lifting.app.feature_home.domain.model.AnalysisTimeRange
import com.lifting.app.feature_home.domain.use_case.AddAnalysisDataUseCase
import com.lifting.app.feature_home.domain.use_case.GetAllAnalysisDataUseCase
import com.lifting.app.feature_home.domain.use_case.GetAnalysisDataUseCase
import com.lifting.app.feature_home.presentation.tracker.components.FilterChip
import com.lifting.app.feature_home.presentation.tracker.components.SortBy
import com.lifting.app.feature_home.presentation.tracker.components.TimeRange
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
) : ViewModel() {

    private val _state = MutableStateFlow(TrackerPageUiState())
    val state: StateFlow<TrackerPageUiState> = _state.asStateFlow()


    fun onEvent(event: TrackerPageEvent) {
        when (event) {
            is TrackerPageEvent.OnDialogButtonClicked -> {
                addAnalysisData(
                    AnalysisDataEntity(
                        date = event.localDate,
                        data = event.data,
                        desc = event.desc
                    )
                )
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
        }
    }

    init {
        getChartDataByFilter(getLocalDateNow().minusDays(TimeRange.SEVEN_DAYS.minusDay),getLocalDateNow())
    }


    private fun addAnalysisData(analysisDataEntity: AnalysisDataEntity) {
        viewModelScope.launch {
            addAnalysisDataUseCase.invoke(analysisDataEntity)
        }
    }

    private fun getChartData(sortBy: SortBy,timeRange: TimeRange) {
        when(sortBy) {
            SortBy.RECORD -> getChartDataByRecords(timeRange.minusDay.toInt())
            SortBy.DATE -> getChartDataByFilter(getLocalDateNow().minusDays(timeRange.minusDay),getLocalDateNow())
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

    private fun getLocalDateNow() : LocalDate = LocalDate.now()

}