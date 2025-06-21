package com.lifting.app.feature.barbell_selector

import com.lifting.app.core.base.viewmodel.Effect

/**
 * Created by bedirhansaricayir on 29.05.2025
 */

internal sealed interface BarbellSelectorUIEffect : Effect {
    data class SetBarbellToBackStack(val barbellWithJunctionId: String) : BarbellSelectorUIEffect
}