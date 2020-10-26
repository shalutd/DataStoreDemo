package com.shalu.android.datastore.manager

import android.content.Context
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.createDataStore
import androidx.datastore.preferences.emptyPreferences
import androidx.datastore.preferences.preferencesKey
import com.shalu.android.datastore.pref.UserDetails
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

class MigrationManager(context: Context) {
    val dataStore = context.createDataStore(
        name = USER_PREFERENCES_NAME,
        migrations = listOf(SharedPreferencesMigration(context,
            USER_PREFERENCES_NAME
        ))
    )

    val userDetailsFlow: Flow<UserDetails> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map {
            val firstName = it[PREF_FIRST_NAME] ?: "testFirstName"
            val lastName = it[PREF_LAST_NAME] ?: "testLastName"
            UserDetails(firstName, lastName)
        }

    companion object {
        const val USER_PREFERENCES_NAME = "my_preference"
        const val USER_FIRST_NAME = "my_firstname"
        const val USER_LAST_NAME = "my_lastname"

        val PREF_FIRST_NAME = preferencesKey<String>(USER_FIRST_NAME)
        val PREF_LAST_NAME = preferencesKey<String>(USER_LAST_NAME)
    }
}