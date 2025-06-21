package com.lifting.app.core.database.di

import com.lifting.app.core.database.LiftingDatabase
import com.lifting.app.core.database.dao.BarbellsDao
import com.lifting.app.core.database.dao.ExercisesDao
import com.lifting.app.core.database.dao.PlatesDao
import com.lifting.app.core.database.dao.WorkoutTemplateDao
import com.lifting.app.core.database.dao.WorkoutsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Created by bedirhansaricayir on 18.07.2024
 */

@Module
@InstallIn(SingletonComponent::class)
internal object DaosModule {
    @Provides
    fun providesExercisesDao(
        database: LiftingDatabase
    ): ExercisesDao = database.exercisesDao()

    @Provides
    fun providesWorkoutTemplateDao(
        database: LiftingDatabase
    ): WorkoutTemplateDao = database.workoutTemplateDao()

    @Provides
    fun providesWorkoutsDao(
        database: LiftingDatabase
    ): WorkoutsDao = database.workoutsDao()

    @Provides
    fun providesBarbellsDao(
        database: LiftingDatabase
    ): BarbellsDao = database.barbellsDao()

    @Provides
    fun providesPlatesDao(
        database: LiftingDatabase
    ): PlatesDao = database.platesDao()
}