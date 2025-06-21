package com.lifting.app.feature.create_exercise

import androidx.lifecycle.viewModelScope
import com.lifting.app.core.base.viewmodel.BaseViewModel
import com.lifting.app.core.data.repository.exercises.ExercisesRepository
import com.lifting.app.core.model.ExerciseCategory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by bedirhansaricayir on 13.08.2024
 */

@HiltViewModel
internal class CreateExerciseViewModel @Inject constructor(
    private val exercisesRepository: ExercisesRepository
) : BaseViewModel<CreateExerciseUIState, CreateExerciseUIEvent, CreateExerciseUIEffect>() {

    override fun setInitialState(): CreateExerciseUIState = CreateExerciseUIState()

    override fun handleEvents(event: CreateExerciseUIEvent) {
        when (event) {
            CreateExerciseUIEvent.OnNavigationClick -> navigateBackEffect()
            CreateExerciseUIEvent.OnActionClick -> createExercise()
            is CreateExerciseUIEvent.OnCategoryChanged -> updateSelectedCategory(event.category)
            is CreateExerciseUIEvent.OnExerciseNameChanged -> updateExerciseName(event.exerciseName)
            is CreateExerciseUIEvent.OnExerciseNotesChanged -> updateExerciseNotes(event.exerciseNotes)
            is CreateExerciseUIEvent.OnSelectedMuscleChanged -> updateSelectedMuscle(event.selectedMuscle)
            is CreateExerciseUIEvent.OnCategoryClicked -> navigateToCategoriesEffect(event.selectedCategory)
            is CreateExerciseUIEvent.OnMuscleClicked -> navigateToMusclesEffect(event.selectedMuscle)
        }
    }

    private fun createExercise() {
        viewModelScope.launch {
            getCurrentState().apply {
                exercisesRepository.createExercise(
                    name = this.exerciseName,
                    notes = this.exerciseNotes,
                    category = this.selectedCategory,
                    primaryMuscleTag = this.selectedMuscle?.tag,
                )
            }
        }
        navigateBackEffect()
    }

    private fun navigateBackEffect() {
        setEffect(CreateExerciseUIEffect.NavigateBack)
    }

    private fun navigateToCategoriesEffect(selectedCategory: String) {
        setEffect(CreateExerciseUIEffect.NavigateToCategories(selectedCategory))
    }

    private fun updateSelectedCategory(selectedCategory: String) {
        updateState { currentState ->
            currentState.copy(
                selectedCategory = ExerciseCategory.getExerciseCategoryByTag(
                    selectedCategory
                )
            )
        }
    }

    private fun updateExerciseName(exerciseName: String) {
        updateState { currentState ->
            currentState.copy(exerciseName = exerciseName)
        }
    }

    private fun updateExerciseNotes(exerciseNotes: String) {
        updateState { currentState ->
            currentState.copy(exerciseNotes = exerciseNotes)
        }
    }

    private fun updateSelectedMuscle(selectedMuscle: String) {
        updateState { currentState ->
            currentState.copy(selectedMuscle = currentState.muscles.find { it.tag == selectedMuscle })
        }
    }

    private fun navigateToMusclesEffect(selectedMuscle: String?) {
        setEffect(CreateExerciseUIEffect.NavigateToMuscles(selectedMuscle))
    }
}