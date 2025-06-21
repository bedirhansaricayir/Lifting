package com.lifting.app.feature.history

import androidx.compose.runtime.Immutable
import com.lifting.app.core.base.viewmodel.State

/**
 * Created by bedirhansaricayir on 08.03.2025
 */

@Immutable
data class HistoryUIState(val data: List<Any> = emptyList()) : State
