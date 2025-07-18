package com.flowerdiary.feature.diary.state

/**
 * 일기 스타일 상태
 */
data class DiaryStyleState(
    val fontFamily: String = "default",
    val fontColor: Int = 0xFF000000.toInt(),
    val backgroundTheme: String = "default"
)

/**
 * 일기 스타일 의도
 */
sealed interface DiaryStyleIntent {
    data object LoadStyles : DiaryStyleIntent
    data class ChangeFontFamily(val fontFamily: String) : DiaryStyleIntent
    data class ChangeFontColor(val color: Int) : DiaryStyleIntent
    data class ChangeBackgroundTheme(val theme: String) : DiaryStyleIntent
    data object ResetToDefault : DiaryStyleIntent
}

/**
 * 일기 스타일 효과
 */
sealed interface DiaryStyleEffect {
    data object StyleChanged : DiaryStyleEffect
}
