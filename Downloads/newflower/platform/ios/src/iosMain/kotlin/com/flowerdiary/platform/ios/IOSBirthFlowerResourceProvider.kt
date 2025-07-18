package com.flowerdiary.platform.ios

import com.flowerdiary.common.utils.ExceptionUtil
import com.flowerdiary.common.utils.logDebug
import com.flowerdiary.common.utils.logInfo
import com.flowerdiary.data.initializer.resource.BirthFlowerResourceProvider
import kotlinx.cinterop.BetaInteropApi
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSBundle
import platform.Foundation.NSString
import platform.Foundation.NSUTF8StringEncoding
import platform.Foundation.dataUsingEncoding

/**
 * iOS 플랫폼 탄생화 리소스 제공자
 * SRP: iOS 리소스 읽기만 담당
 * 플랫폼 의존성은 이 모듈에만 격리
 */
@OptIn(ExperimentalForeignApi::class, BetaInteropApi::class)
actual class BirthFlowerResourceProvider {
    
    actual suspend fun readBirthFlowerJson(): Result<String> =
        ExceptionUtil.runCatchingSuspend {
            logDebug("Reading birth flower JSON from bundle")
            
            val bundle = NSBundle.mainBundle
            val path = bundle.pathForResource("birthflowers", "json")
                ?: throw IllegalStateException("birthflowers.json not found in bundle")
            
            val content = NSString.stringWithContentsOfFile(path, NSUTF8StringEncoding, null)
                ?: throw IllegalStateException("Failed to read birthflowers.json")
            
            content.also {
                logInfo("Successfully read birth flower JSON")
            }
        }
}