package com.lifting.app.feature.superset_selector

import androidx.compose.runtime.Immutable
import com.lifting.app.core.base.viewmodel.State
import com.lifting.app.core.common.extensions.EMPTY
import com.lifting.app.core.model.LogEntriesWithExercise

/**
 * Created by bedirhansaricayir on 11.06.2025
 */
@Immutable
internal data class SupersetSelectorUIState(
    val junctions: List<LogEntriesWithExercise> = emptyList(),
    val junctionId: String = String.EMPTY,
    val workoutId: String = String.EMPTY
) : State
