package com.shalu.android.datastore.pref

import android.content.Context
import androidx.datastore.preferences.*
import com.shalu.android.datastore.UserPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

class PrefSettingsManager(val context: Context) {
    private val dataStore = context.createDataStore(SETTINGS_PREF)

    val userPreferencesFlow: Flow<UserPreferences> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->
            val color = preferences[BG_COLOR]?: android.R.color.white
            UserPreferences(color)
        }

    suspend fun updateColor(color: Int) {
        dataStore.edit { preferences ->
            preferences[BG_COLOR] = color
        }
    }

    companion object {
        val BG_COLOR = preferencesKey<Int>("background_color")
        const val SETTINGS_PREF = "settings_pref"
    }
}