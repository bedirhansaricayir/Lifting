package com.lifting.app.di

import android.content.Context
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
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
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {

    @Provides
    @Singleton
    fun providesFirebaseAuth()  = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun providesFirebaseFirestore() = Firebase.firestore

    @Provides
    @Singleton
    fun providesFirebaseStorage() = FirebaseStorage.getInstance()
    @Provides
    @Singleton
    fun providesRepositoryImpl(firebaseAuth: FirebaseAuth, firestore: FirebaseFirestore): AuthRepository {
        return AuthRepositoryImpl(firebaseAuth, firestore)
    }

    @Provides
    @Singleton
    fun providesFirebaseRepositoryImpl(firebaseAuth: FirebaseAuth, firestore: FirebaseFirestore, storage: FirebaseStorage): FirebaseRepository {
        return FirebaseRepositoryImpl(firebaseAuth,firestore,storage)
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