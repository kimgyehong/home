package com.flowerdiary.core.types

import kotlinx.serialization.Serializable

/**
 * 탄생화 엔티티의 고유 식별자
 * 월과 일 기반의 구조화된 ID 형식
 */
@Serializable
data class FlowerId(override val value: String) : EntityId {
  init {
    require(value.startsWith(PREFIX)) { 
      "FlowerId must start with '$PREFIX': $value" 
    }
    require(EntityId.validateFormat(value, PREFIX)) { 
      "Invalid FlowerId format: $value" 
    }
    require(isValidMonthDay()) { 
      "Invalid month-day format in FlowerId: $value" 
    }
  }

  val month: Int
    get() = extractMonth()

  val day: Int
    get() = extractDay()

  private fun isValidMonthDay(): Boolean {
    val parts = value.removePrefix(PREFIX).split("-")
    if (parts.size != 2) return false
    
    val month = parts[0].toIntOrNull() ?: return false
    val day = parts[1].toIntOrNull() ?: return false
    
    return month in 1..12 && day in 1..31
  }

  private fun extractMonth(): Int {
    return value.removePrefix(PREFIX).split("-")[0].toInt()
  }

  private fun extractDay(): Int {
    return value.removePrefix(PREFIX).split("-")[1].toInt()
  }

  companion object {
    private const val PREFIX = "flower-"

    /**
     * 월과 일로 FlowerId 생성
     * @param month 월 (1-12)
     * @param day 일 (1-31)
     * @return 생성된 FlowerId
     */
    fun create(month: Int, day: Int): FlowerId {
      require(month in 1..12) { "Month must be 1-12: $month" }
      require(day in 1..31) { "Day must be 1-31: $day" }
      
      return FlowerId("$PREFIX${month.toString().padStart(2, '0')}-${day.toString().padStart(2, '0')}")
    }
  }
}