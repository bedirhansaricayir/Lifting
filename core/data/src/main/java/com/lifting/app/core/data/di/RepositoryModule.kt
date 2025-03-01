package com.lifting.app.core.data.di

import com.lifting.app.core.data.repository.exercises.ExercisesRepository
import com.lifting.app.core.data.repository.exercises.ExercisesRepositoryImpl
import com.lifting.app.core.data.repository.muscles.MusclesRepository
import com.lifting.app.core.data.repository.muscles.MusclesRepositoryImpl
import com.lifting.app.core.data.repository.workout_template.WorkoutTemplateRepository
import com.lifting.app.core.data.repository.workout_template.WorkoutTemplateRepositoryImpl
import com.lifting.app.core.data.repository.workouts.WorkoutsRepository
import com.lifting.app.core.data.repository.workouts.WorkoutsRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Created by bedirhansaricayir on 18.07.2024
 */

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    internal abstract fun bindsExercisesRepository(
        exercisesRepository: ExercisesRepositoryImpl
    ): ExercisesRepository

    @Binds
    internal abstract fun bindsMusclesRepository(
        musclesRepository: MusclesRepositoryImpl
    ): MusclesRepository

    @Binds
    internal abstract fun bindsWorkoutTemplateRepository(
        workoutTemplateRepository: WorkoutTemplateRepositoryImpl
    ): WorkoutTemplateRepository

    @Binds
    internal abstract fun bindsWorkoutsRepository(
        workoutsRepositoryImpl: WorkoutsRepositoryImpl
    ): WorkoutsRepository
}