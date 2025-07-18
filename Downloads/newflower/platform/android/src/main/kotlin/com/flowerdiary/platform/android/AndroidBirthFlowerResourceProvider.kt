package com.flowerdiary.platform.android

import android.content.Context
import com.flowerdiary.common.utils.ExceptionUtil
import com.flowerdiary.common.utils.Logger
import com.flowerdiary.data.initializer.resource.BirthFlowerResourceProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Android 플랫폼 탄생화 리소스 제공자
 * SRP: Android 리소스 읽기만 담당
 * 플랫폼 의존성은 이 모듈에만 격리
 */
class AndroidBirthFlowerResourceProvider(
    private val context: Context
) : BirthFlowerResourceProvider {

    override suspend fun readBirthFlowerJson(): Result<String> =
        withContext(Dispatchers.IO) {
            ExceptionUtil.runCatchingSuspend {
                Logger.debug(TAG, "Reading birth flower JSON from assets")

                context.assets.open(JSON_FILE_NAME).use { inputStream ->
                    inputStream.bufferedReader().use { reader ->
                        reader.readText()
                    }
                }.also {
                    Logger.info(TAG, "Successfully read birth flower JSON")
                }
            }
        }

    companion object {
        private const val TAG = "AndroidBirthFlowerResourceProvider"
        private const val JSON_FILE_NAME = "birthflowers_full.json"
    }
}
