package com.flowerdiary.platform.android

import android.content.Context
import com.flowerdiary.common.platform.FileStore
import com.flowerdiary.common.utils.ExceptionUtil
import com.flowerdiary.common.utils.Logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

/**
 * Android 파일 저장소 구현
 * 내부 저장소 사용 - 외부 저장소 권한 불필요
 */
actual class AndroidFileStore(
    private val context: Context
) : FileStore {

    private val imageDirectory: File by lazy {
        File(context.filesDir, "images").apply {
            if (!exists()) {
                mkdirs()
            }
        }
    }

    actual override suspend fun saveImage(
        bytes: ByteArray,
        fileName: String
    ): Result<String> = withContext(Dispatchers.IO) {
        ExceptionUtil.runCatchingSuspend {
            Logger.debug(TAG, "Saving image: $fileName")

            val file = File(imageDirectory, fileName)
            file.writeBytes(bytes)

            file.absolutePath.also {
                Logger.info(TAG, "Image saved: $it")
            }
        }
    }

    actual override suspend fun loadImage(
        fileName: String
    ): Result<ByteArray> = withContext(Dispatchers.IO) {
        ExceptionUtil.runCatchingSuspend {
            Logger.debug(TAG, "Loading image: $fileName")

            val file = File(imageDirectory, fileName)
            if (!file.exists()) {
                throw NoSuchFileException(file, reason = "Image not found")
            }

            file.readBytes().also {
                Logger.info(TAG, "Image loaded: $fileName (${it.size} bytes)")
            }
        }
    }

    actual override suspend fun deleteImage(
        fileName: String
    ): Result<Unit> = withContext(Dispatchers.IO) {
        ExceptionUtil.runCatchingSuspend {
            Logger.debug(TAG, "Deleting image: $fileName")

            val file = File(imageDirectory, fileName)
            if (file.exists()) {
                file.delete()
                Logger.info(TAG, "Image deleted: $fileName")
            } else {
                Logger.warning(TAG, "Image not found for deletion: $fileName")
            }
        }
    }

    actual override suspend fun getImagePath(fileName: String): String =
        File(imageDirectory, fileName).absolutePath

    actual override suspend fun clearCache(): Result<Unit> = withContext(Dispatchers.IO) {
        ExceptionUtil.runCatchingSuspend {
            Logger.debug(TAG, "Clearing cache")

            val cacheDir = context.cacheDir
            cacheDir.deleteRecursively()

            Logger.info(TAG, "Cache cleared")
        }
    }

    actual override suspend fun clearAll(): Result<Unit> = withContext(Dispatchers.IO) {
        ExceptionUtil.runCatchingSuspend {
            Logger.debug(TAG, "Clearing all data")

            // 이미지 디렉토리 삭제
            imageDirectory.deleteRecursively()

            // 캐시 디렉토리 삭제
            context.cacheDir.deleteRecursively()

            Logger.info(TAG, "All data cleared")
        }
    }

    companion object {
        private const val TAG = "AndroidFileStore"
    }
}
