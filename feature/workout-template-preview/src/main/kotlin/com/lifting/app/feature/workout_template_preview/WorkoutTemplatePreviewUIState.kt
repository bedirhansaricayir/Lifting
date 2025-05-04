package com.lifting.app.feature.workout_template_preview

import com.lifting.app.core.base.viewmodel.State
import com.lifting.app.core.model.LogEntriesWithExtraInfo
import com.lifting.app.core.model.Workout
import com.lifting.app.core.model.WorkoutTemplate

/**
 * Created by bedirhansaricayir on 02.03.2025
 */

sealed interface WorkoutTemplatePreviewUIState : State {
    data object Loading : WorkoutTemplatePreviewUIState
    data class Success(
        val template: WorkoutTemplate,
        val entries: List<LogEntriesWithExtraInfo> = emptyList(),
        val workout: Workout? = null,
        val showActiveWorkoutDialog: Boolean = false
    ) : WorkoutTemplatePreviewUIState

    data object Error : WorkoutTemplatePreviewUIState
}
