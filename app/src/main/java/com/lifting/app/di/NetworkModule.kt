package com.lifting.app.di

import android.content.Context
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.lifting.app.feature_home.data.local.datastore.DataStoreRepository
import com.lifting.app.feature_home.data.remote.ApiClient
import com.lifting.app.feature_auth.data.repository.AuthRepositoryImpl
import com.lifting.app.feature_auth.domain.repository.AuthRepository
import com.lifting.app.feature_auth.presentation.google_auth.GoogleAuthUiClient
import com.lifting.app.feature_home.data.repository.FirebaseRepositoryImpl
import com.lifting.app.feature_home.domain.repository.FirebaseRepository
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
    fun providesFirebaseFirestore() = Firebase.firestore

    @Provides
    @Singleton
    fun providesRepositoryImpl(firebaseAuth: FirebaseAuth, firestore: FirebaseFirestore): AuthRepository {
        return AuthRepositoryImpl(firebaseAuth, firestore)
    }

    @Provides
    @Singleton
    fun providesFirebaseRepositoryImpl(firebaseAuth: FirebaseAuth,firestore: FirebaseFirestore): FirebaseRepository {
        return FirebaseRepositoryImpl(firebaseAuth,firestore)
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