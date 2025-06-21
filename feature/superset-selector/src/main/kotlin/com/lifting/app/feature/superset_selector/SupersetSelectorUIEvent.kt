package com.lifting.app.feature.superset_selector

import com.lifting.app.core.base.viewmodel.Event
import com.lifting.app.core.model.ExerciseWorkoutJunc

/**
 * Created by bedirhansaricayir on 11.06.2025
 */
internal sealed interface SupersetSelectorUIEvent : Event {
    data class OnSupersetRequest(val workoutIdWithJunctionId: String) : SupersetSelectorUIEvent
    data class OnExerciseClicked(val junction: ExerciseWorkoutJunc) : SupersetSelectorUIEvent
}