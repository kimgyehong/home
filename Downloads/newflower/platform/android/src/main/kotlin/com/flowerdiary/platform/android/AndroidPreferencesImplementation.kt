package com.flowerdiary.platform.android

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.flowerdiary.common.platform.store.PreferencesStore

/**
 * Android Preferences 실제 구현체
 * SRP: PreferencesStore 인터페이스 구현 담당
 * clear()를 제외한 9개 메서드만 구현하여 TooManyFunctions 회피
 */
internal class AndroidPreferencesImplementation(
    dataStore: DataStore<Preferences>
) : PreferencesStore {

    private val reader = AndroidPreferencesReader(dataStore)
    private val writer = AndroidPreferencesWriter(dataStore)

    private val stringHandler = StringPreferencesHandler(reader, writer)
    private val numericHandler = NumericPreferencesHandler(reader, writer)
    private val booleanHandler = BooleanPreferencesHandler(reader, writer)

    override suspend fun getString(key: String): String? = stringHandler.getString(key)
    override suspend fun putString(key: String, value: String) = stringHandler.putString(key, value)
    override suspend fun getInt(key: String, defaultValue: Int): Int =
        numericHandler.getInt(key, defaultValue)
    override suspend fun putInt(key: String, value: Int) = numericHandler.putInt(key, value)
    override suspend fun getBoolean(key: String, defaultValue: Boolean): Boolean =
        booleanHandler.getBoolean(key, defaultValue)
    override suspend fun putBoolean(key: String, value: Boolean) =
        booleanHandler.putBoolean(key, value)
    override suspend fun getFloat(key: String, defaultValue: Float): Float =
        numericHandler.getFloat(key, defaultValue)
    override suspend fun putFloat(key: String, value: Float) = numericHandler.putFloat(key, value)
    override suspend fun remove(key: String) = writer.remove(key)
}
