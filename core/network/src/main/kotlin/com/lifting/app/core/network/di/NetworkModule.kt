package com.lifting.app.core.network.di

import com.lifting.app.core.network.api.LiftingApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object NetworkModule {

    @Singleton
    @Provides
    fun provideApiClient(okHttpClient: OkHttpClient): LiftingApi {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(LiftingApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(LiftingApi::class.java)
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder().addInterceptor(interceptor).build()
    }
}
