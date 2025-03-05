package com.lifting.app.feature.exercise_detail

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.lifting.app.core.base.viewmodel.BaseViewModel
import com.lifting.app.core.data.repository.exercises.ExercisesRepository
import com.lifting.app.core.navigation.screens.LiftingScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by bedirhansaricayir on 02.03.2025
 */

@HiltViewModel
class ExerciseDetailViewModel @Inject constructor(
    private val exercisesRepository: ExercisesRepository,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<ExerciseDetailUIState, ExerciseDetailUIEvent, ExerciseDetailUIEffect>() {
    private val exerciseId = savedStateHandle.toRoute<LiftingScreen.ExerciseDetail>().exerciseIdKey

    override fun setInitialState(): ExerciseDetailUIState = ExerciseDetailUIState.Success

    override fun handleEvents(event: ExerciseDetailUIEvent) {
        when (event) {

            else -> {}
        }
    }

    init {

    }

}