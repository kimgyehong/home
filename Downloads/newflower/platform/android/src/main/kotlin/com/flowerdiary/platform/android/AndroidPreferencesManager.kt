package com.flowerdiary.platform.android

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences

/**
 * Android Preferences 관리자
 * SRP: Preferences 전체 관리와 위임만 담당
 */
internal class AndroidPreferencesManager(
    private val dataStore: DataStore<Preferences>
) {

    private val reader = AndroidPreferencesReader(dataStore)
    private val writer = AndroidPreferencesWriter(dataStore)

    suspend fun getString(key: String): String? = reader.getString(key)

    suspend fun putString(key: String, value: String) = writer.putString(key, value)

    suspend fun getInt(key: String, defaultValue: Int): Int =
        reader.getInt(key, defaultValue)

    suspend fun putInt(key: String, value: Int) = writer.putInt(key, value)

    suspend fun getBoolean(key: String, defaultValue: Boolean): Boolean =
        reader.getBoolean(key, defaultValue)

    suspend fun putBoolean(key: String, value: Boolean) =
        writer.putBoolean(key, value)

    suspend fun getFloat(key: String, defaultValue: Float): Float =
        reader.getFloat(key, defaultValue)

    suspend fun putFloat(key: String, value: Float) = writer.putFloat(key, value)

    suspend fun remove(key: String) = writer.remove(key)
}
