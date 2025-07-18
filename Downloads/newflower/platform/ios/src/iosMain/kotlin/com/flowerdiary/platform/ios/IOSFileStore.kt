package com.flowerdiary.platform.ios

import com.flowerdiary.common.platform.FileStore
import com.flowerdiary.common.utils.ExceptionUtil
import kotlinx.cinterop.*
import platform.Foundation.*
import platform.darwin.NSObject

/**
 * iOS 파일 저장소 구현
 * SRP: iOS 플랫폼의 파일 저장/읽기만 담당
 * NSFileManager를 사용하여 문서 디렉터리에 파일 관리
 */
actual class IOSFileStore : FileStore {
    
    private val fileManager = NSFileManager.defaultManager
    
    actual override suspend fun saveImage(bytes: ByteArray, fileName: String): Result<String> =
        ExceptionUtil.runCatchingSuspend {
            val documentsPath = getDocumentsDirectory()
            val filePath = "$documentsPath/$fileName"
            
            val nsData = bytes.toNSData()
            val success = nsData.writeToFile(filePath, atomically = true)
            
            if (success) {
                filePath
            } else {
                throw IOSFileStoreException("Failed to save file: $fileName")
            }
        }
    
    actual override suspend fun loadImage(fileName: String): Result<ByteArray> =
        ExceptionUtil.runCatchingSuspend {
            val documentsPath = getDocumentsDirectory()
            val filePath = "$documentsPath/$fileName"
            
            val nsData = NSData.dataWithContentsOfFile(filePath)
                ?: throw IOSFileStoreException("File not found: $fileName")
            
            nsData.toByteArray()
        }
    
    actual override suspend fun deleteImage(fileName: String): Result<Unit> =
        ExceptionUtil.runCatchingSuspend {
            val documentsPath = getDocumentsDirectory()
            val filePath = "$documentsPath/$fileName"
            
            val success = fileManager.removeItemAtPath(filePath, error = null)
            if (!success) {
                throw IOSFileStoreException("Failed to delete file: $fileName")
            }
        }
    
    actual override suspend fun getImagePath(fileName: String): String {
        val documentsPath = getDocumentsDirectory()
        return "$documentsPath/$fileName"
    }
    
    actual override suspend fun clearCache(): Result<Unit> =
        ExceptionUtil.runCatchingSuspend {
            val cacheDirectory = getCacheDirectory()
            val contents = fileManager.contentsOfDirectoryAtPath(cacheDirectory, error = null)
                ?: return@runCatchingSuspend
            
            contents.forEach { item ->
                val filePath = "$cacheDirectory/$item"
                fileManager.removeItemAtPath(filePath, error = null)
            }
        }
    
    actual override suspend fun clearAll(): Result<Unit> =
        ExceptionUtil.runCatchingSuspend {
            val documentsPath = getDocumentsDirectory()
            val contents = fileManager.contentsOfDirectoryAtPath(documentsPath, error = null)
                ?: return@runCatchingSuspend
            
            contents.forEach { item ->
                val filePath = "$documentsPath/$item"
                fileManager.removeItemAtPath(filePath, error = null)
            }
        }
    
    /**
     * 문서 디렉터리 경로 반환
     */
    private fun getDocumentsDirectory(): String {
        val paths = NSSearchPathForDirectoriesInDomains(
            NSDocumentDirectory,
            NSUserDomainMask,
            true
        )
        return paths.first() as String
    }
    
    /**
     * 캐시 디렉터리 경로 반환
     */
    private fun getCacheDirectory(): String {
        val paths = NSSearchPathForDirectoriesInDomains(
            NSCachesDirectory,
            NSUserDomainMask,
            true
        )
        return paths.first() as String
    }
}

/**
 * ByteArray를 NSData로 변환
 */
private fun ByteArray.toNSData(): NSData {
    return NSData.create(
        bytes = this.refTo(0),
        length = this.size.toULong()
    )
}

/**
 * NSData를 ByteArray로 변환
 */
private fun NSData.toByteArray(): ByteArray {
    return ByteArray(this.length.toInt()) { index ->
        this.bytes?.reinterpret<ByteVar>()?.get(index) ?: 0
    }
}

/**
 * iOS 파일 저장소 예외
 */
class IOSFileStoreException(message: String) : Exception(message)