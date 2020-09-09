package com.shalu.android.datastore.proto

import androidx.datastore.CorruptionException
import androidx.datastore.Serializer
import com.google.protobuf.InvalidProtocolBufferException
import com.shalu.android.datastore.UserSettings
import java.io.InputStream
import java.io.OutputStream


object ProtoSettingsSerializer : Serializer<UserSettings> {

    override fun readFrom(input: InputStream): UserSettings {
        try {
            return UserSettings.parseFrom(input)
        } catch (ex: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", ex)
        }
    }

    override fun writeTo(t: UserSettings, output: OutputStream) {
        t.writeTo(output)
    }

}