package com.flowerdiary.domain.service

import com.flowerdiary.domain.model.BirthFlower

/**
 * 탄생화 포맷팅 서비스
 * SRP: 탄생화 데이터 포맷팅 및 표시용 변환만 담당
 * 순수 함수로 구성되어 사이드 이펙트 없음
 */
object BirthFlowerFormatter {
    
    /**
     * 날짜 표시 형식 생성
     */
    fun formatDate(month: Int, day: Int): String {
        return "${month}월 ${day}일"
    }
    
    /**
     * 월 표시 형식 생성
     */
    fun formatMonth(month: Int): String {
        return "${month}월"
    }
    
    /**
     * 일 표시 형식 생성
     */
    fun formatDay(day: Int): String {
        return "${day}일"
    }
    
    /**
     * 탄생화 날짜 표시 형식 생성
     */
    fun getDateDisplay(birthFlower: BirthFlower): String {
        return formatDate(birthFlower.month, birthFlower.day)
    }
    
    /**
     * 탄생화 월 표시 형식 생성
     */
    fun getMonthDisplay(birthFlower: BirthFlower): String {
        return formatMonth(birthFlower.month)
    }
    
    /**
     * 탄생화 미리보기 생성 (도감용)
     * 해금된 꽃은 의미를, 잠긴 꽃은 물음표 표시
     */
    fun getPreview(birthFlower: BirthFlower): String {
        return if (birthFlower.isUnlocked) {
            birthFlower.meaning
        } else {
            "?"
        }
    }
    
    /**
     * 탄생화 요약 정보 생성
     */
    fun getSummary(birthFlower: BirthFlower): String {
        val date = getDateDisplay(birthFlower)
        val preview = getPreview(birthFlower)
        return "$date ${birthFlower.nameKr} - $preview"
    }
    
    /**
     * 탄생화 전체 설명 생성
     */
    fun getFullDescription(birthFlower: BirthFlower): String {
        return if (birthFlower.isUnlocked) {
            "${birthFlower.nameKr} (${birthFlower.nameEn})\n" +
            "꽃말: ${birthFlower.meaning}\n" +
            "설명: ${birthFlower.description}"
        } else {
            "${birthFlower.nameKr}\n잠긴 꽃입니다"
        }
    }
}