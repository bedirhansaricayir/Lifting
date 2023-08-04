package com.lifting.app.di

import com.lifting.app.feature_home.data.local.repository.AnalysisRepository
import com.lifting.app.feature_home.data.local.repository.AnalysisRepositoryImpl
import com.lifting.app.feature_home.domain.repository.Repository
import com.lifting.app.feature_home.data.repository.RepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface RepositoryModule {

    @Binds
    fun repository(repositoryImpl: RepositoryImpl): Repository

    @Binds
    fun provideAnalysisRepository(analysisRepositoryImpl: AnalysisRepositoryImpl): AnalysisRepository
}