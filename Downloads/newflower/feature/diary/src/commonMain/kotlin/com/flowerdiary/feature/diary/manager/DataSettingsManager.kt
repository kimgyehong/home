package com.flowerdiary.feature.diary.manager

import com.flowerdiary.common.constants.Messages
import com.flowerdiary.common.constants.PreferencesConfig
import com.flowerdiary.common.platform.FileStore
import com.flowerdiary.common.platform.PreferencesStore
import com.flowerdiary.common.utils.ExceptionUtil
import com.flowerdiary.domain.repository.DiaryRepository

/**
 * 데이터 설정 관리자
 * SRP: 캐시, 데이터 초기화 등 데이터 관련 설정만 담당
 */
class DataSettingsManager(
    private val preferencesStore: PreferencesStore,
    private val fileStore: FileStore,
    private val diaryRepository: DiaryRepository
) {
    
    /**
     * 캐시 삭제
     */
    suspend fun clearCache(): Result<String> {
        return ExceptionUtil.runCatchingSuspend {
            fileStore.clearCache()
            Messages.SUCCESS_CACHE_CLEARED
        }
    }
    
    /**
     * 모든 데이터 초기화
     */
    suspend fun resetAllData(): Result<String> {
        return ExceptionUtil.runCatchingSuspend {
            // 다이어리 데이터 삭제
            diaryRepository.deleteAll()
            
            // 설정 초기화
            clearAllPreferences()
            
            // 캐시 삭제
            fileStore.clearCache()
            
            Messages.SUCCESS_DATA_RESET
        }
    }
    
    /**
     * 알림 설정 토글
     */
    suspend fun toggleNotifications(currentEnabled: Boolean): Result<Boolean> {
        return ExceptionUtil.runCatchingSuspend {
            val newState = !currentEnabled
            preferencesStore.putBoolean(PreferencesConfig.PREF_KEY_NOTIFICATIONS, newState)
            newState
        }
    }
    
    /**
     * 자동 잠금 해제 토글
     */
    suspend fun toggleAutoUnlock(currentEnabled: Boolean): Result<Boolean> {
        return ExceptionUtil.runCatchingSuspend {
            val newState = !currentEnabled
            preferencesStore.putBoolean(PreferencesConfig.PREF_KEY_AUTO_UNLOCK, newState)
            newState
        }
    }
    
    /**
     * 데이터 설정 로드
     */
    suspend fun loadDataSettings(): DataSettings {
        return DataSettings(
            notificationsEnabled = preferencesStore.getBoolean(
                PreferencesConfig.PREF_KEY_NOTIFICATIONS, 
                true
            ),
            autoUnlockEnabled = preferencesStore.getBoolean(
                PreferencesConfig.PREF_KEY_AUTO_UNLOCK, 
                true
            )
        )
    }
    
    private suspend fun clearAllPreferences() {
        preferencesStore.clear()
    }
}

/**
 * 데이터 설정 데이터
 */
data class DataSettings(
    val notificationsEnabled: Boolean,
    val autoUnlockEnabled: Boolean
)