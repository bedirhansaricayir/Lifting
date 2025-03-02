package com.lifting.app.feature.workout_template_preview

import com.lifting.app.core.base.viewmodel.Effect

/**
 * Created by bedirhansaricayir on 02.03.2025
 */

sealed interface WorkoutTemplatePreviewUIEffect : Effect {
    data class NavigateToWorkoutEdit(val workoutId: String) : WorkoutTemplatePreviewUIEffect
    data object PopBackStack : WorkoutTemplatePreviewUIEffect
}