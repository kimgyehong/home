package com.flowerdiary.feature.diary.manager

import com.flowerdiary.common.constants.Messages
import com.flowerdiary.domain.model.BirthFlower
import com.flowerdiary.domain.model.Mood
import com.flowerdiary.domain.model.Weather
import com.flowerdiary.domain.usecase.flower.GetBirthFlowerUseCase
import com.flowerdiary.domain.usecase.flower.RecommendFlowerUseCase
import com.flowerdiary.feature.diary.state.DiaryEditorState

/**
 * 꽃 선택 관리자
 * SRP: 꽃 선택/추천 관련 로직만 담당
 */
class FlowerSelectionManager(
    private val getBirthFlowerUseCase: GetBirthFlowerUseCase,
    private val recommendFlowerUseCase: RecommendFlowerUseCase
) {
    
    /**
     * 꽃 선택
     */
    fun selectFlower(
        currentState: DiaryEditorState,
        flower: BirthFlower
    ): DiaryEditorState {
        return currentState.copy(selectedFlower = flower)
    }
    
    /**
     * 오늘의 꽃 사용
     */
    fun useTodayFlower(currentState: DiaryEditorState): Pair<DiaryEditorState?, String> {
        return currentState.todayFlower?.let { flower ->
            Pair(
                currentState.copy(selectedFlower = flower),
                Messages.SUCCESS_FLOWER_SELECTED
            )
        } ?: Pair(null, Messages.ERROR_FLOWER_NOT_AVAILABLE)
    }
    
    /**
     * 꽃 추천 요청
     */
    suspend fun recommendFlower(
        mood: Mood,
        weather: Weather
    ): Result<BirthFlower?> {
        return recommendFlowerUseCase.recommendByMoodAndWeather(mood, weather)
    }
    
    /**
     * ID로 꽃 정보 가져오기
     */
    suspend fun getFlowerById(flowerId: String): Result<BirthFlower?> {
        return getBirthFlowerUseCase.getById(flowerId)
    }
    
    /**
     * 오늘의 꽃 가져오기
     */
    suspend fun getTodayFlower(): Result<BirthFlower?> {
        return getBirthFlowerUseCase.getTodayFlower()
    }
    
    /**
     * 추천 결과 메시지 생성
     */
    fun getRecommendationMessage(flower: BirthFlower?): String {
        return flower?.let {
            Messages.recommendationFlower(it.nameKr)
        } ?: Messages.ERROR_NO_RECOMMENDED_FLOWER
    }
}