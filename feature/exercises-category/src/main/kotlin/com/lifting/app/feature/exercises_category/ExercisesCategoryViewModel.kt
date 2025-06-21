package com.lifting.app.feature.exercises_category

import androidx.lifecycle.viewModelScope
import com.lifting.app.core.base.viewmodel.BaseViewModel
import com.lifting.app.core.model.ExerciseCategory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by bedirhansaricayir on 22.08.2024
 */

@HiltViewModel
internal class ExercisesCategoryViewModel @Inject constructor(
) : BaseViewModel<ExercisesCategoryUIState, ExercisesCategoryUIEvent, ExercisesCategoryUIEffect>() {

    private val selectedCategory = MutableStateFlow<String>(ExerciseCategory.WEIGHT_AND_REPS.tag)

    override fun setInitialState(): ExercisesCategoryUIState = ExercisesCategoryUIState()

    override fun handleEvents(event: ExercisesCategoryUIEvent) {
        when (event) {
            is ExercisesCategoryUIEvent.OnCategoryClick -> onCategoryClick(event.category)
            is ExercisesCategoryUIEvent.OnSelectedCategoryChanged -> onSelectedCategoryChanged(event.category)
        }
    }

    init {
        updateUIState()
    }

    private fun updateUIState() {
        viewModelScope.launch {
            selectedCategory.collect { category ->
                updateState {
                    it.copy(
                        selectedCategory = category
                    )
                }
            }
        }
    }

    private fun onCategoryClick(category: ExerciseCategory) {
        setEffect(ExercisesCategoryUIEffect.SetCategoryToBackStack(category))
    }

    private fun onSelectedCategoryChanged(category: String) {
        selectedCategory.value = category
    }
}