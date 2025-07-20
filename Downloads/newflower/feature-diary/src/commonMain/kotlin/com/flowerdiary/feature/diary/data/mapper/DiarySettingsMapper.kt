package com.flowerdiary.feature.diary.data.mapper

import com.flowerdiary.feature.diary.data.entity.DiarySettingsEntity
import com.flowerdiary.feature.diary.domain.model.BgmTrack
import com.flowerdiary.feature.diary.domain.model.DiarySettings

object DiarySettingsMapper {

  fun toDomain(entity: DiarySettingsEntity): DiarySettings {
    return DiarySettings(
      autoSaveEnabled = entity.autoSaveEnabled,
      autoSaveIntervalSeconds = entity.autoSaveIntervalSeconds,
      bgmTrack = mapToBgmTrack(entity.bgmTrack),
      bgmEnabled = entity.bgmEnabled,
      showBirthFlowerBackground = entity.showBirthFlowerBackground,
      fontSize = entity.fontSize
    )
  }

  fun toEntity(domain: DiarySettings): DiarySettingsEntity {
    return DiarySettingsEntity(
      autoSaveEnabled = domain.autoSaveEnabled,
      autoSaveIntervalSeconds = domain.autoSaveIntervalSeconds,
      bgmTrack = domain.bgmTrack.fileName,
      bgmEnabled = domain.bgmEnabled,
      showBirthFlowerBackground = domain.showBirthFlowerBackground,
      fontSize = domain.fontSize,
      lastModified = System.currentTimeMillis()
    )
  }

  fun updateEntityFromDomain(entity: DiarySettingsEntity, domain: DiarySettings): DiarySettingsEntity {
    return entity.copy(
      autoSaveEnabled = domain.autoSaveEnabled,
      autoSaveIntervalSeconds = domain.autoSaveIntervalSeconds,
      bgmTrack = domain.bgmTrack.fileName,
      bgmEnabled = domain.bgmEnabled,
      showBirthFlowerBackground = domain.showBirthFlowerBackground,
      fontSize = domain.fontSize,
      lastModified = System.currentTimeMillis()
    )
  }

  fun mergeDomainChanges(original: DiarySettings, updated: DiarySettings): DiarySettings {
    return DiarySettings(
      autoSaveEnabled = updated.autoSaveEnabled,
      autoSaveIntervalSeconds = updated.autoSaveIntervalSeconds,
      bgmTrack = updated.bgmTrack,
      bgmEnabled = updated.bgmEnabled,
      showBirthFlowerBackground = updated.showBirthFlowerBackground,
      fontSize = updated.fontSize
    )
  }

  private fun mapToBgmTrack(fileName: String): BgmTrack {
    return BgmTrack.fromFileName(fileName) ?: BgmTrack.getDefault()
  }

  fun createDefaultEntity(): DiarySettingsEntity {
    return toEntity(DiarySettings.default())
  }

  fun isValidMapping(entity: DiarySettingsEntity): Boolean {
    return entity.isValid() && 
           BgmTrack.fromFileName(entity.bgmTrack) != null
  }
}