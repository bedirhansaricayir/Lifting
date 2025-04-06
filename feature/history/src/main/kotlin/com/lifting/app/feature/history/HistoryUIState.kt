package com.lifting.app.feature.history

import com.lifting.app.core.base.viewmodel.State
import com.lifting.app.core.model.WorkoutWithExtraInfo
import com.lifting.app.feature.history.components.WorkoutsDateRangeType

/**
 * Created by bedirhansaricayir on 08.03.2025
 */

sealed interface HistoryUIState : State {
    data object Loading : HistoryUIState
    data object Error : HistoryUIState
    data class Success(val data: List<Any>, val dateRangeType: WorkoutsDateRangeType) : HistoryUIState
}