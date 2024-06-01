package com.lifting.app.core.datastore.di

import android.content.Context
import com.lifting.app.core.datastore.SessionManager
import com.lifting.app.core.datastore.SessionManagerImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object SessionManagerModule {

    @Singleton
    @Provides
    fun provideSessionManager(@ApplicationContext context: Context): SessionManager =
        SessionManagerImpl(context)
}

@Module
@InstallIn(SingletonComponent::class)
internal interface SessionManagerBindsModule {
    @Binds
    fun bindSessionManager(sessionManager: SessionManagerImpl): SessionManager
}