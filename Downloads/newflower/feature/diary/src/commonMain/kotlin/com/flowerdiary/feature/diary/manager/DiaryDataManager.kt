package com.flowerdiary.feature.diary.manager

import com.flowerdiary.common.platform.DateTimeUtil
import com.flowerdiary.common.utils.Logger
import com.flowerdiary.domain.model.*
import com.flowerdiary.domain.usecase.diary.GetDiaryUseCase
import com.flowerdiary.domain.usecase.diary.SaveDiaryUseCase
import com.flowerdiary.domain.usecase.flower.GetBirthFlowerUseCase

/**
 * 일기 데이터 관리자
 * SRP: 일기 데이터의 로드/저장 로직만 담당
 */
class DiaryDataManager(
    private val getDiaryUseCase: GetDiaryUseCase,
    private val saveDiaryUseCase: SaveDiaryUseCase,
    private val getBirthFlowerUseCase: GetBirthFlowerUseCase
) {
    
    /**
     * 오늘의 꽃 로드
     */
    suspend fun loadTodayFlower(): Result<BirthFlower?> {
        return runCatching {
            val today = DateTimeUtil.getCurrentDate()
            getBirthFlowerUseCase.getByDate(today.month, today.day)
                .getOrNull()
        }
    }
    
    /**
     * 일기 로드
     */
    suspend fun loadDiary(diaryId: DiaryId): Result<Diary?> {
        return getDiaryUseCase(diaryId)
    }
    
    /**
     * 일기에 연결된 꽃 정보 로드
     */
    suspend fun loadFlowerForDiary(flowerId: BirthFlowerId): Result<BirthFlower?> {
        return getBirthFlowerUseCase.getById(flowerId)
    }
    
    /**
     * 일기 저장
     */
    suspend fun saveDiary(
        diaryId: DiaryId?,
        title: String,
        content: String,
        mood: Mood,
        weather: Weather,
        flowerId: BirthFlowerId,
        fontFamily: String,
        fontColor: Long,
        backgroundTheme: String,
        isEditMode: Boolean
    ): Result<Unit> {
        val now = DateTimeUtil.now()
        
        val diary = if (isEditMode && diaryId != null) {
            Diary(
                id = diaryId,
                title = title,
                content = content,
                mood = mood,
                weather = weather,
                flowerId = flowerId,
                fontFamily = fontFamily,
                fontColor = fontColor,
                backgroundTheme = backgroundTheme,
                createdAt = now, // TODO: 실제로는 기존 값 유지해야 함
                updatedAt = now
            )
        } else {
            Diary(
                id = DiaryId.generate(),
                title = title,
                content = content,
                mood = mood,
                weather = weather,
                flowerId = flowerId,
                fontFamily = fontFamily,
                fontColor = fontColor,
                backgroundTheme = backgroundTheme,
                createdAt = now,
                updatedAt = now
            )
        }
        
        return saveDiaryUseCase(diary)
    }
    
    companion object {
        private const val TAG = "DiaryDataManager"
    }
}