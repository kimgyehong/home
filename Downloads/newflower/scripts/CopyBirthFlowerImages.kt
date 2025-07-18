#!/usr/bin/env kotlin

/**
 * 탄생화 이미지 복사 스크립트
 * SRP: 이미지 파일 복사만 담당
 * 탄생화 폴더의 이미지를 Android drawable 폴더로 복사
 */

import java.io.File
import java.nio.file.Files
import java.nio.file.StandardCopyOption

// 월별 폴더명과 날짜 범위 매핑
val monthFolders = mapOf(
    "1월" to 1,
    "2월" to 2,
    "3월" to 3,
    "4월" to 4,
    "5월" to 5,
    "6월" to 6,
    "7월" to 7,
    "８월" to 8,  // 전각 문자 주의
    "9월" to 9,
    "10월" to 10,
    "11월" to 11,
    "12월" to 12
)

val sourceBasePath = "../탄생화"
val targetBasePath = "../app/android/src/main/res/drawable"

fun main() {
    println("Starting birth flower image copy...")
    
    try {
        // 대상 디렉터리 생성
        val targetDir = File(targetBasePath)
        if (!targetDir.exists()) {
            targetDir.mkdirs()
            println("Created target directory: $targetBasePath")
        }
        
        var copiedCount = 0
        var errorCount = 0
        
        // 각 월별 폴더 처리
        monthFolders.forEach { (folderName, monthNumber) ->
            val monthDir = File(sourceBasePath, folderName)
            
            if (monthDir.exists() && monthDir.isDirectory) {
                println("\nProcessing $folderName...")
                
                monthDir.listFiles { file -> 
                    file.isFile && file.extension.lowercase() == "jpg"
                }?.forEach { imageFile ->
                    try {
                        copyImage(imageFile, targetDir)
                        copiedCount++
                    } catch (e: java.io.IOException) {
                        println("IO error copying ${imageFile.name}: ${e.message}")
                        errorCount++
                    } catch (e: SecurityException) {
                        println("Security error copying ${imageFile.name}: ${e.message}")
                        errorCount++
                    }
                }
            } else {
                println("Warning: Directory not found: ${monthDir.path}")
            }
        }
        
        println("\n=== Copy Complete ===")
        println("Successfully copied: $copiedCount images")
        println("Errors: $errorCount")
        
    } catch (e: java.io.IOException) {
        System.err.println("IO error: ${e.message}")
        e.printStackTrace()
    } catch (e: SecurityException) {
        System.err.println("Security error: ${e.message}")
        e.printStackTrace()
    } catch (e: IllegalArgumentException) {
        System.err.println("Invalid argument: ${e.message}")
        e.printStackTrace()
    }
}

/**
 * 이미지 파일 복사 및 이름 변환
 * Android 리소스 명명 규칙 적용 (소문자, 언더스코어)
 */
fun copyImage(sourceFile: File, targetDir: File) {
    // 파일명을 Android 리소스 규칙에 맞게 변환
    val targetFileName = "flower_${sourceFile.nameWithoutExtension.lowercase()}.${sourceFile.extension}"
    val targetFile = File(targetDir, targetFileName)
    
    Files.copy(
        sourceFile.toPath(),
        targetFile.toPath(),
        StandardCopyOption.REPLACE_EXISTING
    )
    
    println("  Copied: ${sourceFile.name} -> ${targetFile.name}")
}