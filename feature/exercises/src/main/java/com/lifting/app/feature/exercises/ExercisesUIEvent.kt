package com.lifting.app.feature.exercises

import com.lifting.app.core.base.viewmodel.Event

/**
 * Created by bedirhansaricayir on 30.07.2024
 */

sealed interface ExercisesUIEvent : Event {
    data object OnAddClick: ExercisesUIEvent
    data class OnSearchQueryChanged(val query: String): ExercisesUIEvent
    data object OnFilterClick: ExercisesUIEvent
    data class OnExerciseClick(val id: String,val isBottomSheet: Boolean): ExercisesUIEvent
}