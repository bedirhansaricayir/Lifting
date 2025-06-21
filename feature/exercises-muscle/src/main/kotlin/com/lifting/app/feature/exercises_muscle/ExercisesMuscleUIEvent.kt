package com.lifting.app.feature.exercises_muscle

import com.lifting.app.core.base.viewmodel.Event
import com.lifting.app.core.model.Muscle

/**
 * Created by bedirhansaricayir on 28.08.2024
 */

internal sealed interface ExercisesMuscleUIEvent : Event {
    data class OnMuscleClick(val muscle: Muscle) : ExercisesMuscleUIEvent
    data class OnSelectedMuscleChanged(val muscle: String) : ExercisesMuscleUIEvent
}