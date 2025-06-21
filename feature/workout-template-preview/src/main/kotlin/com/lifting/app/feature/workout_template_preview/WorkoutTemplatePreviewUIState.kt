package com.lifting.app.feature.workout_template_preview

import androidx.compose.runtime.Immutable
import com.lifting.app.core.base.viewmodel.State
import com.lifting.app.core.model.LogEntriesWithExtraInfo
import com.lifting.app.core.model.Workout
import com.lifting.app.core.model.WorkoutTemplate

/**
 * Created by bedirhansaricayir on 02.03.2025
 */

@Immutable
internal data class WorkoutTemplatePreviewUIState(
    val template: WorkoutTemplate? = null,
    val entries: List<LogEntriesWithExtraInfo> = emptyList(),
    val workout: Workout? = null,
    val showActiveWorkoutDialog: Boolean = false
) : State