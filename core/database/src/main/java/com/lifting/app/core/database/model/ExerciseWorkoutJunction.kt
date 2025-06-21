package com.lifting.app.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by bedirhansaricayir on 15.07.2024
 */

@Entity(tableName = "exercise_workout_junctions")
data class ExerciseWorkoutJunction(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val id: String,

    @ColumnInfo(name = "superset_id")
    var supersetId: Int? = null,

    @ColumnInfo(name = "barbell_id")
    var barbellId: String? = null,

    @ColumnInfo(name = "exercise_id")
    var exerciseId: String? = null,
    @ColumnInfo(name = "workout_id")
    var workoutId: String? = null,
)