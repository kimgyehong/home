package com.flowerdiary.platform.android

/**
 * String 타입 Preferences 처리
 * SRP: String 타입 저장/조회만 담당
 */
internal class StringPreferencesHandler(
    private val reader: AndroidPreferencesReader,
    private val writer: AndroidPreferencesWriter
) {
    suspend fun getString(key: String): String? = reader.getString(key)
    suspend fun putString(key: String, value: String) = writer.putString(key, value)
}
