package com.lifting.app.feature.exercises_category

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.lifting.app.core.base.viewmodel.BaseViewModel
import com.lifting.app.core.model.ExerciseCategory
import com.lifting.app.core.model.allExerciseCategories
import com.lifting.app.core.navigation.screens.LiftingScreen.Companion.SELECTED_EXERCISE_CATEGORY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by bedirhansaricayir on 22.08.2024
 */

@HiltViewModel
class ExercisesCategoryViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle
): BaseViewModel<ExercisesCategoryUIState,ExercisesCategoryUIEvent,ExercisesCategoryUIEffect>() {

    private val selectedCategory = savedStateHandle.get<String>(SELECTED_EXERCISE_CATEGORY)

    override fun setInitialState(): ExercisesCategoryUIState = ExercisesCategoryUIState.Loading

    override fun handleEvents(event: ExercisesCategoryUIEvent) {
        when(event) {
            is ExercisesCategoryUIEvent.OnCategoryClick -> onCategoryClick(event.category)
            is ExercisesCategoryUIEvent.OnSelectedCategoryChanged -> onSelectedCategoryChanged(event.category)
        }
    }

    init {
        setUIState()
    }

    private fun setUIState() {
        viewModelScope.launch {
            val exercisesCategories: List<ExerciseCategory> = allExerciseCategories
            setState (
                ExercisesCategoryUIState.Success(
                    categories = exercisesCategories,
                    selectedCategory = selectedCategory
                )
            )
        }
    }

    private fun onCategoryClick(category: ExerciseCategory) {
        setEffect(ExercisesCategoryUIEffect.SetCategoryToBackStack(category))
    }

    private fun onSelectedCategoryChanged(category: String) {
        savedStateHandle[SELECTED_EXERCISE_CATEGORY] = category
        updateState { currentState ->
            (currentState as ExercisesCategoryUIState.Success).copy(selectedCategory = category)
        }
    }
}