package com.flowerdiary.data.initializer.loader

import com.flowerdiary.common.utils.ExceptionUtil
import com.flowerdiary.common.utils.logInfo
import com.flowerdiary.common.utils.logDebug
import com.flowerdiary.common.utils.logError
import com.flowerdiary.data.initializer.mapper.BirthFlowerEntryMapper
import com.flowerdiary.data.initializer.parser.BirthFlowerDataParser
import com.flowerdiary.data.initializer.provider.BirthFlowerColorProvider
import com.flowerdiary.data.initializer.provider.BirthFlowerImagePathProvider
import com.flowerdiary.data.initializer.validator.BirthFlowerValidator
import com.flowerdiary.domain.model.BirthFlower

/**
 * 탄생화 데이터 로더
 * SRP: 전체 데이터 로드 프로세스 조정만 담당
 * 의존성 주입으로 각 컴포넌트 결합
 */
class BirthFlowerDataLoader(
    private val parser: BirthFlowerDataParser = BirthFlowerDataParser(),
    private val validator: BirthFlowerValidator = BirthFlowerValidator(),
    private val mapper: BirthFlowerEntryMapper = BirthFlowerEntryMapper(
        colorProvider = BirthFlowerColorProvider(),
        imagePathProvider = BirthFlowerImagePathProvider()
    )
) {
    
    /**
     * JSON 문자열에서 탄생화 데이터 로드
     */
    suspend fun loadFromJson(jsonContent: String): Result<List<BirthFlower>> =
        ExceptionUtil.runCatchingSuspend {
            logInfo("Starting birth flower data loading")
            
            // 1. JSON 파싱
            val entries = parser.parse(jsonContent).getOrThrow()
            logDebug("Parsed ${entries.size} entries")
            
            // 2. 검증 및 변환
            val validFlowers = processEntries(entries)
            logInfo("Successfully loaded ${validFlowers.size} birth flowers")
            
            validFlowers
        }
    
    /**
     * 엔트리 처리 (검증 -> 변환)
     */
    private fun processEntries(entries: List<com.flowerdiary.data.initializer.BirthFlowerEntry>): List<BirthFlower> =
        entries.mapNotNull { entry ->
            processEntry(entry)
        }
    
    /**
     * 단일 엔트리 처리
     */
    private fun processEntry(entry: com.flowerdiary.data.initializer.BirthFlowerEntry): BirthFlower? =
        validator.validate(entry)
            .mapCatching { validEntry ->
                mapper.mapToDomain(validEntry).getOrThrow()
            }
            .onFailure { error ->
                logError("Failed to process entry: ${entry.date}", error)
            }
            .getOrNull()
}
