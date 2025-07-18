package com.flowerdiary.data.initializer.parser

import com.flowerdiary.common.constants.BirthFlowerConstants
import com.flowerdiary.common.utils.ExceptionUtil
import com.flowerdiary.common.utils.logDebug
import com.flowerdiary.common.utils.logInfo
import com.flowerdiary.common.utils.logWarning
import com.flowerdiary.common.utils.logError
import com.flowerdiary.data.initializer.BirthFlowerData
import com.flowerdiary.data.initializer.BirthFlowerEntry
import com.flowerdiary.data.initializer.BirthFlowerJson
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

/**
 * 탄생화 데이터 JSON 파서
 * SRP: JSON 파싱 책임만 담당
 * Detekt: 메서드 60줄 이하, Generic Exception 미사용
 */
class BirthFlowerDataParser {
    
    private val json = Json { 
        ignoreUnknownKeys = true
        isLenient = true 
    }
    
    /**
     * JSON 문자열을 탄생화 엔트리 리스트로 파싱
     */
    fun parse(jsonContent: String): Result<List<BirthFlowerEntry>> =
        ExceptionUtil.runCatching {
            logDebug("Starting to parse birth flower JSON data")
            
            val birthFlowerJson = json.decodeFromString<BirthFlowerJson>(jsonContent)
            val entries = birthFlowerJson.flowers
            
            logInfo("Successfully parsed ${entries.size} birth flower entries")
            entries
        }
    
}
