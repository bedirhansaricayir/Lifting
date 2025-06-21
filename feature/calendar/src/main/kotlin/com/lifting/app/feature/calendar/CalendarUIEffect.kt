package com.lifting.app.feature.calendar

import com.lifting.app.core.base.viewmodel.Effect

/**
 * Created by bedirhansaricayir on 06.06.2025
 */

internal sealed interface CalendarUIEffect : Effect {
    data object NavigateBack : CalendarUIEffect
    data class NavigateToWorkoutDetail(val workoutId: String) : CalendarUIEffect
}