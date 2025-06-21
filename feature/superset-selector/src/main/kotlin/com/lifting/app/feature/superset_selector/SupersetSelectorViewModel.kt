package com.lifting.app.feature.superset_selector

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.lifting.app.core.base.viewmodel.BaseViewModel
import com.lifting.app.core.common.extensions.getBackStackArgs
import com.lifting.app.core.data.repository.workouts.WorkoutsRepository
import com.lifting.app.core.model.ExerciseWorkoutJunc
import com.lifting.app.core.navigation.screens.LiftingScreen.Companion.RESULT_SUPERSET_SELECTOR_SUPERSET_ID_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by bedirhansaricayir on 11.06.2025
 */
@HiltViewModel
internal class SupersetSelectorViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val workoutRepository: WorkoutsRepository
) : BaseViewModel<SupersetSelectorUIState, SupersetSelectorUIEvent, SupersetSelectorUIEffect>() {

    private val workoutIdWithJunctionId =
        savedStateHandle.getStateFlow<String>(RESULT_SUPERSET_SELECTOR_SUPERSET_ID_KEY, "")

    override fun setInitialState(): SupersetSelectorUIState = SupersetSelectorUIState()

    override fun handleEvents(event: SupersetSelectorUIEvent) {
        when (event) {
            is SupersetSelectorUIEvent.OnSupersetRequest -> onSupersetRequest(event.workoutIdWithJunctionId)
            is SupersetSelectorUIEvent.OnExerciseClicked -> handleExerciseClicked(event.junction)
        }
    }

    init {
        updateUIState()
    }

    private fun updateUIState() {
        viewModelScope.launch {
            workoutIdWithJunctionId.collectLatest { workoutIdWithJunctionId ->
                if (workoutIdWithJunctionId.isNotEmpty()) {
                    val workoutId = workoutIdWithJunctionId.getBackStackArgs().first()
                    val junctionId = workoutIdWithJunctionId.getBackStackArgs().last()
                    workoutRepository.getLogEntriesWithExercise(workoutId).collect {
                        updateState { currentState ->
                            currentState.copy(
                                junctions = it,
                                junctionId = junctionId,
                                workoutId = workoutId
                            )
                        }
                    }
                }
            }
        }
    }

    private fun onSupersetRequest(workoutIdWithJunctionId: String) {
        savedStateHandle[RESULT_SUPERSET_SELECTOR_SUPERSET_ID_KEY] = workoutIdWithJunctionId
    }

    private fun handleExerciseClicked(junction: ExerciseWorkoutJunc) {
        getCurrentState().let { state ->
            val mSupersetId =
                junction.supersetId ?: ((state.junctions.mapNotNull { it.junction.supersetId }
                    .maxOrNull()
                    ?: -1) + 1)

            val result = "${state.junctionId},${junction.id},${mSupersetId}"

            setResultToBackStack(result)
        }
    }

    private fun setResultToBackStack(result: String) =
        setEffect(SupersetSelectorUIEffect.SetSuperSetToBackStack(result))


}