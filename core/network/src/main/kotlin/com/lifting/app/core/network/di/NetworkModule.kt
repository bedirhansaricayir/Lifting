package com.lifting.app.core.network.di

import com.lifting.app.core.common.dispatchers.Dispatcher
import com.lifting.app.core.common.dispatchers.LiftingDispatchers.IO
import com.lifting.app.core.common.dispatchers.di.ApplicationScope
import com.lifting.app.core.datastore.SessionManager
import com.lifting.app.core.network.api.LiftingApi
import com.lifting.app.core.network.interceptor.AuthInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object NetworkModule {
    @Singleton
    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Singleton
    @Provides
    fun provideAuthInterceptor(
        sessionManager: SessionManager,
        @Dispatcher(IO) ioDispatcher: CoroutineDispatcher,
        @ApplicationScope scope: CoroutineScope
    ): AuthInterceptor = AuthInterceptor(
        sessionManager = sessionManager,
        ioDispatcher = ioDispatcher,
        scope = scope
    )

    @Singleton
    @Provides
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        authInterceptor: AuthInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(loggingInterceptor)
            .addInterceptor(authInterceptor).build()
    }

    @Singleton
    @Provides
    fun provideConverterFactory(): Converter.Factory {
        return GsonConverterFactory.create()
    }

    @Singleton
    @Provides
    fun provideApiClient(
        okHttpClient: OkHttpClient,
        converterFactory: Converter.Factory
    ): LiftingApi {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(LiftingApi.BASE_URL)
            .addConverterFactory(converterFactory)
            .build()
            .create(LiftingApi::class.java)
    }

}
