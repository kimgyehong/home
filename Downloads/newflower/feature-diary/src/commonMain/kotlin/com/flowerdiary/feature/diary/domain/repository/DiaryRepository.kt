package com.flowerdiary.feature.diary.domain.repository

import com.flowerdiary.feature.diary.domain.model.Diary
import com.flowerdiary.feature.diary.domain.model.DiaryId
import com.flowerdiary.feature.diary.domain.model.DiaryStatus
import kotlinx.datetime.LocalDate

interface DiaryRepository {

  suspend fun save(diary: Diary): Diary

  suspend fun saveMultiple(diaries: List<Diary>): List<Diary>

  suspend fun getById(id: DiaryId): Diary?

  suspend fun getByDate(date: LocalDate): Diary?

  suspend fun getAll(): List<Diary>

  suspend fun getByStatus(status: DiaryStatus): List<Diary>

  suspend fun getByDateRange(startDate: LocalDate, endDate: LocalDate): List<Diary>

  suspend fun getByMonth(year: Int, month: Int): List<Diary>

  suspend fun getRecent(limit: Int): List<Diary>

  suspend fun delete(id: DiaryId): Boolean

  suspend fun deleteMultiple(ids: List<DiaryId>): Boolean

  suspend fun deleteByStatus(status: DiaryStatus): Int

  suspend fun exists(id: DiaryId): Boolean

  suspend fun count(): Int

  suspend fun countByStatus(status: DiaryStatus): Int

  suspend fun backup(diary: Diary): Boolean

  suspend fun restoreFromBackup(id: DiaryId): Diary?

  suspend fun search(query: String): List<Diary>

  suspend fun searchByTitle(query: String): List<Diary>

  suspend fun searchByContent(query: String): List<Diary>
}