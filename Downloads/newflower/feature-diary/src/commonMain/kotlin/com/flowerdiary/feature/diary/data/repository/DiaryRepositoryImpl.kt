package com.flowerdiary.feature.diary.data.repository

import com.flowerdiary.feature.diary.data.mapper.DiaryMapper
import com.flowerdiary.feature.diary.domain.model.Diary
import com.flowerdiary.feature.diary.domain.model.DiaryId
import com.flowerdiary.feature.diary.domain.model.DiaryStatus
import com.flowerdiary.feature.diary.domain.repository.DiaryRepository
import kotlinx.datetime.LocalDate

class DiaryRepositoryImpl(
  private val localDataSource: DiaryLocalDataSource,
  private val remoteDataSource: DiaryRemoteDataSource? = null
) : DiaryRepository {

  override suspend fun save(diary: Diary): Diary {
    val entity = DiaryMapper.toEntity(diary)
    val savedEntity = localDataSource.saveDiary(entity)
    
    remoteDataSource?.let {
      try {
        it.uploadDiary(savedEntity)
      } catch (exception: Exception) {
        // Remote sync failure is not critical
      }
    }
    
    return DiaryMapper.toDomain(savedEntity)
  }

  override suspend fun saveMultiple(diaries: List<Diary>): List<Diary> {
    val entities = DiaryMapper.toEntityList(diaries)
    val savedEntities = localDataSource.saveDiaries(entities)
    
    remoteDataSource?.let {
      try {
        it.uploadDiaries(savedEntities)
      } catch (exception: Exception) {
        // Remote sync failure is not critical
      }
    }
    
    return DiaryMapper.toDomainList(savedEntities)
  }

  override suspend fun getById(id: DiaryId): Diary? {
    val entity = localDataSource.getDiaryById(id.value)
    return entity?.let { DiaryMapper.toDomain(it) }
  }

  override suspend fun getByDate(date: LocalDate): Diary? {
    val entity = localDataSource.getDiaryByDate(date)
    return entity?.let { DiaryMapper.toDomain(it) }
  }

  override suspend fun getAll(): List<Diary> {
    val entities = localDataSource.getAllDiaries()
    return DiaryMapper.toDomainList(entities)
  }

  override suspend fun getByStatus(status: DiaryStatus): List<Diary> {
    val statusString = status.toString().lowercase()
    val entities = localDataSource.getDiariesByStatus(statusString)
    return DiaryMapper.toDomainList(entities)
  }

  override suspend fun getByDateRange(startDate: LocalDate, endDate: LocalDate): List<Diary> {
    val entities = localDataSource.getDiariesByDateRange(startDate, endDate)
    return DiaryMapper.toDomainList(entities)
  }

  override suspend fun getByMonth(year: Int, month: Int): List<Diary> {
    val entities = localDataSource.getDiariesByMonth(year, month)
    return DiaryMapper.toDomainList(entities)
  }

  override suspend fun getRecent(limit: Int): List<Diary> {
    val entities = localDataSource.getRecentDiaries(limit)
    return DiaryMapper.toDomainList(entities)
  }

  override suspend fun delete(id: DiaryId): Boolean {
    val result = localDataSource.deleteDiary(id.value)
    
    if (result) {
      remoteDataSource?.let {
        try {
          it.deleteDiaryFromRemote(id.value)
        } catch (exception: Exception) {
          // Remote sync failure is not critical
        }
      }
    }
    
    return result
  }

  override suspend fun deleteMultiple(ids: List<DiaryId>): Boolean {
    val idStrings = ids.map { it.value }
    return localDataSource.deleteDiaries(idStrings)
  }

  override suspend fun deleteByStatus(status: DiaryStatus): Int {
    val statusString = status.toString().lowercase()
    return localDataSource.deleteDiariesByStatus(statusString)
  }

  override suspend fun exists(id: DiaryId): Boolean {
    return localDataSource.diaryExists(id.value)
  }

  override suspend fun count(): Int {
    return localDataSource.countDiaries()
  }

  override suspend fun countByStatus(status: DiaryStatus): Int {
    val statusString = status.toString().lowercase()
    return localDataSource.countDiariesByStatus(statusString)
  }

  override suspend fun backup(diary: Diary): Boolean {
    val entity = DiaryMapper.toEntity(diary)
    return localDataSource.backupDiary(entity)
  }

  override suspend fun restoreFromBackup(id: DiaryId): Diary? {
    val entity = localDataSource.restoreDiaryFromBackup(id.value)
    return entity?.let { DiaryMapper.toDomain(it) }
  }

  override suspend fun search(query: String): List<Diary> {
    val entities = localDataSource.searchDiaries(query)
    return DiaryMapper.toDomainList(entities)
  }

  override suspend fun searchByTitle(query: String): List<Diary> {
    val entities = localDataSource.searchDiariesByTitle(query)
    return DiaryMapper.toDomainList(entities)
  }

  override suspend fun searchByContent(query: String): List<Diary> {
    val entities = localDataSource.searchDiariesByContent(query)
    return DiaryMapper.toDomainList(entities)
  }
}