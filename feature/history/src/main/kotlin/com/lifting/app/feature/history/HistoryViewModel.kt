package com.lifting.app.feature.history

import androidx.lifecycle.viewModelScope
import com.lifting.app.core.base.viewmodel.BaseViewModel
import com.lifting.app.core.common.extensions.toEpochMillis
import com.lifting.app.core.data.repository.workouts.WorkoutsRepository
import com.lifting.app.core.model.CountWithDate
import com.lifting.app.core.model.WorkoutWithExtraInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import java.time.YearMonth
import java.time.temporal.TemporalAdjusters
import javax.inject.Inject

/**
 * Created by bedirhansaricayir on 08.03.2025
 */

@HiltViewModel
internal class HistoryViewModel @Inject constructor(
    private val workoutsRepository: WorkoutsRepository,
) : BaseViewModel<HistoryUIState, HistoryUIEvent, HistoryUIEffect>() {

    override fun setInitialState(): HistoryUIState = HistoryUIState()

    override fun handleEvents(event: HistoryUIEvent) {
        when (event) {
            HistoryUIEvent.OnCalendarClicked -> navigateToCalendar()
            is HistoryUIEvent.OnWorkoutClicked -> navigateToWorkoutDetail(event.workoutId)
        }
    }

    init {
        updateUIState()
    }

    private fun updateUIState() {
        viewModelScope.launch {
            workoutsRepository.getWorkoutsWithExtraInfo().collect { data ->
                val mappedData = mapData(data)
                updateState { currentState ->
                    currentState.copy(
                        data = mappedData,
                    )
                }
            }
        }
    }

    private suspend fun mapData(list: List<WorkoutWithExtraInfo>) = buildList {
        list.indices.forEach { i ->
            val after = list[i]
            val before = list.getOrNull(i - 1)

            val afterDate =
                after.workout?.startAt?.toLocalDate()?.with(TemporalAdjusters.firstDayOfMonth())
            val beforeDate =
                before?.workout?.startAt?.toLocalDate()?.with(TemporalAdjusters.firstDayOfMonth())

            if (after.workout?.startAt != null && afterDate != null && beforeDate != afterDate) {
                val year = after.workout!!.startAt!!.year
                val month = after.workout!!.startAt!!.month
                val count = workoutsRepository.getWorkoutsCountOnDateRange(
                    dateStart = YearMonth.of(year, month).atDay(1),
                    dateEnd = YearMonth.of(year, month).atEndOfMonth()
                ).firstOrNull() ?: 0

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
}