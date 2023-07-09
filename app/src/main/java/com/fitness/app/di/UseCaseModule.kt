package com.fitness.app.di

import com.fitness.app.feature_auth.domain.use_case.AuthenticationUseCase
import com.fitness.app.feature_auth.domain.use_case.EmailInputValidationUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideAuthenticationUseCase(): AuthenticationUseCase = AuthenticationUseCase()

    @Provides
    @Singleton
    fun provideEmailInputValidationUseCase(): EmailInputValidationUseCase = EmailInputValidationUseCase()
}