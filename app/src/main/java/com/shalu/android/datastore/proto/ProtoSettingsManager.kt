package com.shalu.android.datastore.proto

import android.content.Context
import androidx.datastore.DataStore
import androidx.datastore.createDataStore
import androidx.datastore.preferences.*
import com.shalu.android.datastore.UserPreferences
import com.shalu.android.datastore.UserSettings
import com.shalu.android.datastore.pref.PrefSettingsManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

class ProtoSettingsManager(val context: Context) {
    private val dataStore: DataStore<UserSettings> =
        context.createDataStore(fileName = "user_settings.pb", serializer = ProtoSettingsSerializer)

    val userSettingsFlow: Flow<UserSettings> = dataStore.data.catch { ex ->
        if (ex is IOException) {
            emit(UserSettings.getDefaultInstance())
        } else {
            throw ex
        }
    }
    suspend fun updateColor(bgColor: UserSettings.BgColor) {
        dataStore.updateData { userSetting ->
            userSetting.toBuilder().setBgColor(bgColor).build()
        }
    }
}