package com.lifting.app.di

import android.content.Context
import androidx.room.Room
import com.lifting.app.feature_home.data.local.AnalysisDao
import com.lifting.app.feature_home.data.local.AnalysisDatabase
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
        "analysis_database"
    ).build()

    @Provides
    @Singleton
    fun provideAnalysisDao(analysisDatabase: AnalysisDatabase): AnalysisDao = analysisDatabase.analysisDao()
}