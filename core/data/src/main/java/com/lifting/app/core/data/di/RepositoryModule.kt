package com.lifting.app.core.data.di

import android.content.Context
import com.lifting.app.core.data.reader.AssetReader
import com.lifting.app.core.data.reader.AssetReaderImpl
import com.lifting.app.core.data.repository.barbells.BarbellsRepository
import com.lifting.app.core.data.repository.barbells.BarbellsRepositoryImpl
import com.lifting.app.core.data.repository.exercises.ExercisesRepository
import com.lifting.app.core.data.repository.exercises.ExercisesRepositoryImpl
import com.lifting.app.core.data.repository.plates.PlatesRepository
import com.lifting.app.core.data.repository.plates.PlatesRepositoryImpl
import com.lifting.app.core.data.repository.workout_template.WorkoutTemplateRepository
import com.lifting.app.core.data.repository.workout_template.WorkoutTemplateRepositoryImpl
import com.lifting.app.core.data.repository.workouts.WorkoutsRepository
import com.lifting.app.core.data.repository.workouts.WorkoutsRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import javax.inject.Singleton

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
    internal abstract fun bindsWorkoutTemplateRepository(
        workoutTemplateRepository: WorkoutTemplateRepositoryImpl
    ): WorkoutTemplateRepository

    @Binds
    internal abstract fun bindsWorkoutsRepository(
        workoutsRepositoryImpl: WorkoutsRepositoryImpl
    ): WorkoutsRepository

    @Binds
    internal abstract fun bindsPlatesRepository(
        platesRepositoryImpl: PlatesRepositoryImpl
    ): PlatesRepository

    @Binds
    internal abstract fun bindsBarbellsRepository(
        barbellsRepositoryImpl: BarbellsRepositoryImpl
    ): BarbellsRepository

}

@Module
@InstallIn(SingletonComponent::class)
object AssetReaderModule {
    @Provides
    @Singleton
    fun provideReader(@ApplicationContext context: Context, json: Json): AssetReader {
        return AssetReaderImpl(context, json)
    }

    @Provides
    @Singleton
    fun provideJson(): Json {
        val json by lazy {
            Json {
                isLenient = true
                prettyPrint = true
                ignoreUnknownKeys = true
            }
        }
        return json
    }
}