package com.lifting.app.feature_home.presentation.tracker

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lifting.app.feature_home.data.local.entity.AnalysisDataEntity
import com.lifting.app.feature_home.domain.model.AnalysisTimeRange
import com.lifting.app.feature_home.domain.use_case.AddAnalysisDataUseCase
import com.lifting.app.feature_home.domain.use_case.GetAllAnalysisDataUseCase
import com.lifting.app.feature_home.domain.use_case.GetAnalysisDataUseCase
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
                        bodyweight = event.bw
                    )
                )
            }

            is TrackerPageEvent.OnTimeRangeClicked -> {
                setChipSelection(timeRange = event.timeRange)
                getChartDataByFilter(getLocalDateNow().minusDays(event.timeRange.minusDay),getLocalDateNow())
            }
        }
    }

    init {
        getChartDataByFilter(getLocalDateNow().minusDays(TimeRange.THIRTY_DAYS.minusDay),getLocalDateNow())
    }


    private fun addAnalysisData(analysisDataEntity: AnalysisDataEntity) {
        viewModelScope.launch {
            addAnalysisDataUseCase.invoke(analysisDataEntity)
        }
    }

    private fun getAllChartData() {
        viewModelScope.launch {
            getAllAnalysisDataUseCase.invoke().collect { response ->
                _state.value = _state.value.copy(
                    chartState = response
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

    private fun setChipSelection(timeRange: TimeRange) {
        when (timeRange) {
            TimeRange.SEVEN_DAYS -> _state.value = _state.value.copy(timeRange = AnalysisTimeRange.TIMERANGE_7DAYS)
            TimeRange.THIRTY_DAYS -> _state.value = _state.value.copy(timeRange = AnalysisTimeRange.TIMERANGE_30DAYS)
            TimeRange.SIXTY_DAYS -> _state.value = _state.value.copy(timeRange = AnalysisTimeRange.TIMERANGE_60DAYS)
            TimeRange.NINETY_DAYS -> _state.value = _state.value.copy(timeRange = AnalysisTimeRange.TIMERANGE_90DAYS)
            TimeRange.ONE_YEAR -> _state.value = _state.value.copy(timeRange = AnalysisTimeRange.TIMERANGE_1YEAR)
        }
    }

    private fun getLocalDateNow() : LocalDate = LocalDate.now()

}