package com.lifting.app.di

import android.content.Context
import androidx.room.Room
import com.lifting.app.common.constants.DbConstants.ANALYSIS_DATABASE
import com.lifting.app.feature_tracker.data.local.AnalysisDao
import com.lifting.app.feature_tracker.data.local.entity.AnalysisDatabase
import com.lifting.app.feature_home.data.local.datastore.DataStoreRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext
        context: Context
    ) = Room.databaseBuilder(
        context,
        AnalysisDatabase::class.java,
        ANALYSIS_DATABASE
    ).fallbackToDestructiveMigration()
        .allowMainThreadQueries()
        .build()

    @Provides
    @Singleton
    fun provideAnalysisDao(analysisDatabase: AnalysisDatabase): AnalysisDao = analysisDatabase.analysisDao()

    @Provides
    @Singleton
    fun provideDataStoreRepository(
        @ApplicationContext context: Context
    ) = DataStoreRepository(context = context)
}