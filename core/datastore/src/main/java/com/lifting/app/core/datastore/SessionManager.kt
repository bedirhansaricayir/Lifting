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

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "session_prefs")
private typealias Token = Flow<String>

@Singleton
internal class SessionManagerImpl @Inject constructor(
    @ApplicationContext context: Context,
) : SessionManager {

    private val dataStore = context.dataStore

    companion object {
        private val tokenKey = stringPreferencesKey("token_key")
    }

    override suspend fun saveToken(token: String) {
        dataStore.edit { preferences ->
            preferences[tokenKey] = token
        }
    }

    override suspend fun getToken(): Token {
        return dataStore.data
            .catch { exception ->
                if (exception is IOException)
                    emit(emptyPreferences())
                else throw exception
            }
            .map { preferences ->
                preferences[tokenKey] ?: ""
            }
    }

    override suspend fun clearToken() {
        dataStore.edit { preferences ->
            preferences.remove(tokenKey)
        }
    }

}

interface SessionManager {
    suspend fun saveToken(token: String)
    suspend fun getToken(): Token
    suspend fun clearToken()
}
