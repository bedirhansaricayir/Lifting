package com.lifting.app.feature.barbell_selector

import androidx.compose.runtime.Immutable
import com.lifting.app.core.base.viewmodel.State
import com.lifting.app.core.common.extensions.EMPTY
import com.lifting.app.core.model.Barbell

/**
 * Created by bedirhansaricayir on 29.05.2025
 */

@Immutable
internal data class BarbellSelectorUIState(
    val barbells: List<Barbell> = emptyList(),
    val junctionId: String = String.EMPTY,
    val barbellId: String? = null
) : State

