package com.flowerdiary.feature.diary.state.settings

import com.flowerdiary.common.constants.Config
import com.flowerdiary.feature.diary.common.Percentage
import com.flowerdiary.feature.diary.common.TrackIndex
import com.flowerdiary.feature.diary.common.VolumeLevel
import com.flowerdiary.feature.diary.common.toTrackIndex
import com.flowerdiary.feature.diary.common.toVolumeLevel

/**
 * 오디오 설정 관련 상태 - Context7 KMP 극한 압축
 * SRP: BGM과 오디오 관련 설정만 관리
 */
data class AudioSettingsState(
    val bgmEnabled: Boolean = true,
    private val _bgmVolume: Float = Config.BGM_VOLUME_DEFAULT,
    private val _bgmTrackIndex: Int = 0
) {
    val bgmVolume: VolumeLevel get() = _bgmVolume.toVolumeLevel()
    val bgmTrackIndex: TrackIndex get() = _bgmTrackIndex.toTrackIndex()
    val bgmVolumePercent: Percentage get() = bgmVolume.toPercentage()
    val currentTrackName: String get() = bgmTrackIndex.toDisplayName()
    val isMuted: Boolean get() = !bgmEnabled || bgmVolume.isMuted
}