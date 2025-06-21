package com.lifting.app.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.lifting.app.core.database.dao.BarbellsDao
import com.lifting.app.core.database.dao.ExercisesDao
import com.lifting.app.core.database.dao.PlatesDao
import com.lifting.app.core.database.dao.WorkoutTemplateDao
import com.lifting.app.core.database.dao.WorkoutsDao
import com.lifting.app.core.database.model.BarbellEntity
import com.lifting.app.core.database.model.ExerciseEntity
import com.lifting.app.core.database.model.ExerciseLogEntity
import com.lifting.app.core.database.model.ExerciseLogEntryEntity
import com.lifting.app.core.database.model.ExerciseSetGroupNoteEntity
import com.lifting.app.core.database.model.ExerciseWorkoutJunction
import com.lifting.app.core.database.model.PlateEntity
import com.lifting.app.core.database.model.WorkoutEntity
import com.lifting.app.core.database.model.WorkoutTemplateEntity
import com.lifting.app.core.database.util.Converters

/**
 * Created by bedirhansaricayir on 18.07.2024
 */

@Database(
    entities = [
        ExerciseEntity::class,
        ExerciseLogEntryEntity::class,
        ExerciseSetGroupNoteEntity::class,
        WorkoutEntity::class,
        ExerciseWorkoutJunction::class,
        WorkoutTemplateEntity::class,
        ExerciseLogEntity::class,
        BarbellEntity::class,
        PlateEntity::class
    ],
    version = 8,
    exportSchema = true
)
@TypeConverters(Converters::class)
internal abstract class LiftingDatabase: RoomDatabase() {
    abstract fun exercisesDao(): ExercisesDao
    abstract fun workoutTemplateDao(): WorkoutTemplateDao
    abstract fun workoutsDao() : WorkoutsDao
    abstract fun barbellsDao(): BarbellsDao
    abstract fun platesDao(): PlatesDao
}