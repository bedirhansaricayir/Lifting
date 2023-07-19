package com.lifting.app.di

import com.lifting.app.feature_auth.data.repository.AuthRepositoryImpl
import com.lifting.app.feature_auth.domain.use_case.AuthenticationUseCase
import com.lifting.app.feature_auth.domain.use_case.EmailInputValidationUseCase
import com.lifting.app.feature_auth.domain.use_case.ReloadUserUseCase
import com.lifting.app.feature_auth.domain.use_case.SendEmailVerificationUseCase
import com.lifting.app.feature_auth.domain.use_case.SendPasswordResetUseCase
import com.lifting.app.feature_auth.domain.use_case.SignInUseCase
import com.lifting.app.feature_auth.domain.use_case.SignUpUseCase
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
    fun provideAuthenticationUseCase(authRepositoryImpl: AuthRepositoryImpl): AuthenticationUseCase = AuthenticationUseCase(authRepositoryImpl)

    @Provides
    @Singleton
    fun provideReloadUserUseCase(authRepositoryImpl: AuthRepositoryImpl): ReloadUserUseCase = ReloadUserUseCase(authRepositoryImpl)

    @Provides
    @Singleton
    fun provideSendEmailVerificationUseCase(authRepositoryImpl: AuthRepositoryImpl): SendEmailVerificationUseCase = SendEmailVerificationUseCase(authRepositoryImpl)

    @Provides
    @Singleton
    fun provideSendPasswordResetUseCase(authRepositoryImpl: AuthRepositoryImpl): SendPasswordResetUseCase = SendPasswordResetUseCase(authRepositoryImpl)

    @Provides
    @Singleton
    fun provideSignInUseCase(authRepositoryImpl: AuthRepositoryImpl): SignInUseCase = SignInUseCase(authRepositoryImpl)

    @Provides
    @Singleton
    fun provideSignUpUseCase(authRepositoryImpl: AuthRepositoryImpl): SignUpUseCase = SignUpUseCase(authRepositoryImpl)

    @Provides
    @Singleton
    fun provideEmailInputValidationUseCase(): EmailInputValidationUseCase = EmailInputValidationUseCase()
}