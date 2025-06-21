package com.lifting.app.feature.exercises_category

import androidx.compose.runtime.Immutable
import com.lifting.app.core.base.viewmodel.State
import com.lifting.app.core.model.ExerciseCategory

/**
 * Created by bedirhansaricayir on 22.08.2024
 */


@Immutable
internal data class ExercisesCategoryUIState(
    val categories: List<ExerciseCategory> = ExerciseCategory.entries,
    val selectedCategory: String = ExerciseCategory.WEIGHT_AND_REPS.tag
) : State
