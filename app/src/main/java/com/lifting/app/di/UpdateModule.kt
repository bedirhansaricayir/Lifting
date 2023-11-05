package com.lifting.app.di

import android.content.Context
import com.lifting.app.AppUpdateController
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
object UpdateModule {
    @Provides
    @ActivityScoped
    fun provideAppUpdateController(@ActivityContext context: Context): AppUpdateController {
        return AppUpdateController(context)
    }
}