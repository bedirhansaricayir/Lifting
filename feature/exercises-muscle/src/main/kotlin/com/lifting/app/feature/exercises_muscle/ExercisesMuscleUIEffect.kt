package com.lifting.app.feature.exercises_muscle

import com.lifting.app.core.base.viewmodel.Effect
import com.lifting.app.core.model.Muscle

/**
 * Created by bedirhansaricayir on 28.08.2024
 */

internal sealed interface ExercisesMuscleUIEffect : Effect {
    data class SetMuscleToBackStack(val selectedMuscle: Muscle) : ExercisesMuscleUIEffect
}