package com.lifting.app.core.database.di

import android.content.Context
import androidx.room.Room
import com.lifting.app.core.database.LiftingDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
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
    ): LiftingDatabase = Room.databaseBuilder(
        context,
        LiftingDatabase::class.java,
        "Lifting.db",
    ).fallbackToDestructiveMigration()
        .build()
}