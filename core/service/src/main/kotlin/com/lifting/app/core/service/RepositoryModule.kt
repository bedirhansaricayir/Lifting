package com.lifting.app.core.service

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Created by bedirhansaricayir on 11.06.2025
 */

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    internal abstract fun bindsRestTimerRepository(
        restTimerRepositoryImpl: RestTimerRepositoryImpl
    ): RestTimerRepository
}