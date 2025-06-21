package com.lifting.app.core.database.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.lifting.app.core.database.LiftingDatabase
import com.lifting.app.core.database.callback.DatabasePrePopulateCallback
import com.lifting.app.core.database.dao.BarbellsDao
import com.lifting.app.core.database.dao.ExercisesDao
import com.lifting.app.core.database.dao.PlatesDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Provider
import javax.inject.Singleton

/**
 * Created by bedirhansaricayir on 18.07.2024
 */


@Module
@InstallIn(SingletonComponent::class)
internal object DatabaseModule {

    @Provides
    @Singleton
    fun providesLiftingDatabase(
        @ApplicationContext context: Context,
        exercisesProvider: Provider<ExercisesDao>,
        barbellsProvider: Provider<BarbellsDao>,
        platesProvider: Provider<PlatesDao>,
    ): LiftingDatabase = Room.databaseBuilder(
        context,
        LiftingDatabase::class.java,
        "Lifting.db",
    ).fallbackToDestructiveMigration()
        .setJournalMode(RoomDatabase.JournalMode.TRUNCATE)
        .addCallback(DatabasePrePopulateCallback(context, exercisesProvider, barbellsProvider, platesProvider))
        .build()
}