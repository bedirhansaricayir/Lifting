package com.lifting.app.core.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by bedirhansaricayir on 01.05.2025
 */

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "preferences_storage")

@Singleton
internal class PreferencesStorageImpl @Inject constructor(
    @ApplicationContext context: Context
) : PreferencesStorage {

    private val dataStore = context.dataStore

    companion object {
        private val activeWorkoutIdKey = stringPreferencesKey("active_workout_id_key")
    }

    override val activeWorkoutId: Flow<String>
        get() = dataStore.data
            .catch { exception ->
                if (exception is IOException)
                    emit(emptyPreferences())
                else throw exception
            }
            .map { preferences ->
                preferences[activeWorkoutIdKey] ?: ""
            }

    override suspend fun setActiveWorkoutId(workoutId: String) {
        dataStore.edit { preferences ->
            preferences[activeWorkoutIdKey] = workoutId
        }
    }

    override suspend fun clearPreferences() {
        dataStore.edit { it.clear() }
    }

}

interface PreferencesStorage {
    val activeWorkoutId: Flow<String>
    suspend fun setActiveWorkoutId(workoutId: String)
    suspend fun clearPreferences()
}