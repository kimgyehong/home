package com.flowerdiary.common.platform

/**
 * 플랫폼별 설정 저장소 인터페이스
 * 키-값 쌍으로 앱 설정 관리
 * 순수 Kotlin 인터페이스 - platform 모듈에서 구현
 */
interface PreferencesStore {
    suspend fun getString(key: String): String?
    suspend fun putString(key: String, value: String)
    suspend fun getInt(key: String, defaultValue: Int = 0): Int
    suspend fun putInt(key: String, value: Int)
    suspend fun getBoolean(key: String, defaultValue: Boolean = false): Boolean
    suspend fun putBoolean(key: String, value: Boolean)
    suspend fun getFloat(key: String, defaultValue: Float = 0f): Float
    suspend fun putFloat(key: String, value: Float)
    suspend fun remove(key: String)
    suspend fun clear()
}
