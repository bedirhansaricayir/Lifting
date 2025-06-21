package com.lifting.app.feature.exercises_muscle

import androidx.lifecycle.viewModelScope
import com.lifting.app.core.base.viewmodel.BaseViewModel
import com.lifting.app.core.model.Muscle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by bedirhansaricayir on 28.08.2024
 */

@HiltViewModel
internal class ExercisesMuscleViewModel @Inject constructor(
) : BaseViewModel<ExercisesMuscleUIState, ExercisesMuscleUIEvent, ExercisesMuscleUIEffect>() {

    private val selectedMuscle = MutableStateFlow<String?>("")

    override fun setInitialState(): ExercisesMuscleUIState = ExercisesMuscleUIState()

    override fun handleEvents(event: ExercisesMuscleUIEvent) {
        when (event) {
            is ExercisesMuscleUIEvent.OnMuscleClick -> onMuscleClick(event.muscle)
            is ExercisesMuscleUIEvent.OnSelectedMuscleChanged -> onSelectedMuscleChanged(event.muscle)
        }
    }

    init {
        updateUIState()
    }

    private fun updateUIState() {
        viewModelScope.launch {
            selectedMuscle.collect { muscle ->
                updateState {
                    it.copy(
                        selectedMuscle = muscle
                    )
                }
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