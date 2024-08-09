package com.lifting.app.feature.exercises

import com.lifting.app.core.base.viewmodel.Effect

/**
 * Created by bedirhansaricayir on 30.07.2024
 */

sealed interface ExercisesUIEffect : Effect {
    data object NavigateToAddExercise : ExercisesUIEffect
}