package com.lifting.app.feature.barbell_selector

import com.lifting.app.core.base.viewmodel.Event
import com.lifting.app.core.model.Barbell

/**
 * Created by bedirhansaricayir on 29.05.2025
 */

internal sealed interface BarbellSelectorUIEvent : Event {
    data class OnReceivedBarbellChangeRequest(val selectedBarbellWithJunctionId: String) : BarbellSelectorUIEvent
    data class OnBarbellClick(val barbell: Barbell) : BarbellSelectorUIEvent
}