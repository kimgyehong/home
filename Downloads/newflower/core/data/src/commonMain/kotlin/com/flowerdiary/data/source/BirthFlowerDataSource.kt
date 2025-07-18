package com.flowerdiary.data.source

import com.flowerdiary.domain.model.BirthFlower

/**
 * 탄생화 데이터 소스 (간소화 버전)
 * SRP: 탄생화 데이터 조합 및 제공 책임만 담당
 * 실제 로직은 각 전문 클래스에 위임
 */
class BirthFlowerDataSource(
    private val dataProvider: BirthFlowerDataProvider = BirthFlowerDataProvider(),
    private val colorGenerator: BirthFlowerColorGenerator = BirthFlowerColorGenerator(),
    private val builder: BirthFlowerBuilder = BirthFlowerBuilder(colorGenerator)
) {
    
    /**
     * 모든 탄생화 데이터 생성
     */
    fun createAllBirthFlowers(): List<BirthFlower> {
        val dataMap = dataProvider.getAllData()
        return builder.buildFromDataMap(dataMap)
    }
    
    /**
     * 특정 날짜의 탄생화 데이터 생성
     */
    fun createBirthFlower(month: Int, day: Int): BirthFlower? {
        val dateKey = String.format("%02d-%02d", month, day)
        val data = dataProvider.getAllData()[dateKey] ?: return null
        return builder.buildBirthFlower(month, day, data)
    }
    
    /**
     * 특정 월의 탄생화 데이터 생성
     */
    fun createBirthFlowersByMonth(month: Int): List<BirthFlower> {
        val monthKey = String.format("%02d", month)
        val monthData = dataProvider.getAllData()
            .filterKeys { it.startsWith(monthKey) }
        return builder.buildFromDataMap(monthData)
    }
    
    companion object {
        private const val TAG = "BirthFlowerDataSource"
    }
}