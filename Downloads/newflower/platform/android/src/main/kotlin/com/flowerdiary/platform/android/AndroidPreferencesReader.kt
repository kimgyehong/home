package com.flowerdiary.platform.android

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.flowerdiary.common.utils.ExceptionUtil
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

/**
 * Android Preferences 읽기 전용 클래스
 * SRP: Preferences 읽기만 담당
 */
internal class AndroidPreferencesReader(
    private val dataStore: DataStore<Preferences>
) {

    suspend fun getString(key: String): String? =
        ExceptionUtil.runCatchingSuspend {
            val preferencesKey = stringPreferencesKey(key)
            dataStore.data
                .map { preferences -> preferences[preferencesKey] }
                .first()
        }.getOrNull()

    suspend fun getInt(key: String, defaultValue: Int): Int =
        ExceptionUtil.runCatchingSuspend {
            val preferencesKey = intPreferencesKey(key)
            dataStore.data
                .map { preferences -> preferences[preferencesKey] ?: defaultValue }
                .first()
        }.getOrDefault(defaultValue)

    suspend fun getBoolean(key: String, defaultValue: Boolean): Boolean =
        ExceptionUtil.runCatchingSuspend {
            val preferencesKey = booleanPreferencesKey(key)
            dataStore.data
                .map { preferences -> preferences[preferencesKey] ?: defaultValue }
                .first()
        }.getOrDefault(defaultValue)

    suspend fun getFloat(key: String, defaultValue: Float): Float =
        ExceptionUtil.runCatchingSuspend {
            val preferencesKey = floatPreferencesKey(key)
            dataStore.data
                .map { preferences -> preferences[preferencesKey] ?: defaultValue }
                .first()
        }.getOrDefault(defaultValue)
}
