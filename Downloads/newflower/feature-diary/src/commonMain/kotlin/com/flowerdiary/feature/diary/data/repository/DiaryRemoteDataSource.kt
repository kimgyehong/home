package com.flowerdiary.feature.diary.data.repository

import com.flowerdiary.feature.diary.data.entity.DiaryEntity
import com.flowerdiary.feature.diary.data.entity.DiarySettingsEntity

interface DiaryRemoteDataSource {

  suspend fun syncDiary(diary: DiaryEntity): DiaryEntity?

  suspend fun syncDiaries(diaries: List<DiaryEntity>): List<DiaryEntity>

  suspend fun downloadDiary(id: String): DiaryEntity?

  suspend fun downloadAllDiaries(): List<DiaryEntity>

  suspend fun uploadDiary(diary: DiaryEntity): Boolean

  suspend fun uploadDiaries(diaries: List<DiaryEntity>): Boolean

  suspend fun deleteDiaryFromRemote(id: String): Boolean

  suspend fun checkSyncStatus(id: String): Boolean

  suspend fun getLastSyncTimestamp(): Long

  suspend fun updateLastSyncTimestamp(timestamp: Long): Boolean

  suspend fun backupToRemote(diaries: List<DiaryEntity>): Boolean

  suspend fun restoreFromRemote(): List<DiaryEntity>

  suspend fun syncSettings(settings: DiarySettingsEntity): DiarySettingsEntity?

  suspend fun downloadSettings(): DiarySettingsEntity?

  suspend fun uploadSettings(settings: DiarySettingsEntity): Boolean

  suspend fun isRemoteAvailable(): Boolean

  suspend fun getRemoteVersion(): String

  suspend fun checkRemoteCompatibility(): Boolean

  suspend fun clearRemoteData(): Boolean
}