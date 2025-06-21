package com.lifting.app.feature.workout

import com.lifting.app.core.base.viewmodel.Event
import com.lifting.app.core.model.LayoutType
import com.lifting.app.core.model.ScrollDirection

/**
 * Created by bedirhansaricayir on 02.02.2025
 */

internal sealed interface WorkoutUIEvent : Event {
    data object OnCreateTemplateClicked : WorkoutUIEvent
    data class OnTemplateClicked(val templateId: String) : WorkoutUIEvent
    data class OnScrollDirectionChanged(val direction: ScrollDirection) : WorkoutUIEvent
    data class OnLayoutTypeClicked(val layoutType: LayoutType) : WorkoutUIEvent
    data class OnStartWorkoutClicked(val workoutName: String) : WorkoutUIEvent
}