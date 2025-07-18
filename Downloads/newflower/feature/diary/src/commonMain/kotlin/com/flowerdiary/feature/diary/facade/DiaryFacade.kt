package com.flowerdiary.feature.diary.facade

import com.flowerdiary.common.data.UnitOfWork
import com.flowerdiary.common.platform.DateTimeUtil
import com.flowerdiary.common.utils.ExceptionUtil
import com.flowerdiary.common.utils.logDebug
import com.flowerdiary.common.utils.logInfo
import com.flowerdiary.common.utils.logError
import com.flowerdiary.domain.model.*
import com.flowerdiary.domain.repository.BirthFlowerRepository
import com.flowerdiary.domain.repository.DiaryRepository
import com.flowerdiary.domain.usecase.diary.SaveDiaryUseCase
import com.flowerdiary.domain.usecase.flower.UnlockBirthFlowerUseCase

/**
 * 일기 관련 복잡한 비즈니스 로직을 단순화하는 Facade
 * SRP: 일기와 꽃 관련 작업의 조정만 담당
 * 여러 유스케이스와 레포지토리를 조합하여 복잡한 작업 수행
 */
class DiaryFacade(
    private val unitOfWork: UnitOfWork,
    private val diaryRepository: DiaryRepository,
    private val birthFlowerRepository: BirthFlowerRepository,
    private val saveDiaryUseCase: SaveDiaryUseCase,
    private val unlockBirthFlowerUseCase: UnlockBirthFlowerUseCase
) {
    
    /**
     * 일기 저장과 동시에 해당 날짜의 꽃 해금
     * 트랜잭션으로 원자성 보장
     */
    suspend fun saveAndUnlockFlower(diary: Diary): Result<Unit> =
        unitOfWork.transaction {
            ExceptionUtil.runCatching {
                // 1. 일기 저장
                saveDiaryUseCase(diary).getOrThrow()
                logDebug("Diary saved: ${diary.id.value}")
                
                // 2. 해당 날짜의 꽃 정보 가져오기
                val dateInfo = DateTimeUtil.toDateInfo(diary.createdAt)
                val flower = birthFlowerRepository
                    .findByDate(dateInfo.month, dateInfo.day)
                    .getOrThrow()
                
                // 3. 꽃이 아직 해금되지 않았다면 해금
                if (flower != null && !flower.isUnlocked) {
                    unlockBirthFlowerUseCase.unlockById(flower.id).getOrThrow()
                    logInfo("Flower unlocked: ${flower.nameKr}")
                }
                
                Unit
            }.onFailure { e ->
                logError("Failed to save diary and unlock flower", e)
            }
        }
    
    /**
     * 월별 일기 통계 조회
     */
    suspend fun getMonthlyStatistics(year: Int, month: Int): Result<DiaryStatistics> =
        ExceptionUtil.runCatching {
            val diaries = diaryRepository
                .findByYearMonth(year, month)
                .getOrThrow()
            
            val statistics = DiaryStatistics(
                totalCount = diaries.size,
                moodDistribution = calculateMoodDistribution(diaries),
                weatherDistribution = calculateWeatherDistribution(diaries),
                mostUsedFlower = findMostUsedFlower(diaries),
                averageContentLength = calculateAverageContentLength(diaries)
            )
            
            statistics
        }.onFailure { e ->
            logError("Failed to get monthly statistics", e)
        }
    
    /**
     * 기분 분포 계산
     */
    private fun calculateMoodDistribution(diaries: List<Diary>): Map<Mood, Int> =
        diaries.groupingBy { it.mood }.eachCount()
    
    /**
     * 날씨 분포 계산
     */
    private fun calculateWeatherDistribution(diaries: List<Diary>): Map<Weather, Int> =
        diaries.groupingBy { it.weather }.eachCount()
    
    /**
     * 가장 많이 사용된 꽃 찾기
     */
    private suspend fun findMostUsedFlower(diaries: List<Diary>): BirthFlower? {
        if (diaries.isEmpty()) return null
        
        val flowerCounts = diaries
            .groupingBy { it.flowerId }
            .eachCount()
            .maxByOrNull { it.value }
            ?.key
        
        return flowerCounts?.let {
            birthFlowerRepository.findById(it).getOrNull()
        }
    }
    
    /**
     * 평균 내용 길이 계산
     */
    private fun calculateAverageContentLength(diaries: List<Diary>): Int =
        if (diaries.isEmpty()) 0
        else diaries.map { it.content.length }.average().toInt()
    
}

/**
 * 일기 통계 데이터
 * SRP: 통계 정보만 담당
 */
data class DiaryStatistics(
    val totalCount: Int,
    val moodDistribution: Map<Mood, Int>,
    val weatherDistribution: Map<Weather, Int>,
    val mostUsedFlower: BirthFlower?,
    val averageContentLength: Int
)
