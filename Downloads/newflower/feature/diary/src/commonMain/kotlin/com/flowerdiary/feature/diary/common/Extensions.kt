package com.flowerdiary.feature.diary.common

import com.flowerdiary.domain.model.BirthFlower

/**
 * Extension Functions for common operations
 * Context7 KMP 모범 사례: 반복 로직 제거 및 코드 재사용성 향상
 */

/**
 * 컬렉션의 진행률을 계산하는 고차 함수
 */
inline fun <T> Collection<T>.progressOf(predicate: (T) -> Boolean): Float =
    if (isEmpty()) 0f else count(predicate).toFloat() / size * 100f

/**
 * Float을 Percentage로 변환
 */
fun Float.toPercentage(): Percentage = Percentage(toInt())

/**
 * Float을 VolumeLevel로 변환 (검증 포함)
 */
fun Float.toVolumeLevel(): VolumeLevel = VolumeLevel(this)

/**
 * String을 CharCount로 변환
 */
fun String.toCharCount(): CharCount = CharCount(length)

/**
 * Int를 TrackIndex로 변환
 */
fun Int.toTrackIndex(): TrackIndex = TrackIndex(this)

/**
 * 문자열 목록에서 빈 문자열이 아닌 항목이 있는지 확인
 */
fun Iterable<String>.hasNonBlankContent(): Boolean = any { it.isNotBlank() }

/**
 * 꽃 목록을 잠금 상태로 그룹화
 */
fun List<BirthFlower>.groupByLockStatus(): Pair<List<BirthFlower>, List<BirthFlower>> =
    partition { it.isUnlocked }

/**
 * 꽃 목록에서 모든 꽃이 해금되었는지 확인
 */
fun List<BirthFlower>.allUnlocked(): Boolean = all { it.isUnlocked }