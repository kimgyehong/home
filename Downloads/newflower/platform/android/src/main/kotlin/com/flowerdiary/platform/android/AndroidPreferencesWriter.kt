package com.flowerdiary.platform.android

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.flowerdiary.common.utils.ExceptionUtil
import com.flowerdiary.common.utils.Logger

/**
 * Android Preferences 쓰기 전용 클래스
 * SRP: Preferences 쓰기만 담당
 */
internal class AndroidPreferencesWriter(
    private val dataStore: DataStore<Preferences>
) {

    private companion object {
        private const val TAG = "AndroidPreferencesWriter"
    }

    suspend fun putString(key: String, value: String) {
        ExceptionUtil.runCatchingSuspend {
            val preferencesKey = stringPreferencesKey(key)
            dataStore.edit { preferences ->
                preferences[preferencesKey] = value
            }
            Logger.debug(TAG, "String saved: $key")
        }
    }

    suspend fun putInt(key: String, value: Int) {
        ExceptionUtil.runCatchingSuspend {
            val preferencesKey = intPreferencesKey(key)
            dataStore.edit { preferences ->
                preferences[preferencesKey] = value
            }
            Logger.debug(TAG, "Int saved: $key = $value")
        }
    }

    suspend fun putBoolean(key: String, value: Boolean) {
        ExceptionUtil.runCatchingSuspend {
            val preferencesKey = booleanPreferencesKey(key)
            dataStore.edit { preferences ->
                preferences[preferencesKey] = value
            }
            Logger.debug(TAG, "Boolean saved: $key = $value")
        }
    }

    suspend fun putFloat(key: String, value: Float) {
        ExceptionUtil.runCatchingSuspend {
            val preferencesKey = floatPreferencesKey(key)
            dataStore.edit { preferences ->
                preferences[preferencesKey] = value
            }
            Logger.debug(TAG, "Float saved: $key = $value")
        }
    }

    suspend fun remove(key: String) {
        ExceptionUtil.runCatchingSuspend {
            dataStore.edit { preferences ->
                preferences.remove(stringPreferencesKey(key))
                preferences.remove(intPreferencesKey(key))
                preferences.remove(booleanPreferencesKey(key))
                preferences.remove(floatPreferencesKey(key))
            }
            Logger.debug(TAG, "Key removed: $key")
        }
    }

    suspend fun clear() {
        ExceptionUtil.runCatchingSuspend {
            dataStore.edit { preferences ->
                preferences.clear()
            }
            Logger.info(TAG, "All preferences cleared")
        }
    }
}
