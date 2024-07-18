package com.lifting.app.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.lifting.app.core.database.dao.ExercisesDao
import com.lifting.app.core.database.model.ExerciseEntity
import com.lifting.app.core.database.model.ExerciseLogEntryEntity
import com.lifting.app.core.database.model.ExerciseSetGroupNoteEntity
import com.lifting.app.core.database.model.ExerciseWorkoutJunction
import com.lifting.app.core.database.model.MuscleEntity
import com.lifting.app.core.database.model.WorkoutEntity
import com.lifting.app.core.database.util.Converters

/**
 * Created by bedirhansaricayir on 18.07.2024
 */

@Database(
    entities = [
        ExerciseEntity::class,
        ExerciseLogEntryEntity::class,
        ExerciseSetGroupNoteEntity::class,
        MuscleEntity::class,
        WorkoutEntity::class,
        ExerciseWorkoutJunction::class,
    ],
    version = 1,
    exportSchema = true
)
@TypeConverters(Converters::class)
internal abstract class LiftingDatabase: RoomDatabase() {
    abstract fun exercisesDao(): ExercisesDao
}