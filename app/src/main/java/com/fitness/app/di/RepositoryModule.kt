package com.fitness.app.di

import com.fitness.app.data.repository.Repository
import com.fitness.app.data.repository.RepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface RepositoryModule {

    @Binds
    fun repository(repositoryImpl: RepositoryImpl): Repository
}