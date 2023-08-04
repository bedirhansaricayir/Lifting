package com.lifting.app.feature_home.presentation.tracker

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lifting.app.common.util.Resource
import com.lifting.app.feature_home.data.local.entity.AnalysisDataEntity
import com.lifting.app.feature_home.domain.model.AnalysisTimeRange
import com.lifting.app.feature_home.domain.model.ChartState
import com.lifting.app.feature_home.domain.use_case.AddAnalysisDataUseCase
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
      private val getAnalysisDataUseCase: GetAnalysisDataUseCase,
      private val addAnalysisDataUseCase: AddAnalysisDataUseCase,
): ViewModel() {

    private val _state = MutableStateFlow(TrackerPageUiState())
    val state: StateFlow<TrackerPageUiState> = _state.asStateFlow()


    fun onEvent(event:TrackerPageEvent) {
        when(event) {
            is TrackerPageEvent.OnDialogButtonClicked -> {
                addAnalysisRepository(AnalysisDataEntity(date = event.localDate, bodyweight = event.bw))
            }
            is TrackerPageEvent.OnTimeRangeClicked -> {
                getChartData(timeRange = event.timeRange)
            }
        }
    }
    init {
        getAnalysisData(LocalDate.now(), LocalDate.now())
    }


     private fun addAnalysisRepository(analysisDataEntity: AnalysisDataEntity){
         viewModelScope.launch {
             addAnalysisDataUseCase.invoke(analysisDataEntity)
         }
     }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getAnalysisData(currentDate: LocalDate, endDate: LocalDate) {
        viewModelScope.launch {
            getAnalysisDataUseCase.invoke(currentDate, endDate).collect { response ->
                _state.value = _state.value.copy(chartState = response, timeRange = AnalysisTimeRange.TIMERANGE_7DAYS)
               /* when(response) {
                    is Resource.Loading -> {
                        Log.d("geldi","loading")

                    }
                    is Resource.Success -> {
                        _state.value = _state.value.copy(timeRange = AnalysisTimeRange.TIMERANGE_7DAYS, chartState = response.data ?: emptyList())

                    }
                    is Resource.Error -> {

                    }
                }*/

            }
        }

    }

    private fun getChartData(timeRange: TimeRange) {
       when(timeRange) {
           TimeRange.SEVEN_DAYS -> _state.value = _state.value.copy(timeRange = AnalysisTimeRange.TIMERANGE_7DAYS)
           TimeRange.THIRTY_DAYS -> _state.value = _state.value.copy(timeRange = AnalysisTimeRange.TIMERANGE_30DAYS)
           TimeRange.SIXTY_DAYS -> _state.value = _state.value.copy(timeRange = AnalysisTimeRange.TIMERANGE_60DAYS)
           TimeRange.NINETY_DAYS -> _state.value = _state.value.copy(timeRange =  AnalysisTimeRange.TIMERANGE_90DAYS)
           TimeRange.ONE_YEAR ->  _state.value = _state.value.copy(timeRange =  AnalysisTimeRange.TIMERANGE_1YEAR)
       }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun chartState(): List<ChartState>{
    return listOf(
        ChartState(
            LocalDate.now(),
            14f
        ),
        ChartState(
            LocalDate.now(),
            15f
        ),ChartState(
            LocalDate.now(),
            16f
        ),
        ChartState(
            LocalDate.now(),
            18f
        )
    )
}
@RequiresApi(Build.VERSION_CODES.O)
fun chartState2(): List<ChartState>{
    return listOf(
        ChartState(
            LocalDate.now(),
            23f
        ),
        ChartState(
            LocalDate.now(),
            24f
        ),ChartState(
            LocalDate.now(),
            22f
        ),
        ChartState(
            LocalDate.now(),
            25f
        )
    )
}