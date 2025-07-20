package com.flowerdiary.feature.diary.data.repository

import com.flowerdiary.core.database.BirthFlowerRepository as CoreBirthFlowerRepository
import com.flowerdiary.core.model.BirthFlower
import com.flowerdiary.core.types.FlowerId
import com.flowerdiary.feature.diary.domain.repository.BirthFlowerRepository
import kotlinx.datetime.LocalDate

class BirthFlowerRepositoryImpl(
  private val coreRepository: CoreBirthFlowerRepository
) : BirthFlowerRepository {

  private val favorites = mutableSetOf<String>()

  override suspend fun getByDate(date: LocalDate): BirthFlower? {
    return try {
      coreRepository.getByDate(date.monthNumber, date.dayOfMonth)
    } catch (exception: Exception) {
      null
    }
  }

  override suspend fun getById(id: FlowerId): BirthFlower? {
    return try {
      coreRepository.getById(id)
    } catch (exception: Exception) {
      null
    }
  }

  override suspend fun getByMonth(month: Int): List<BirthFlower> {
    return try {
      coreRepository.getByMonth(month)
    } catch (exception: Exception) {
      emptyList()
    }
  }

  override suspend fun getByDay(month: Int, day: Int): BirthFlower? {
    return try {
      coreRepository.getByDate(month, day)
    } catch (exception: Exception) {
      null
    }
  }

  override suspend fun getAll(): List<BirthFlower> {
    return try {
      coreRepository.getAll()
    } catch (exception: Exception) {
      emptyList()
    }
  }

  override suspend fun search(query: String): List<BirthFlower> {
    return try {
      val allFlowers = coreRepository.getAll()
      allFlowers.filter { flower ->
        flower.name.contains(query, ignoreCase = true) ||
        flower.meaning.contains(query, ignoreCase = true) ||
        flower.description.contains(query, ignoreCase = true)
      }
    } catch (exception: Exception) {
      emptyList()
    }
  }

  override suspend fun searchByName(name: String): List<BirthFlower> {
    return try {
      val allFlowers = coreRepository.getAll()
      allFlowers.filter { flower ->
        flower.name.contains(name, ignoreCase = true)
      }
    } catch (exception: Exception) {
      emptyList()
    }
  }

  override suspend fun searchByMeaning(meaning: String): List<BirthFlower> {
    return try {
      val allFlowers = coreRepository.getAll()
      allFlowers.filter { flower ->
        flower.meaning.contains(meaning, ignoreCase = true)
      }
    } catch (exception: Exception) {
      emptyList()
    }
  }

  override suspend fun getRandomFlower(): BirthFlower? {
    return try {
      val allFlowers = coreRepository.getAll()
      if (allFlowers.isNotEmpty()) {
        allFlowers.random()
      } else {
        null
      }
    } catch (exception: Exception) {
      null
    }
  }

  override suspend fun getFlowersByMonth(month: Int): List<BirthFlower> {
    return getByMonth(month)
  }

  override suspend fun exists(id: FlowerId): Boolean {
    return getById(id) != null
  }

  override suspend fun count(): Int {
    return try {
      coreRepository.getAll().size
    } catch (exception: Exception) {
      0
    }
  }

  override suspend fun countByMonth(month: Int): Int {
    return getByMonth(month).size
  }

  override suspend fun getImagePath(id: FlowerId): String? {
    return try {
      val flower = coreRepository.getById(id)
      flower?.imagePath
    } catch (exception: Exception) {
      null
    }
  }

  override suspend fun hasImage(id: FlowerId): Boolean {
    val imagePath = getImagePath(id)
    return !imagePath.isNullOrBlank()
  }

  override suspend fun getFlowerForDiary(date: LocalDate): BirthFlower? {
    return getByDate(date)
  }

  override suspend fun getFavorites(): List<BirthFlower> {
    return try {
      val allFlowers = coreRepository.getAll()
      allFlowers.filter { flower ->
        favorites.contains(flower.id.value)
      }
    } catch (exception: Exception) {
      emptyList()
    }
  }

  override suspend fun addToFavorites(id: FlowerId): Boolean {
    return try {
      favorites.add(id.value)
      true
    } catch (exception: Exception) {
      false
    }
  }

  override suspend fun removeFromFavorites(id: FlowerId): Boolean {
    return try {
      favorites.remove(id.value)
      true
    } catch (exception: Exception) {
      false
    }
  }

  override suspend fun isFavorite(id: FlowerId): Boolean {
    return favorites.contains(id.value)
  }
}