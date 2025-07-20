package com.flowerdiary.feature.diary.data.repository

import com.flowerdiary.feature.diary.data.entity.DiaryEntity
import com.flowerdiary.feature.diary.data.entity.DiarySettingsEntity
import kotlinx.datetime.LocalDate

interface DiaryLocalDataSource {

  suspend fun saveDiary(diary: DiaryEntity): DiaryEntity

  suspend fun saveDiaries(diaries: List<DiaryEntity>): List<DiaryEntity>

  suspend fun getDiaryById(id: String): DiaryEntity?

  suspend fun getDiaryByDate(date: LocalDate): DiaryEntity?

  suspend fun getAllDiaries(): List<DiaryEntity>

  suspend fun getDiariesByStatus(status: String): List<DiaryEntity>

  suspend fun getDiariesByDateRange(startDate: LocalDate, endDate: LocalDate): List<DiaryEntity>

  suspend fun getDiariesByMonth(year: Int, month: Int): List<DiaryEntity>

  suspend fun getRecentDiaries(limit: Int): List<DiaryEntity>

  suspend fun deleteDiary(id: String): Boolean

  suspend fun deleteDiaries(ids: List<String>): Boolean

  suspend fun deleteDiariesByStatus(status: String): Int

  suspend fun diaryExists(id: String): Boolean

  suspend fun countDiaries(): Int

  suspend fun countDiariesByStatus(status: String): Int

  suspend fun backupDiary(diary: DiaryEntity): Boolean

  suspend fun restoreDiaryFromBackup(id: String): DiaryEntity?

  suspend fun searchDiaries(query: String): List<DiaryEntity>

  suspend fun searchDiariesByTitle(query: String): List<DiaryEntity>

  suspend fun searchDiariesByContent(query: String): List<DiaryEntity>

  suspend fun getSettings(): DiarySettingsEntity

  suspend fun saveSettings(settings: DiarySettingsEntity): DiarySettingsEntity

  suspend fun clearAllData(): Boolean
}