package com.lifting.app.feature.exercises_muscle

import androidx.lifecycle.viewModelScope
import com.lifting.app.core.base.viewmodel.BaseViewModel
import com.lifting.app.core.data.repository.muscles.MusclesRepository
import com.lifting.app.core.model.Muscle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by bedirhansaricayir on 28.08.2024
 */

@HiltViewModel
class ExercisesMuscleViewModel @Inject constructor(
    private val musclesRepository: MusclesRepository
) : BaseViewModel<ExercisesMuscleUIState, ExercisesMuscleUIEvent, ExercisesMuscleUIEffect>() {

    private val selectedMuscle = MutableStateFlow<String?>("")

    override fun setInitialState(): ExercisesMuscleUIState = ExercisesMuscleUIState.Loading

    override fun handleEvents(event: ExercisesMuscleUIEvent) {
        when (event) {
            is ExercisesMuscleUIEvent.OnMuscleClick -> onMuscleClick(event.muscle)
            is ExercisesMuscleUIEvent.OnSelectedMuscleChanged -> onSelectedMuscleChanged(event.muscle)
        }
    }

    init {
        setUIState()
    }

    private fun setUIState() {
        viewModelScope.launch {
            musclesRepository.getMuscles()
                .onStart {
                    setState(ExercisesMuscleUIState.Loading)
                }
                .catch { throwable ->
                    setState(
                        ExercisesMuscleUIState.Error(
                            message = throwable.message ?: "Something Went Wrong!"
                        )
                    )
                }
                .collect { muscles ->
                    setState(
                        ExercisesMuscleUIState.Success(
                            muscles = muscles,
                            selectedMuscle = selectedMuscle.value
                        )
                    )
                }
        }
    }

    private fun onMuscleClick(muscle: Muscle) {
        setEffect(ExercisesMuscleUIEffect.SetMuscleToBackStack(muscle))
    }

    private fun onSelectedMuscleChanged(muscle: String) {
        selectedMuscle.value = muscle
    }
}