package com.lifting.app.core.network.di

import com.lifting.app.core.network.LiftingRemoteDataSource
import com.lifting.app.core.network.source.RemoteDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface DataSourceModule {

    @Binds
    fun bindsNetworkDataSource(dataSource: LiftingRemoteDataSource): RemoteDataSource
}