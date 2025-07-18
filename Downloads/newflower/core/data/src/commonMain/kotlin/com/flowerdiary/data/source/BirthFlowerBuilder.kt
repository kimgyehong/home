package com.flowerdiary.data.source

import com.flowerdiary.common.resources.ResourcePathProvider
import com.flowerdiary.domain.model.BirthFlower
import com.flowerdiary.domain.model.FlowerId

/**
 * 탄생화 객체 생성 빌더
 * SRP: BirthFlower 객체 생성 로직만 담당
 */
class BirthFlowerBuilder(
    private val colorGenerator: BirthFlowerColorGenerator
) {
    
    /**
     * 데이터 맵으로부터 BirthFlower 리스트 생성
     */
    fun buildFromDataMap(dataMap: Map<String, BirthFlowerData>): List<BirthFlower> {
        return dataMap.map { (dateKey, data) ->
            val (month, day) = parseDateKey(dateKey)
            buildBirthFlower(month, day, data)
        }.sortedWith(compareBy({ it.month }, { it.day }))
    }
    
    /**
     * 단일 BirthFlower 객체 생성
     */
    fun buildBirthFlower(month: Int, day: Int, data: BirthFlowerData): BirthFlower {
        val id = FlowerId(month * 100 + day)
        val imageUrl = ResourcePathProvider.getBirthFlowerImagePath(month, day)
        val backgroundColor = colorGenerator.generateColor(data.nameKr)
        
        return BirthFlower(
            id = id,
            month = month,
            day = day,
            nameKr = data.nameKr,
            nameEn = data.nameEn,
            meaning = data.meaning,
            description = data.description,
            imageUrl = imageUrl,
            backgroundColor = backgroundColor,
            isUnlocked = false
        )
    }
    
    /**
     * 날짜 키 파싱 (MM-DD 형식)
     */
    private fun parseDateKey(dateKey: String): Pair<Int, Int> {
        val parts = dateKey.split("-")
        require(parts.size == 2) { "Invalid date key format: $dateKey" }
        
        val month = parts[0].toInt()
        val day = parts[1].toInt()
        
        validateDate(month, day)
        
        return Pair(month, day)
    }
    
    /**
     * 날짜 유효성 검증
     */
    private fun validateDate(month: Int, day: Int) {
        require(month in 1..12) { "Invalid month: $month" }
        require(day in 1..31) { "Invalid day: $day" }
    }
}