package com.lifting.app.core.data.repository.muscles

import com.lifting.app.core.database.model.MuscleEntity

/**
 * Created by bedirhansaricayir on 21.08.2024
 */

fun getPrePopulateMuscles(): List<MuscleEntity> = listOf(
    MuscleEntity(tag = "abductors", name = "Abductors", isDeletable = false, isHidden = false),
    MuscleEntity(tag = "abs", name = "Abs", isDeletable = false, isHidden = false),
    MuscleEntity(tag = "back", name = "Back", isDeletable = false, isHidden = false),
    MuscleEntity(tag = "biceps", name = "Biceps", isDeletable = false, isHidden = false),
    MuscleEntity(tag = "calves", name = "Calves", isDeletable = false, isHidden = false),
    MuscleEntity(tag = "cardio", name = "Cardio", isDeletable = false, isHidden = false),
    MuscleEntity(tag = "chest", name = "Chest", isDeletable = false, isHidden = false),
    MuscleEntity(tag = "body", name = "Core", isDeletable = false, isHidden = false),
    MuscleEntity(tag = "forearms", name = "Forearms", isDeletable = false, isHidden = false),
    MuscleEntity(tag = "glutes", name = "Glutes", isDeletable = false, isHidden = false),
    MuscleEntity(tag = "hamstrings", name = "Hamstrings", isDeletable = false, isHidden = false),
    MuscleEntity(tag = "lats", name = "Lats", isDeletable = false, isHidden = false),
    MuscleEntity(tag = "weightlifting", name = "Olympic Weightlifting", isDeletable = false, isHidden = false),
    MuscleEntity(tag = "quadriceps", name = "Quadriceps", isDeletable = false, isHidden = false),
    MuscleEntity(tag = "shoulders", name = "Shoulders", isDeletable = false, isHidden = false),
    MuscleEntity(tag = "traps", name = "Traps", isDeletable = false, isHidden = false),
    MuscleEntity(tag = "triceps", name = "Triceps", isDeletable = false, isHidden = false),
)