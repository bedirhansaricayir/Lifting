package com.lifting.app.feature.create_exercise

import androidx.lifecycle.viewModelScope
import com.lifting.app.core.base.viewmodel.BaseViewModel
import com.lifting.app.core.data.repository.exercises.ExercisesRepository
import com.lifting.app.core.data.repository.muscles.MusclesRepository
import com.lifting.app.core.data.repository.muscles.parseToMuscle
import com.lifting.app.core.model.ExerciseCategory
import com.lifting.app.core.model.allExerciseCategories
import com.lifting.app.core.model.parseToExerciseCategory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by bedirhansaricayir on 13.08.2024
 */

@HiltViewModel
class CreateExerciseViewModel @Inject constructor(
    private val musclesRepository: MusclesRepository,
    private val exercisesRepository: ExercisesRepository
) : BaseViewModel<CreateExerciseUIState, CreateExerciseUIEvent, CreateExerciseUIEffect>() {

    override fun setInitialState(): CreateExerciseUIState = CreateExerciseUIState.Loading

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

    init {
        initUIState()
    }

    private fun initUIState() {
        viewModelScope.launch {
            musclesRepository.getMuscles()
                .onStart {
                    setState(CreateExerciseUIState.Loading)
                }.catch { throwable ->
                    setState(
                        CreateExerciseUIState.Error(
                            message = throwable.message ?: "Something Went Wrong!"
                        )
                    )
                }
                .collect { muscleList ->
                    setState(
                        CreateExerciseUIState.Success(
                            exerciseName = "",
                            exerciseNotes = "",
                            categories = allExerciseCategories,
                            selectedCategory = ExerciseCategory.WeightAndReps,
                            muscles = muscleList,
                        )
                    )
                }
        }
    }


    private fun createExercise() {
        viewModelScope.launch {
            (getCurrentState() as CreateExerciseUIState.Success).apply {
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
            (currentState as CreateExerciseUIState.Success).copy(selectedCategory = selectedCategory.parseToExerciseCategory())
        }
    }

    private fun updateExerciseName(exerciseName: String) {
        updateState { currentState ->
            (currentState as CreateExerciseUIState.Success).copy(exerciseName = exerciseName)
        }
    }

    private fun updateExerciseNotes(exerciseNotes: String) {
        updateState { currentState ->
            (currentState as CreateExerciseUIState.Success).copy(exerciseNotes = exerciseNotes)
        }
    }

    private fun updateSelectedMuscle(selectedMuscle: String) {
        updateState { currentState ->
            (currentState as CreateExerciseUIState.Success).copy(selectedMuscle = selectedMuscle.parseToMuscle())
        }
    }

    private fun navigateToMusclesEffect(selectedMuscle: String?) {
        setEffect(CreateExerciseUIEffect.NavigateToMuscles(selectedMuscle))
    }
}