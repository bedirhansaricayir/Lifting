package com.lifting.app.di

import android.content.Context
import com.lifting.app.feature_notification.data.AlarmSchedulerImpl
import com.lifting.app.feature_notification.domain.AlarmScheduler
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AlarmModule {
    @Provides
    fun provideAlarmScheduler(@ApplicationContext context: Context): AlarmScheduler = AlarmSchedulerImpl(context)
}