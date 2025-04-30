package com.lifting.app.feature.workout_detail

import com.lifting.app.core.base.viewmodel.Event

/**
 * Created by bedirhansaricayir on 06.04.2025
 */
sealed interface WorkoutDetailUIEvent : Event {
    data object OnEditClicked : WorkoutDetailUIEvent
    data object OnDeleteClicked : WorkoutDetailUIEvent
    data object OnBackClicked: WorkoutDetailUIEvent
}