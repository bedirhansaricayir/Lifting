package com.lifting.app.feature.superset_selector

import com.lifting.app.core.base.viewmodel.Effect

/**
 * Created by bedirhansaricayir on 11.06.2025
 */
internal sealed interface SupersetSelectorUIEffect : Effect {
    data class SetSuperSetToBackStack(val result: String) : SupersetSelectorUIEffect
}