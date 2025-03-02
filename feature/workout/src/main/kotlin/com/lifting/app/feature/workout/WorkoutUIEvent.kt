package com.lifting.app.feature.workout

import com.lifting.app.core.base.viewmodel.Event

/**
 * Created by bedirhansaricayir on 02.02.2025
 */

sealed interface WorkoutUIEvent : Event {
    data object OnCreateTemplateClicked : WorkoutUIEvent
    data class OnTemplateClicked(val templateId: String) : WorkoutUIEvent
}