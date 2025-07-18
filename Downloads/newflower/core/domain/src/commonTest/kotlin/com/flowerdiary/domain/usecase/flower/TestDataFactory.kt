package com.flowerdiary.domain.usecase.flower

import com.flowerdiary.domain.model.BirthFlower
import com.flowerdiary.domain.model.FlowerId

/**
 * 테스트 데이터 생성 팩토리
 * SRP: 테스트에 필요한 꽃 데이터 생성만 담당
 */
internal object TestDataFactory {
    
    private const val HAPPY_FLOWER_ID = 1
    private const val SAD_FLOWER_ID = 2
    private const val PEACEFUL_FLOWER_ID = 3
    private const val BRIGHT_FLOWER_ID = 4
    
    private const val JANUARY = 1
    private const val FEBRUARY = 2
    private const val MARCH = 3
    private const val APRIL = 4
    
    private const val FIRST_DAY = 1
    private const val SECOND_DAY = 2
    private const val THIRD_DAY = 3
    private const val FOURTH_DAY = 4
    
    private const val RED_COLOR = 0xFFFF0000L
    private const val BLUE_COLOR = 0xFF0000FFL
    private const val GREEN_COLOR = 0xFF00FF00L
    private const val YELLOW_COLOR = 0xFFFFFF00L
    
    val happyFlower = BirthFlower(
        id = FlowerId(HAPPY_FLOWER_ID),
        month = JANUARY,
        day = FIRST_DAY,
        nameKr = "행복한 꽃",
        nameEn = "Happy Flower",
        meaning = "행복과 기쁨",
        description = "기쁨을 주는 꽃",
        imageUrl = "happy.png",
        backgroundColor = RED_COLOR,
        isUnlocked = true
    )
    
    val sadFlower = BirthFlower(
        id = FlowerId(SAD_FLOWER_ID),
        month = FEBRUARY,
        day = SECOND_DAY,
        nameKr = "위로의 꽃",
        nameEn = "Comfort Flower",
        meaning = "위로와 희망",
        description = "슬픔을 달래는 꽃",
        imageUrl = "comfort.png",
        backgroundColor = BLUE_COLOR,
        isUnlocked = true
    )
    
    val peacefulFlower = BirthFlower(
        id = FlowerId(PEACEFUL_FLOWER_ID),
        month = MARCH,
        day = THIRD_DAY,
        nameKr = "평화의 꽃",
        nameEn = "Peace Flower",
        meaning = "평화와 조화",
        description = "마음의 평안을 주는 꽃",
        imageUrl = "peace.png",
        backgroundColor = GREEN_COLOR,
        isUnlocked = true
    )
    
    val brightFlower = BirthFlower(
        id = FlowerId(BRIGHT_FLOWER_ID),
        month = APRIL,
        day = FOURTH_DAY,
        nameKr = "밝은 꽃",
        nameEn = "Bright Flower",
        meaning = "밝은 빛",
        description = "빛나는 꽃",
        imageUrl = "bright.png",
        backgroundColor = YELLOW_COLOR,
        isUnlocked = true
    )
    
    fun createFlowerList(): List<BirthFlower> = listOf(
        happyFlower,
        sadFlower,
        peacefulFlower,
        brightFlower
    )
}
