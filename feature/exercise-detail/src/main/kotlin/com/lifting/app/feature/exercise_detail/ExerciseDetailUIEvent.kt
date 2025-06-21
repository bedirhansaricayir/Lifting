package com.lifting.app.feature.exercise_detail

import com.lifting.app.core.base.viewmodel.Event

/**
 * Created by bedirhansaricayir on 02.03.2025
 */

internal sealed interface ExerciseDetailUIEvent : Event {
    data class OnPeriodMenuClick(val chartType: ChartType) : ExerciseDetailUIEvent
    data class OnDropdownDismissed(val chartType: ChartType) : ExerciseDetailUIEvent
    data class OnPeriodItemSelected(val chartType: ChartType, val period: ChartPeriod) : ExerciseDetailUIEvent
    data object OnBackClicked : ExerciseDetailUIEvent
}