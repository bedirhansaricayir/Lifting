package com.lifting.app.feature_tracker.presentation.calendar

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lifting.app.common.util.displayText
import com.lifting.app.common.util.toLocaleFormat
import com.lifting.app.feature_tracker.domain.model.ChartState
import com.lifting.app.feature_tracker.domain.use_case.GetAllAnalysisDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject


@HiltViewModel
class CalendarViewModel @Inject constructor(
    private val getAllChartData: GetAllAnalysisDataUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(CalendarUiState())
    val state: StateFlow<CalendarUiState> = _state.asStateFlow()

    private val currentDate: LocalDate = LocalDate.now()
    private val formattedDate: String = currentDate.toLocaleFormat()
    private val currDateStrFormat = formattedDate

    init {
        updateCurrentDateViewed(currDateStrFormat)
        updateCurrentMonthViewed("${currentDate.month.displayText(false)} ${currentDate.year}")
        getData()
    }

    fun updateCurrentDateViewed(date: String) {
        _state.value = _state.value.copy(currDateViewed = date)
    }

    fun updateCurrentMonthViewed(month : String) {
        _state.value = _state.value.copy(currMonthViewed = month)
    }

    fun isDateSelected(date : String) : Boolean {
        return date == _state.value.currDateViewed
    }


    fun resetToCurrentDate() {
        _state.value = _state.value.copy(
            currDateViewed = currDateStrFormat
        )
    }

    private fun getData() {
        viewModelScope.launch {
            getAllChartData.invoke().collect { response ->
                _state.value = _state.value.copy(chartState = response)
            }
        }
    }

    private fun getCurrentDateEvent(date: String): List<ChartState> {
        val currentDateEvent = mutableListOf<ChartState>()

        _state.value.chartState.forEach { data ->
            if (data.date.toLocaleFormat() == date) {
                currentDateEvent.add(data)
            }

        }
        return currentDateEvent
    }

    fun checkIfDateHasEvent(date: String): Int {
        return getCurrentDateEvent(date).size
    }
}