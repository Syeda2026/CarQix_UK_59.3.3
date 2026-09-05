package com.aaapp.appguru.myusedcarsaleuk.core.utils

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

private val Context.dataStore by preferencesDataStore(name = "user_preferences")

class DataStoreManager(private val context: Context) {

    companion object {
        private val KEY_ONBOARDING_COMPLETED = booleanPreferencesKey("onboarding_completed")
        private val KEY_APP_THEME_MODE = stringPreferencesKey("app_theme_mode") // "SYSTEM", "LIGHT", "DARK"
        private val KEY_HAS_RATED_APP = booleanPreferencesKey("has_rated_app")
    }

    val hasRatedApp: Flow<Boolean> = context.dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { prefs ->
            prefs[KEY_HAS_RATED_APP] ?: false
        }

    suspend fun setHasRatedApp(rated: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[KEY_HAS_RATED_APP] = rated
        }
    }

    val isOnboardingCompleted: Flow<Boolean> = context.dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { prefs ->
            prefs[KEY_ONBOARDING_COMPLETED] ?: false
        }

    suspend fun setOnboardingCompleted(completed: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[KEY_ONBOARDING_COMPLETED] = completed
        }
    }

    val appThemeMode: Flow<String> = context.dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { prefs ->
            prefs[KEY_APP_THEME_MODE] ?: "SYSTEM"
        }

    suspend fun setAppThemeMode(themeMode: String) {
        context.dataStore.edit { prefs ->
            prefs[KEY_APP_THEME_MODE] = themeMode
        }
    }
}
