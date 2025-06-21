package com.lifting.app.core.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.lifting.app.core.model.AppLanguage
import com.lifting.app.core.model.AppTheme
import com.lifting.app.core.model.DistanceUnit
import com.lifting.app.core.model.LayoutType
import com.lifting.app.core.model.WeightUnit
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
        private val appThemeKey = stringPreferencesKey("app_theme_key")
        private val weightUnitKey = stringPreferencesKey("weight_unit_key")
        private val distanceUnitKey = stringPreferencesKey("distance_unit_key")
        private val languageKey = stringPreferencesKey("language_key")
        private val workoutScreenLayoutTypeKey = stringPreferencesKey("workout_screen_layout_type_key")
    }

    override val activeWorkoutId: Flow<String>
        get() = dataStore.data
            .catch { exception ->
                if (exception is IOException)
                    emit(emptyPreferences())
                else throw exception
            }
            .map { preferences ->
                preferences[activeWorkoutIdKey] ?: "none"
            }

    override suspend fun setActiveWorkoutId(workoutId: String) {
        dataStore.edit { preferences ->
            preferences[activeWorkoutIdKey] = workoutId
        }
    }

    override val appTheme: Flow<AppTheme>
        get() = dataStore.data
            .catch { exception ->
                if (exception is IOException)
                    emit(emptyPreferences())
                else throw exception
            }
            .map { preferences ->
                when (preferences[appThemeKey]) {
                    AppTheme.Light.name -> AppTheme.Light
                    AppTheme.Dark.name -> AppTheme.Dark
                    AppTheme.System.name -> AppTheme.System
                    else -> AppTheme.System
                }
            }

    override suspend fun setAppTheme(appTheme: AppTheme) {
        dataStore.edit { preferences ->
            preferences[appThemeKey] = appTheme.name
        }
    }

    override val weightUnit: Flow<WeightUnit>
        get() = dataStore.data
            .catch { exception ->
                if (exception is IOException)
                    emit(emptyPreferences())
                else throw exception
            }
            .map { preferences ->
                when (preferences[weightUnitKey]) {
                    WeightUnit.Kg.name -> WeightUnit.Kg
                    WeightUnit.Lbs.name -> WeightUnit.Lbs
                    else -> WeightUnit.Kg
                }
            }

    override suspend fun setWeightUnit(weightUnit: WeightUnit) {
        dataStore.edit { preferences ->
            preferences[weightUnitKey] = weightUnit.name
        }
    }

    override val distanceUnit: Flow<DistanceUnit>
        get() = dataStore.data
            .catch { exception ->
                if (exception is IOException)
                    emit(emptyPreferences())
                else throw exception
            }
            .map { preferences ->
                when (preferences[distanceUnitKey]) {
                    DistanceUnit.Km.name -> DistanceUnit.Km
                    DistanceUnit.Miles.name -> DistanceUnit.Miles
                    else -> DistanceUnit.Km
                }
            }

    override suspend fun setDistanceUnit(distanceUnit: DistanceUnit) {
        dataStore.edit { preferences ->
            preferences[distanceUnitKey] = distanceUnit.name
        }
    }

    override val appLanguage: Flow<AppLanguage>
        get() = dataStore.data
            .catch { exception ->
                if (exception is IOException)
                    emit(emptyPreferences())
                else throw exception
            }
            .map { preferences ->
                when (preferences[languageKey]) {
                    AppLanguage.English.code -> AppLanguage.English
                    AppLanguage.Turkish.code -> AppLanguage.Turkish
                    else -> AppLanguage.English
                }
            }

    override suspend fun setAppLanguage(language: AppLanguage) {
        dataStore.edit { preferences ->
            preferences[languageKey] = language.code
        }
    }

    override val workoutScreenLayoutType: Flow<LayoutType>
        get() = dataStore.data
            .catch { exception ->
                if (exception is IOException)
                    emit(emptyPreferences())
                else throw exception
            }
            .map { preferences ->
                when (preferences[workoutScreenLayoutTypeKey]) {
                    LayoutType.List.name -> LayoutType.List
                    LayoutType.Grid.name -> LayoutType.Grid
                    else -> LayoutType.Grid
                }
            }

    override suspend fun setWorkoutScreenLayoutType(layoutType: LayoutType) {
        dataStore.edit { preferences ->
            preferences[workoutScreenLayoutTypeKey] = layoutType.name
        }
    }

    override suspend fun clearPreferences() {
        dataStore.edit { it.clear() }
    }

}

interface PreferencesStorage {
    val activeWorkoutId: Flow<String>
    suspend fun setActiveWorkoutId(workoutId: String)
    val appTheme: Flow<AppTheme>
    suspend fun setAppTheme(appTheme: AppTheme)
    val weightUnit : Flow<WeightUnit>
    suspend fun setWeightUnit(weightUnit: WeightUnit)
    val distanceUnit : Flow<DistanceUnit>
    suspend fun setAppLanguage(language: AppLanguage)
    val appLanguage : Flow<AppLanguage>
    suspend fun setDistanceUnit(distanceUnit: DistanceUnit)
    val workoutScreenLayoutType: Flow<LayoutType>
    suspend fun setWorkoutScreenLayoutType(layoutType: LayoutType)
    suspend fun clearPreferences()
}