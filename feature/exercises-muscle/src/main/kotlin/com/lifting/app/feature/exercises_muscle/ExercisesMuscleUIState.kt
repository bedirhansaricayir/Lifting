package com.lifting.app.feature.exercises_muscle

import androidx.compose.runtime.Immutable
import com.lifting.app.core.base.viewmodel.State
import com.lifting.app.core.model.Muscle

/**
 * Created by bedirhansaricayir on 28.08.2024
 */

@Immutable
internal data class ExercisesMuscleUIState(
    val muscles: List<Muscle> = Muscle.entries,
    val selectedMuscle: String? = ""
) : State
