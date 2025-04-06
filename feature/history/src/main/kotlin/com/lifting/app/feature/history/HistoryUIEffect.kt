package com.lifting.app.feature.history

import com.lifting.app.core.base.viewmodel.Effect

/**
 * Created by bedirhansaricayir on 08.03.2025
 */

sealed interface HistoryUIEffect : Effect {
    data object NavigateToCalendar : HistoryUIEffect
}
