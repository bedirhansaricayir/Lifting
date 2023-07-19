package com.lifting.app.di

import android.content.Context
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.FirebaseAuth
import com.lifting.app.data.local.datastore.DataStoreRepository
import com.lifting.app.data.remote.ApiClient
import com.lifting.app.feature_auth.data.repository.AuthRepositoryImpl
import com.lifting.app.feature_auth.domain.repository.AuthRepository
import com.lifting.app.feature_auth.presentation.google_auth.GoogleAuthUiClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {


    @Singleton
    @Provides
    fun provideApiClient(okHttpClient: OkHttpClient): ApiClient {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(ApiClient.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiClient::class.java)
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient{
        val interceptor= HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder().addInterceptor(interceptor).build()
    }

    @Provides
    @Singleton
    fun provideDataStoreRepository(
        @ApplicationContext context: Context
    ) = DataStoreRepository(context = context)

    @Provides
    @Singleton
    fun providesFirebaseAuth()  = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun providesRepositoryImpl(firebaseAuth: FirebaseAuth): AuthRepository {
        return AuthRepositoryImpl(firebaseAuth)
    }

    @Provides
    @Singleton
    fun provideOneTapClient(
        @ApplicationContext
        context: Context
    ) = Identity.getSignInClient(context)
    @Provides
    @Singleton
    fun provideGoogleAuthUiClient(
        @ApplicationContext
        context: Context,
        oneTapClient: SignInClient,
        firebaseAuth: FirebaseAuth
    ) = GoogleAuthUiClient(context,oneTapClient,firebaseAuth)
}