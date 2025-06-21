package com.lifting.app.feature.create_exercise

import androidx.compose.runtime.Immutable
import com.lifting.app.core.base.viewmodel.State
import com.lifting.app.core.model.ExerciseCategory
import com.lifting.app.core.model.Muscle

/**
 * Created by bedirhansaricayir on 21.08.2024
 */
@Immutable
internal data class CreateExerciseUIState(
    val exerciseName: String? = "",
    val exerciseNotes: String? = "",
    val categories: List<ExerciseCategory> = ExerciseCategory.entries,
    val selectedCategory: ExerciseCategory = ExerciseCategory.WEIGHT_AND_REPS,
    val muscles: List<Muscle> = Muscle.entries,
    val selectedMuscle: Muscle? = null
) : State
