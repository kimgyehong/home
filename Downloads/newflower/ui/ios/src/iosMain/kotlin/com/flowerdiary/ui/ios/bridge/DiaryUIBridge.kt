package com.flowerdiary.ui.ios.bridge

import com.flowerdiary.domain.model.Mood
import com.flowerdiary.domain.model.Weather
import com.flowerdiary.feature.diary.mapper.DiaryUIModel
import com.flowerdiary.feature.diary.mapper.FlowerUIModel
import com.flowerdiary.feature.diary.state.*
import platform.Foundation.NSObject

/**
 * iOS UI 브릿지
 * SRP: Kotlin 데이터 클래스를 SwiftUI에서 사용 가능하도록 변환만 담당
 * Swift에서 접근 가능한 프로퍼티와 메서드 제공
 */

/**
 * 일기 UI 모델 브릿지
 */
class DiaryUIBridge(private val model: DiaryUIModel) : NSObject {
    val id: String = model.id
    val title: String = model.title
    val preview: String = model.preview
    val formattedDate: String = model.formattedDate
    val relativeTime: String = model.relativeTime
    val moodEmoji: String = model.moodEmoji
    val moodName: String = model.moodName
    val weatherIcon: String = model.weatherIcon
    val weatherName: String = model.weatherName
    val hasCustomSettings: Boolean = model.hasCustomSettings
    val isEdited: Boolean = model.isEdited
}

/**
 * 탄생화 UI 모델 브릿지
 */
class FlowerUIBridge(private val model: FlowerUIModel) : NSObject {
    val id: Int = model.id
    val dateDisplay: String = model.dateDisplay
    val monthDisplay: String = model.monthDisplay
    val name: String = model.name
    val englishName: String = model.englishName
    val meaning: String = model.meaning
    val description: String = model.description
    val imageUrl: String = model.imageUrl
    val backgroundColor: Long = model.backgroundColor
    val backgroundColorHex: String = model.backgroundColorHex
    val isUnlocked: Boolean = model.isUnlocked
    val lockedImageUrl: String = model.lockedImageUrl
    val shareText: String = model.shareText
}

/**
 * 일기 목록 상태 브릿지
 */
class DiaryListStateBridge(private val state: DiaryListState) : NSObject {
    val isLoading: Boolean = state.isLoading
    val error: String? = state.error
    val diaries: List<DiaryUIBridge> = state.diaries.map { DiaryUIBridge(it) }
    val isEmpty: Boolean = state.diaries.isEmpty()
}

/**
 * 일기 편집 상태 브릿지
 */
class DiaryEditorStateBridge(private val state: DiaryEditorState) : NSObject {
    val isLoading: Boolean = state.isLoading
    val error: String? = state.error
    val diaryId: String? = state.diaryId
    val title: String = state.title
    val content: String = state.content
    val date: String = state.date
    val mood: String? = state.mood?.name
    val weather: String? = state.weather?.name
    val selectedFlower: FlowerUIBridge? = state.selectedFlower?.let { FlowerUIBridge(it) }
    val fontFamily: String = state.fontFamily
    val fontSize: Float = state.fontSize
    val fontColor: Long = state.fontColor
    val backgroundTheme: String = state.backgroundTheme
    val isSaveEnabled: Boolean = state.isSaveEnabled
}

/**
 * 도감 상태 브릿지
 */
class CollectionStateBridge(private val state: CollectionState) : NSObject {
    val isLoading: Boolean = state.isLoading
    val error: String? = state.error
    val flowers: List<FlowerUIBridge> = state.flowers.map { FlowerUIBridge(it) }
    val unlockedCount: Int = state.unlockedCount
    val totalCount: Int = state.totalCount
    val progressPercentage: Float = state.progressPercentage
}

/**
 * 설정 상태 브릿지
 */
class SettingsStateBridge(private val state: SettingsState) : NSObject {
    val isLoading: Boolean = state.isLoading
    val error: String? = state.error
    val bgmEnabled: Boolean = state.bgmEnabled
    val bgmVolume: Float = state.bgmVolume
    val bgmVolumePercent: Int = state.bgmVolumePercent
    val bgmTrackIndex: Int = state.bgmTrackIndex
    val fontSizeScale: Float = state.fontSizeScale
    val fontSizeLevel: String = state.fontSizeLevel.name
    val notificationsEnabled: Boolean = state.notificationsEnabled
    val autoUnlockEnabled: Boolean = state.autoUnlockEnabled
    val themeMode: String = state.themeMode.name
}

/**
 * 기분 브릿지
 */
class MoodBridge(private val mood: Mood) : NSObject {
    val name: String = mood.name
    val emoji: String = mood.emoji
    val displayName: String = mood.displayName
    val colorHex: String = mood.colorHex
}

/**
 * 날씨 브릿지
 */
class WeatherBridge(private val weather: Weather) : NSObject {
    val name: String = weather.name
    val icon: String = weather.icon
    val displayName: String = weather.displayName
    val description: String = weather.description
}

/**
 * 기분 목록 제공자
 */
object MoodProvider {
    fun getAllMoods(): List<MoodBridge> {
        return Mood.values().map { MoodBridge(it) }
    }
}

/**
 * 날씨 목록 제공자
 */
object WeatherProvider {
    fun getAllWeathers(): List<WeatherBridge> {
        return Weather.values().map { WeatherBridge(it) }
    }
}