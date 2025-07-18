package com.flowerdiary.feature.diary.mapper

import com.flowerdiary.common.constants.ColorPalette
import com.flowerdiary.common.constants.Config
import com.flowerdiary.domain.model.BirthFlower

/**
 * 탄생화 UI 표시용 매퍼
 * SRP: 도메인 모델을 UI 표시용으로 변환만 담당
 * 색상 변환, 날짜 포맷팅 등 UI 관련 로직 처리
 */
object FlowerUIMapper {
    
    /**
     * 탄생화를 UI 표시용 모델로 변환
     */
    fun BirthFlower.toUIModel(): FlowerUIModel {
        return FlowerUIModel(
            id = id.value,
            dateDisplay = getDateDisplay(),
            monthDisplay = getMonthDisplay(),
            name = nameKr,
            englishName = nameEn,
            meaning = meaning,
            description = description,
            imageUrl = imageUrl,
            backgroundColor = backgroundColor,
            backgroundColorHex = toHexColor(backgroundColor),
            isUnlocked = isUnlocked,
            lockedImageUrl = getLockedImageUrl(),
            shareText = generateShareText()
        )
    }
    
    /**
     * 월 표시 텍스트
     */
    private fun BirthFlower.getMonthDisplay(): String = "${month}월"
    
    /**
     * ARGB Long을 Hex 문자열로 변환
     */
    private fun toHexColor(color: Long): String {
        return "#${color.toString(16).padStart(Config.HEX_PADDING_LENGTH, '0').substring(Config.HEX_COLOR_START_INDEX)}"
    }
    
    /**
     * 잠긴 상태 이미지 URL
     */
    private fun getLockedImageUrl(): String = "flower_locked"
    
    /**
     * 공유용 텍스트 생성
     */
    private fun BirthFlower.generateShareText(): String =
        """
        🌸 ${month}월 ${day}일의 탄생화
        
        꽃: $nameKr ($nameEn)
        꽃말: $meaning
        
        $description
        
        #탄생화 #${nameKr} #꽃말 #FlowerDiary
        """.trimIndent()
}

/**
 * UI 표시용 탄생화 모델
 * SRP: UI에 필요한 데이터만 포함
 */
data class FlowerUIModel(
    val id: Int,
    val dateDisplay: String,
    val monthDisplay: String,
    val name: String,
    val englishName: String,
    val meaning: String,
    val description: String,
    val imageUrl: String,
    val backgroundColor: Long,
    val backgroundColorHex: String,
    val isUnlocked: Boolean,
    val lockedImageUrl: String,
    val shareText: String
)
