package com.flowerdiary.platform.android

/**
 * 숫자 타입 Preferences 처리
 * SRP: Int/Float 타입 저장/조회만 담당
 */
internal class NumericPreferencesHandler(
    private val reader: AndroidPreferencesReader,
    private val writer: AndroidPreferencesWriter
) {
    suspend fun getInt(key: String, defaultValue: Int): Int = reader.getInt(key, defaultValue)
    suspend fun putInt(key: String, value: Int) = writer.putInt(key, value)
    suspend fun getFloat(key: String, defaultValue: Float): Float =
        reader.getFloat(key, defaultValue)
    suspend fun putFloat(key: String, value: Float) = writer.putFloat(key, value)
}
