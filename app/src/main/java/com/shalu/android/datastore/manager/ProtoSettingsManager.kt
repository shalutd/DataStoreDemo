package com.shalu.android.datastore.manager

import android.content.Context
import androidx.datastore.DataStore
import androidx.datastore.createDataStore
import com.shalu.android.datastore.UserSettings
import com.shalu.android.datastore.proto.ProtoSettingsSerializer
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
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