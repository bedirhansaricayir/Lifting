package com.lifting.app.feature.exercise_detail

import com.lifting.app.core.base.viewmodel.Effect

/**
 * Created by bedirhansaricayir on 02.03.2025
 */

internal sealed interface ExerciseDetailUIEffect : Effect {
    data object PopBackStack : ExerciseDetailUIEffect
}