package com.flowerdiary.platform.ios

import com.flowerdiary.common.platform.PreferencesStore
import com.flowerdiary.common.utils.ExceptionUtil
import platform.Foundation.NSUserDefaults

/**
 * iOS 환경설정 저장소 구현
 * SRP: iOS 플랫폼의 키-값 저장/읽기만 담당
 * NSUserDefaults를 사용하여 설정 데이터 관리
 */
actual class IOSPreferencesStore : PreferencesStore {
    
    private val userDefaults = NSUserDefaults.standardUserDefaults
    
    override suspend fun getString(key: String, defaultValue: String?): Result<String?> =
        ExceptionUtil.runCatchingSuspend {
            val value = userDefaults.stringForKey(key)
            value ?: defaultValue
        }
    
    override suspend fun putString(key: String, value: String): Result<Unit> =
        ExceptionUtil.runCatchingSuspend {
            userDefaults.setObject(value, forKey = key)
            userDefaults.synchronize()
        }
    
    override suspend fun getInt(key: String, defaultValue: Int): Result<Int> =
        ExceptionUtil.runCatchingSuspend {
            if (userDefaults.objectForKey(key) != null) {
                userDefaults.integerForKey(key).toInt()
            } else {
                defaultValue
            }
        }
    
    override suspend fun putInt(key: String, value: Int): Result<Unit> =
        ExceptionUtil.runCatchingSuspend {
            userDefaults.setInteger(value.toLong(), forKey = key)
            userDefaults.synchronize()
        }
    
    override suspend fun getLong(key: String, defaultValue: Long): Result<Long> =
        ExceptionUtil.runCatchingSuspend {
            if (userDefaults.objectForKey(key) != null) {
                userDefaults.integerForKey(key)
            } else {
                defaultValue
            }
        }
    
    override suspend fun putLong(key: String, value: Long): Result<Unit> =
        ExceptionUtil.runCatchingSuspend {
            userDefaults.setInteger(value, forKey = key)
            userDefaults.synchronize()
        }
    
    override suspend fun getFloat(key: String, defaultValue: Float): Result<Float> =
        ExceptionUtil.runCatchingSuspend {
            if (userDefaults.objectForKey(key) != null) {
                userDefaults.floatForKey(key)
            } else {
                defaultValue
            }
        }
    
    override suspend fun putFloat(key: String, value: Float): Result<Unit> =
        ExceptionUtil.runCatchingSuspend {
            userDefaults.setFloat(value, forKey = key)
            userDefaults.synchronize()
        }
    
    override suspend fun getBoolean(key: String, defaultValue: Boolean): Result<Boolean> =
        ExceptionUtil.runCatchingSuspend {
            if (userDefaults.objectForKey(key) != null) {
                userDefaults.boolForKey(key)
            } else {
                defaultValue
            }
        }
    
    override suspend fun putBoolean(key: String, value: Boolean): Result<Unit> =
        ExceptionUtil.runCatchingSuspend {
            userDefaults.setBool(value, forKey = key)
            userDefaults.synchronize()
        }
    
    override suspend fun remove(key: String): Result<Unit> =
        ExceptionUtil.runCatchingSuspend {
            userDefaults.removeObjectForKey(key)
            userDefaults.synchronize()
        }
    
    override suspend fun clear(): Result<Unit> =
        ExceptionUtil.runCatchingSuspend {
            val dictionary = userDefaults.dictionaryRepresentation()
            dictionary.keys.forEach { key ->
                userDefaults.removeObjectForKey(key as String)
            }
            userDefaults.synchronize()
        }
    
    override suspend fun contains(key: String): Result<Boolean> =
        ExceptionUtil.runCatchingSuspend {
            userDefaults.objectForKey(key) != null
        }
}