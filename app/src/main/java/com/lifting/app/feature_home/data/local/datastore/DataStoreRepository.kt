package com.lifting.app.feature_home.data.local.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import com.lifting.app.common.constants.Constants.Companion.ONBOARDING
import com.lifting.app.common.constants.Constants.Companion.ONBOARDING_COMPLETED
import com.lifting.app.common.constants.Constants.Companion.SUCCESSFULLY_SIGN
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = ONBOARDING)

class DataStoreRepository(context: Context) {

    private object PreferencesKey {
        val onBoardingKey = booleanPreferencesKey(name = ONBOARDING_COMPLETED)
        val successfullySignInKey = booleanPreferencesKey(name = SUCCESSFULLY_SIGN)
    }

    private val dataStore = context.dataStore

    suspend fun saveOnBoardingState(completed: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.onBoardingKey] = completed
        }
    }

    suspend fun saveSuccessfullySignInState(completed: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.successfullySignInKey] = completed
        }
    }

    fun readOnBoardingState(): Flow<Boolean> {
        return dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { preferences ->
                val onBoardingState = preferences[PreferencesKey.onBoardingKey] ?: false
                onBoardingState
            }
    }

    fun readSuccessfullySignInState(): Flow<Boolean> {
        return dataStore.data
            .catch {exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { preferences ->
                val successfullySignInState = preferences[PreferencesKey.successfullySignInKey] ?: false
                successfullySignInState
            }
    }
}