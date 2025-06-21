package com.lifting.app.feature.calendar

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.lifting.app.core.base.viewmodel.BaseViewModel
import com.lifting.app.core.common.utils.generateUUID
import com.lifting.app.core.data.repository.workouts.WorkoutsRepository
import com.lifting.app.core.model.TimePeriod
import com.lifting.app.core.model.WorkoutWithExtraInfo
import com.lifting.app.core.ui.extensions.toCreateWorkoutNameByPeriod
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

/**
 * Created by bedirhansaricayir on 09.03.2025
 */

@HiltViewModel
internal class CalendarViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val workoutsRepository: WorkoutsRepository
) : BaseViewModel<CalendarUIState, CalendarUIEvent, CalendarUIEffect>() {

    override fun setInitialState(): CalendarUIState = CalendarUIState()

    override fun handleEvents(event: CalendarUIEvent) {
        when (event) {
            CalendarUIEvent.OnBackIconClick -> setNavigateBackEffect()
            is CalendarUIEvent.OnDayClicked -> getWorkoutsByDate(event.date)
            is CalendarUIEvent.OnWorkoutClicked -> navigateToWorkoutDetail(event.workoutId)
            is CalendarUIEvent.OnCreateNewWorkoutClicked -> createWorkout(discardActive = false)
            CalendarUIEvent.OnDialogConfirmClicked -> setDialogThenCreateWorkout()
            CalendarUIEvent.OnDialogDismissClicked -> setShowActiveWorkoutDialog(false)
        }
    }

    private val workouts = MutableStateFlow<List<WorkoutWithExtraInfo>>(emptyList())

    init {
        updateUIState()
    }

    private fun setNavigateBackEffect() = setEffect(CalendarUIEffect.NavigateBack)

    private fun updateUIState() {
        viewModelScope.launch {
            workoutsRepository.getWorkoutsWithExtraInfo().collect {
                workouts.value = it

                val workoutDays = workouts.value.map { workoutWithInfo ->
                    (workoutWithInfo.workout?.completedAt!!.toLocalDate())
                }

                updateState { currentState ->
                    currentState.copy(
                        workoutDays = workoutDays
                    )
                }

                if (workoutDays.contains(LocalDate.now())) {
                    getWorkoutsByDate(LocalDate.now())
                }
            }
        }
    }

    private fun getWorkoutsByDate(localDate: LocalDate) {
        viewModelScope.launch {
            updateState { currentState ->
                currentState.copy(
                    workouts = workouts.value.filter { workoutWithInfo ->
                        workoutWithInfo.workout?.completedAt?.toLocalDate() == localDate
                    },
                    selectedDay = localDate
                )
            }
        }
    }

    private fun createWorkout(
        workoutName: String = TimePeriod.now().toCreateWorkoutNameByPeriod(context),
        discardActive: Boolean
    ) {
        viewModelScope.launch {
            val workoutId = generateUUID()
            val isCreated = workoutsRepository.createWorkout(
                workoutId = workoutId,
                workoutName = workoutName,
                discardActive = discardActive,
                onWorkoutAlreadyActive = {
                    setShowActiveWorkoutDialog(true)
                }
            )
            if (isCreated) {
                workoutsRepository.setActiveWorkoutId(workoutId)
            }
        }
    }

    private fun setDialogThenCreateWorkout() {
        setShowActiveWorkoutDialog(false)
        createWorkout(discardActive = true)
    }

    private fun setShowActiveWorkoutDialog(showDialog: Boolean) {
        updateState { currentState ->
            currentState.copy(
                showActiveWorkoutDialog = showDialog
            )
        }
    }

    private fun navigateToWorkoutDetail(workoutId: String) =
        setEffect(CalendarUIEffect.NavigateToWorkoutDetail(workoutId))
}
