package com.flowerdiary.data.initializer.mapper

import com.flowerdiary.common.utils.logDebug
import com.flowerdiary.common.utils.logError
import com.flowerdiary.data.initializer.BirthFlowerEntry
import com.flowerdiary.data.initializer.provider.BirthFlowerColorProvider
import com.flowerdiary.data.initializer.provider.BirthFlowerImagePathProvider
import com.flowerdiary.domain.model.BirthFlower
import com.flowerdiary.domain.model.FlowerId

/**
 * 탄생화 엔트리를 도메인 모델로 변환
 * SRP: 데이터 변환만 담당
 * 의존성 주입으로 결합도 감소
 */
class BirthFlowerEntryMapper(
    private val colorProvider: BirthFlowerColorProvider,
    private val imagePathProvider: BirthFlowerImagePathProvider
) {
    
    /**
     * BirthFlowerEntry를 도메인 BirthFlower로 변환
     */
    fun mapToDomain(entry: BirthFlowerEntry): Result<BirthFlower> =
        runCatching {
            val flowerId = FlowerId.fromDate(entry.month, entry.day)
            val backgroundColor = colorProvider.getColorForMonth(entry.month)
            val imageUrl = imagePathProvider.getImageUrl(entry.month, entry.day)
            
            BirthFlower(
                id = flowerId,
                month = entry.month,
                day = entry.day,
                nameKr = entry.nameKr,
                nameEn = entry.nameEn,
                meaning = entry.meaning,
                description = entry.description,
                imageUrl = imageUrl,
                backgroundColor = parseColorString(entry.backgroundColor),
                isUnlocked = false
            ).also {
                logDebug("Mapped entry for ${entry.date}")
            }
        }.onFailure { error ->
            logError("Failed to map entry for ${entry.date}", error)
        }
    
    /**
     * 색상 문자열을 Long으로 변환
     * #RRGGBB 형식을 0xFFRRGGBB 형식으로 변환
     */
    private fun parseColorString(colorString: String): Long {
        val color = colorString.removePrefix("#")
        return "FF$color".toLong(16)
    }
}
