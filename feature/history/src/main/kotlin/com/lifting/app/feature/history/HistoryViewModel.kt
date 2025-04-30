package com.lifting.app.feature.history

import androidx.lifecycle.viewModelScope
import com.lifting.app.core.base.viewmodel.BaseViewModel
import com.lifting.app.core.common.extensions.getIntValues
import com.lifting.app.core.common.extensions.toEpochMillis
import com.lifting.app.core.data.repository.workouts.WorkoutsRepository
import com.lifting.app.core.model.CountWithDate
import com.lifting.app.core.model.WorkoutWithExtraInfo
import com.lifting.app.feature.history.components.WorkoutsDateRangeType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.Month
import java.time.Year
import java.time.YearMonth
import java.time.temporal.TemporalAdjusters
import javax.inject.Inject

/**
 * Created by bedirhansaricayir on 08.03.2025
 */

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val workoutsRepository: WorkoutsRepository,
) : BaseViewModel<HistoryUIState, HistoryUIEvent, HistoryUIEffect>() {
    override fun setInitialState(): HistoryUIState = HistoryUIState.Loading

    private val dateRangeType = MutableStateFlow<WorkoutsDateRangeType>(WorkoutsDateRangeType.All)

    private val dateStart = dateRangeType.map { rangeType ->
        when (rangeType) {
            is WorkoutsDateRangeType.Day -> LocalDate.of(
                rangeType.year,
                rangeType.month,
                rangeType.day
            )

            is WorkoutsDateRangeType.Month -> LocalDate.of(rangeType.year, rangeType.month, 1)
            is WorkoutsDateRangeType.Year -> LocalDate.of(rangeType.year, 1, 1)
            else -> null
        }
    }.stateIn(viewModelScope, SharingStarted.Lazily, null)

    private val dateEnd = dateRangeType.map { rangeType ->
        when (rangeType) {
            is WorkoutsDateRangeType.Day -> LocalDate.of(
                rangeType.year,
                rangeType.month,
                rangeType.day
            )

            is WorkoutsDateRangeType.Month -> YearMonth.of(rangeType.year, rangeType.month)
                .atEndOfMonth()

            is WorkoutsDateRangeType.Year -> Year.of(rangeType.year).atMonth(Month.DECEMBER)
                .atEndOfMonth()

            else -> null
        }
    }.stateIn(viewModelScope, SharingStarted.Lazily, null)


    override fun handleEvents(event: HistoryUIEvent) {
        when (event) {
            HistoryUIEvent.OnCalendarClicked -> navigateToCalendar()
            is HistoryUIEvent.OnWorkoutClicked -> navigateToWorkoutDetail(event.workoutId)
            else -> Unit
        }
    }

    init {
        setUiState()
    }

    private fun setUiState() {
        viewModelScope.launch {
            combine(
                dateStart,
                dateEnd,
                dateRangeType,
            ) { start, end, rangeType ->
                Triple(start, end, rangeType)
            }.flatMapLatest { (start, end, rangeType) ->
                workoutsRepository.getWorkoutsWithExtraInfo(start, end)
                    .map { data -> data to rangeType }
            }.collect { (data, rangeType) ->
                val mappedData = mapData(data)
                runCatching {
                    updateState { currentState ->
                        (currentState as HistoryUIState.Success).copy(
                            data = mappedData,
                            dateRangeType = rangeType
                        )
                    }
                }.getOrElse { throwable ->
                    if (throwable is ClassCastException) {
                        setState(HistoryUIState.Success(mappedData, rangeType))
                    } else {
                        setState(HistoryUIState.Error)
                    }
                }
            }

        }
    }

    private suspend fun mapData(list: List<WorkoutWithExtraInfo>) = buildList {
        list.indices.forEach { i ->
            val after = list[i]
            val before = list.getOrNull(i - 1)
            val start = dateStart.value
            val end = dateEnd.value

            val afterDate =
                after.workout?.startAt?.toLocalDate()?.with(TemporalAdjusters.firstDayOfMonth())
            val beforeDate =
                before?.workout?.startAt?.toLocalDate()?.with(TemporalAdjusters.firstDayOfMonth())

            if (after.workout?.startAt != null && afterDate != null && beforeDate != afterDate) {
                val count = if (
                    dateRangeType != WorkoutsDateRangeType.Year::class &&
                    dateRangeType != WorkoutsDateRangeType.All::class &&
                    start != null && end != null
                ) {
                    workoutsRepository.getWorkoutsCountOnDateRange(start, end).firstOrNull() ?: 0L
                } else {
                    val year = after.workout!!.startAt!!.year
                    val month = after.workout!!.startAt!!.month
                    workoutsRepository.getWorkoutsCountOnDateRange(
                        dateStart = YearMonth.of(year, month).atDay(1),
                        dateEnd = YearMonth.of(year, month).atEndOfMonth()
                    ).firstOrNull() ?: 0
                }
                add(CountWithDate(count = count, date = afterDate.toEpochMillis()))
            }
            add(after)
        }
    }

    private fun navigateToCalendar() {
        setEffect(HistoryUIEffect.NavigateToCalendar)
    }

    private fun navigateToWorkoutDetail(workoutId: String) {
        setEffect(HistoryUIEffect.NavigateToWorkoutDetail(workoutId))
    }

    fun setSelectedDay(day: String) {
        val actualDay = day.getIntValues()
        dateRangeType.value = WorkoutsDateRangeType.Day(actualDay[0], actualDay[1], actualDay[2])
    }

    fun setSelectedMonth(month: String) {
        val actualMonth = month.getIntValues()
        dateRangeType.value = WorkoutsDateRangeType.Month(actualMonth[0], actualMonth[1])
    }

    fun setSelectedYear(year: Int) {
        dateRangeType.value = WorkoutsDateRangeType.Year(year)
    }
}