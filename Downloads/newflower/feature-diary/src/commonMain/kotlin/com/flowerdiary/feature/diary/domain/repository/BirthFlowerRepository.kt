package com.flowerdiary.feature.diary.domain.repository

import com.flowerdiary.core.model.BirthFlower
import com.flowerdiary.core.types.FlowerId
import kotlinx.datetime.LocalDate

interface BirthFlowerRepository {

  suspend fun getByDate(date: LocalDate): BirthFlower?

  suspend fun getById(id: FlowerId): BirthFlower?

  suspend fun getByMonth(month: Int): List<BirthFlower>

  suspend fun getByDay(month: Int, day: Int): BirthFlower?

  suspend fun getAll(): List<BirthFlower>

  suspend fun search(query: String): List<BirthFlower>

  suspend fun searchByName(name: String): List<BirthFlower>

  suspend fun searchByMeaning(meaning: String): List<BirthFlower>

  suspend fun getRandomFlower(): BirthFlower?

  suspend fun getFlowersByMonth(month: Int): List<BirthFlower>

  suspend fun exists(id: FlowerId): Boolean

  suspend fun count(): Int

  suspend fun countByMonth(month: Int): Int

  suspend fun getImagePath(id: FlowerId): String?

  suspend fun hasImage(id: FlowerId): Boolean

  suspend fun getFlowerForDiary(date: LocalDate): BirthFlower?

  suspend fun getFavorites(): List<BirthFlower>

  suspend fun addToFavorites(id: FlowerId): Boolean

  suspend fun removeFromFavorites(id: FlowerId): Boolean

  suspend fun isFavorite(id: FlowerId): Boolean
}