package com.lifting.app.core.datastore.di

import android.content.Context
import com.lifting.app.core.datastore.PreferencesStorage
import com.lifting.app.core.datastore.PreferencesStorageImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by bedirhansaricayir on 01.05.2025
 */

@Module
@InstallIn(SingletonComponent::class)
internal object PreferencesStorageModule {

    @Singleton
    @Provides
    fun providePreferencesStorage(@ApplicationContext context: Context): PreferencesStorage =
        PreferencesStorageImpl(context)
}