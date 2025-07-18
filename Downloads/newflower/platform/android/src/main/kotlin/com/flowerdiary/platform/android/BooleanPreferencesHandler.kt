package com.flowerdiary.platform.android

/**
 * Boolean 타입 Preferences 처리
 * SRP: Boolean 타입 저장/조회만 담당
 */
internal class BooleanPreferencesHandler(
    private val reader: AndroidPreferencesReader,
    private val writer: AndroidPreferencesWriter
) {
    suspend fun getBoolean(key: String, defaultValue: Boolean): Boolean =
        reader.getBoolean(key, defaultValue)
    suspend fun putBoolean(key: String, value: Boolean) = writer.putBoolean(key, value)
}
