package com.flowerdiary.feature.diary.data.mapper

import com.flowerdiary.core.model.BirthFlower
import com.flowerdiary.core.types.FlowerId
import com.flowerdiary.feature.diary.data.entity.BirthFlowerEntity

object BirthFlowerMapper {

  fun toDomain(entity: BirthFlowerEntity): BirthFlower {
    return BirthFlower(
      id = FlowerId.fromString(entity.id),
      name = entity.name,
      meaning = entity.meaning,
      description = entity.description,
      month = entity.month,
      day = entity.day,
      imagePath = entity.imagePath
    )
  }

  fun toEntity(domain: BirthFlower): BirthFlowerEntity {
    return BirthFlowerEntity(
      id = domain.id.value,
      name = domain.name,
      meaning = domain.meaning,
      description = domain.description,
      month = domain.month,
      day = domain.day,
      imagePath = domain.imagePath,
      isAvailable = true
    )
  }

  fun toDomainList(entities: List<BirthFlowerEntity>): List<BirthFlower> {
    return entities.filter { it.isAvailable }.map { toDomain(it) }
  }

  fun toEntityList(domains: List<BirthFlower>): List<BirthFlowerEntity> {
    return domains.map { toEntity(it) }
  }

  fun updateEntityFromDomain(entity: BirthFlowerEntity, domain: BirthFlower): BirthFlowerEntity {
    return entity.copy(
      name = domain.name,
      meaning = domain.meaning,
      description = domain.description,
      month = domain.month,
      day = domain.day,
      imagePath = domain.imagePath,
      updatedAt = System.currentTimeMillis()
    )
  }

  fun getDateKey(month: Int, day: Int): String {
    return String.format("%02d%02d", month, day)
  }

  fun isValidEntity(entity: BirthFlowerEntity): Boolean {
    return entity.isValid() && entity.isDateValid() && entity.hasValidImage()
  }

  fun filterAvailable(entities: List<BirthFlowerEntity>): List<BirthFlowerEntity> {
    return entities.filter { it.isAvailable }
  }

  fun createImagePath(month: Int, day: Int): String {
    val dateKey = getDateKey(month, day)
    return "탄생화/${month}월/${dateKey}.jpg"
  }
}