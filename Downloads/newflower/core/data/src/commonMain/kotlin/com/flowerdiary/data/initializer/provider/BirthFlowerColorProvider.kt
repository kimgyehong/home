package com.flowerdiary.data.initializer.provider

import com.flowerdiary.common.constants.ColorPalette

/**
 * 탄생화 월별 색상 제공자
 * SRP: 월별 배경 색상 제공만 담당
 * 하드코딩 금지: ColorPalette 상수 사용
 */
class BirthFlowerColorProvider {
    
    /**
     * 월별 배경 색상 반환
     */
    fun getColorForMonth(month: Int): Long =
        when (month) {
            JANUARY -> ColorPalette.FlowerBackground.January
            FEBRUARY -> ColorPalette.FlowerBackground.February
            MARCH -> ColorPalette.FlowerBackground.March
            APRIL -> ColorPalette.FlowerBackground.April
            MAY -> ColorPalette.FlowerBackground.May
            JUNE -> ColorPalette.FlowerBackground.June
            JULY -> ColorPalette.FlowerBackground.July
            AUGUST -> ColorPalette.FlowerBackground.August
            SEPTEMBER -> ColorPalette.FlowerBackground.September
            OCTOBER -> ColorPalette.FlowerBackground.October
            NOVEMBER -> ColorPalette.FlowerBackground.November
            DECEMBER -> ColorPalette.FlowerBackground.December
            else -> ColorPalette.FlowerBackground.January // 기본값
        }
    
    companion object {
        // 월 상수 정의 (매직넘버 방지)
        private const val JANUARY = 1
        private const val FEBRUARY = 2
        private const val MARCH = 3
        private const val APRIL = 4
        private const val MAY = 5
        private const val JUNE = 6
        private const val JULY = 7
        private const val AUGUST = 8
        private const val SEPTEMBER = 9
        private const val OCTOBER = 10
        private const val NOVEMBER = 11
        private const val DECEMBER = 12
    }
}
