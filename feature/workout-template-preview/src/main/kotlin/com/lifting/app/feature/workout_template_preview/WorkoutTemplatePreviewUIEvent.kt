package com.lifting.app.feature.workout_template_preview

import com.lifting.app.core.base.viewmodel.Event

/**
 * Created by bedirhansaricayir on 02.03.2025
 */

internal sealed interface WorkoutTemplatePreviewUIEvent : Event {
    data class OnEditClicked(val workoutId: String) : WorkoutTemplatePreviewUIEvent
    data object OnDeleteClicked : WorkoutTemplatePreviewUIEvent
    data object OnBackClicked : WorkoutTemplatePreviewUIEvent
    data object OnPlayClicked : WorkoutTemplatePreviewUIEvent
    data object OnDialogDismissClicked : WorkoutTemplatePreviewUIEvent
    data object OnDialogConfirmClicked : WorkoutTemplatePreviewUIEvent
}