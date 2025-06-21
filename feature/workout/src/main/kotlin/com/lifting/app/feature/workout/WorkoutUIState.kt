package com.lifting.app.feature.workout

import androidx.compose.runtime.Immutable
import com.lifting.app.core.base.viewmodel.State
import com.lifting.app.core.model.LayoutType
import com.lifting.app.core.model.TemplateWithWorkout

/**
 * Created by bedirhansaricayir on 02.02.2025
 */

@Immutable
internal data class WorkoutUIState(
    val templates: List<TemplateWithWorkout> = emptyList(),
    val layoutType: LayoutType = LayoutType.Grid,
    val fabIsVisible: Boolean = true,
    val isExistActiveWorkout: Boolean = false
) : State


