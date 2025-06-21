package com.lifting.app.feature.settings

import com.lifting.app.core.base.viewmodel.Effect
import com.lifting.app.core.ui.common.UiText

/**
 * Created by bedirhansaricayir on 16.05.2025
 */
sealed interface SettingsUIEffect : Effect {
    data object NavigateToAppStore : SettingsUIEffect
    data object NavigateToMail : SettingsUIEffect
    data class ShowNotification(val message: UiText) : SettingsUIEffect
}