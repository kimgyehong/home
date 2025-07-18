package com.flowerdiary.common.platform

/**
 * 플랫폼별 파일 저장소 인터페이스
 * 이미지 저장/로드 기능 제공
 * 순수 Kotlin 인터페이스 - platform 모듈에서 구현
 */
interface FileStore {
    suspend fun saveImage(bytes: ByteArray, fileName: String): Result<String>
    suspend fun loadImage(fileName: String): Result<ByteArray>
    suspend fun deleteImage(fileName: String): Result<Unit>
    suspend fun getImagePath(fileName: String): String
    suspend fun clearCache(): Result<Unit>
    suspend fun clearAll(): Result<Unit>
}
