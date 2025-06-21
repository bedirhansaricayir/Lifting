package com.lifting.app.feature.exercise_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.lifting.app.core.base.viewmodel.BaseViewModel
import com.lifting.app.core.common.extensions.toEpochMillis
import com.lifting.app.core.data.repository.exercises.ExercisesRepository
import com.lifting.app.core.model.Exercise
import com.lifting.app.core.model.LogEntriesWithWorkout
import com.lifting.app.core.model.Selectable
import com.lifting.app.core.model.calculateTotalVolume
import com.lifting.app.core.navigation.screens.LiftingScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.update
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

/**
 * Created by bedirhansaricayir on 02.03.2025
 */

@HiltViewModel
internal class ExerciseDetailViewModel @Inject constructor(
    private val exercisesRepository: ExercisesRepository,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<ExerciseDetailUIState, ExerciseDetailUIEvent, ExerciseDetailUIEffect>() {
    private val exerciseId = savedStateHandle.toRoute<LiftingScreen.ExerciseDetail>().exerciseIdKey
    private val selectedChartPeriod = MutableStateFlow<Map<ChartType, ChartPeriod>>(ChartPeriod())

    override fun setInitialState(): ExerciseDetailUIState = ExerciseDetailUIState()

    override fun handleEvents(event: ExerciseDetailUIEvent) {
        when (event) {
            is ExerciseDetailUIEvent.OnPeriodMenuClick -> updateDropdownVisibility(event.chartType)
            is ExerciseDetailUIEvent.OnDropdownDismissed -> updateDropdownVisibility(event.chartType)
            is ExerciseDetailUIEvent.OnPeriodItemSelected -> updateSelectedPeriod(
                event.chartType,
                event.period
            )

            ExerciseDetailUIEvent.OnBackClicked -> popBackStack()
        }
    }

    init {
        updateUIState()
    }

    private fun updateUIState() {
        val exerciseFlow: Flow<Exercise> = exercisesRepository.getExercise(exerciseId)
        val logEntriesFlow: Flow<List<LogEntriesWithWorkout>> =
            exercisesRepository.getVisibleLogEntries(exerciseId)

        combine(
            exerciseFlow,
            logEntriesFlow,
            selectedChartPeriod
        ) { exercise, logEntries, selectedPeriod ->
            updateState { currentState ->
                currentState.copy(
                    exercise = exercise,
                    logEntries = logEntries,
                    chartData = logEntries.mapToChartData(selectedPeriod),
                    periods = updatePeriodsMapBySelectedPeriod(selectedPeriod)
                )
            }
        }.flowOn(Dispatchers.Default)
            .launchIn(viewModelScope)

    }

    private fun List<LogEntriesWithWorkout>.mapToChartData(
        period: Map<ChartType, ChartPeriod>
    ): Map<ChartType, Pair<ChartPeriod, List<ChartData>>> {
        val now = LocalDateTime.now()

        fun getStartDate(chartType: ChartType): LocalDateTime {
            return when (period[chartType] ?: ChartPeriod.WEEKLY) {
                ChartPeriod.WEEKLY -> now.minusWeeks(1)
                ChartPeriod.MONTHLY -> now.minusMonths(1)
                ChartPeriod.YEARLY -> now.minusYears(1)
            }
        }

        fun filterEntries(chartType: ChartType): List<LogEntriesWithWorkout> {
            val startDate = getStartDate(chartType)
            return this.filter { workout ->
                val date =
                    workout.workout.createdAt ?: workout.workout.updatedAt ?: return@filter false
                date.isAfter(startDate)
            }
        }

        fun buildChartData(chartType: ChartType): List<ChartData> {
            val filtered = filterEntries(chartType)

            val entriesGroup = filtered.sortedBy { it.workout.startAt?.toEpochMillis() }
                .groupBy { it.workout.createdAt?.toLocalDate() }
            
            return entriesGroup.map { (date, logs) ->
                val label = date?.format(DateTimeFormatter.ofPattern("MMM d")) ?: ""
                val entries = logs.flatMap { it.logEntries }

                val value = when (chartType) {
                    ChartType.HEAVIEST_WEIGHT -> entries.maxOfOrNull { (it.weight ?: 0.0) } ?: 0.0
                    ChartType.TOTAL_VOLUME -> entries.calculateTotalVolume()
                    ChartType.MAX_REPS -> entries.maxOfOrNull { it.reps ?: 0 }?.toDouble() ?: 0.0
                }

                ChartData(label = label, value = value)
            }
        }

        return ChartType().associateWith { chartType ->
            getSelectedPeriod(chartType) to buildChartData(chartType)
        }
    }

    private fun updateDropdownVisibility(chartType: ChartType) = updateState { currentState ->
        val currentMap = getCurrentState().isDropdownVisible
        val updatedMap = currentMap.toMutableMap().apply {
            this[chartType] = currentMap[chartType] != true
        }
        currentState.copy(
            isDropdownVisible = updatedMap
        )
    }

    private fun updateSelectedPeriod(type: ChartType, period: ChartPeriod) {
        selectedChartPeriod.update { value ->
            value.toMutableMap().apply {
                this[type] = period
            }
        }

        updateDropdownVisibility(type)
    }

    private fun getSelectedPeriod(type: ChartType) = selectedChartPeriod.value[type]!!

    private fun updatePeriodsMapBySelectedPeriod(selectedPeriod: Map<ChartType, ChartPeriod>): Map<ChartType, List<Selectable<ChartPeriod>>> =
        ChartType().associateWith { chartType ->
            ChartPeriod.getPeriods().map { period ->
                Selectable(
                    item = period,
                    selected = period == selectedPeriod[chartType]
                )
            }
        }

    private fun popBackStack() = setEffect(ExerciseDetailUIEffect.PopBackStack)
}