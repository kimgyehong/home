package com.flowerdiary.core.database

import com.flowerdiary.core.model.Settings
import com.flowerdiary.core.types.SettingsTimestamp
import com.flowerdiary.core.util.ResultWrapper

/**
 * 설정 데이터 접근 인터페이스
 * 앱 설정 저장 및 조회
 */
interface SettingsRepository {
  
  /**
   * 현재 설정 조회
   */
  suspend fun getCurrentSettings(): ResultWrapper<Settings>
  
  /**
   * 설정 저장
   */
  suspend fun saveSettings(settings: Settings): ResultWrapper<Unit>
  
  /**
   * BGM 설정 업데이트
   */
  suspend fun updateBgmSettings(
    trackIndex: Int,
    volume: Float,
    enabled: Boolean,
    updatedAt: SettingsTimestamp
  ): ResultWrapper<Unit>
  
  /**
   * 로딩 설정 업데이트
   */
  suspend fun updateLoadingSettings(
    animationEnabled: Boolean,
    duration: Long,
    updatedAt: SettingsTimestamp
  ): ResultWrapper<Unit>
  
  /**
   * BGM 트랙 변경
   */
  suspend fun updateBgmTrack(trackIndex: Int, updatedAt: SettingsTimestamp): ResultWrapper<Unit>
  
  /**
   * BGM 볼륨 변경
   */
  suspend fun updateBgmVolume(volume: Float, updatedAt: SettingsTimestamp): ResultWrapper<Unit>
  
  /**
   * BGM 활성화/비활성화
   */
  suspend fun toggleBgm(enabled: Boolean, updatedAt: SettingsTimestamp): ResultWrapper<Unit>
  
  /**
   * 로딩 애니메이션 활성화/비활성화
   */
  suspend fun toggleLoadingAnimation(
    enabled: Boolean, 
    updatedAt: SettingsTimestamp
  ): ResultWrapper<Unit>
  
  /**
   * 로딩 시간 변경
   */
  suspend fun updateLoadingDuration(
    duration: Long, 
    updatedAt: SettingsTimestamp
  ): ResultWrapper<Unit>
  
  /**
   * 기본 설정으로 초기화
   */
  suspend fun resetToDefault(updatedAt: SettingsTimestamp): ResultWrapper<Unit>
  
  /**
   * 설정 존재 여부 확인
   */
  suspend fun settingsExists(): ResultWrapper<Boolean>
}