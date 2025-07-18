#!/usr/bin/env kotlin

/**
 * 탄생화 데이터 변환 스크립트
 * 탄생화정리.txt를 읽어서 birthflowers_full.json 생성
 * SRP: 파일 변환만 담당
 */

import java.io.File

// 실행: kotlin ConvertBirthFlowerData.kt

val inputPath = "../탄생화/탄생화정리.txt"
val outputPath = "../app/android/src/main/assets/birthflowers.json"

fun main() {
    println("Starting birth flower data conversion...")
    
    try {
        val inputFile = File(inputPath)
        if (!inputFile.exists()) {
            error("Input file not found: $inputPath")
        }
        
        // 입력 파일 읽기
        val content = inputFile.readText()
        
        // JSON 포맷 정리 (이미 올바른 JSON 형식)
        val formattedJson = formatJson(content)
        
        // 출력 디렉터리 생성
        val outputFile = File(outputPath)
        outputFile.parentFile?.mkdirs()
        
        // 파일 쓰기
        outputFile.writeText(formattedJson)
        
        println("Successfully created: $outputPath")
        println("Total size: ${outputFile.length()} bytes")
        
    } catch (e: java.io.IOException) {
        System.err.println("IO error: ${e.message}")
        e.printStackTrace()
    } catch (e: SecurityException) {
        System.err.println("Security error: ${e.message}")
        e.printStackTrace()
    } catch (e: IllegalStateException) {
        System.err.println("State error: ${e.message}")
        e.printStackTrace()
    }
}

/**
 * JSON 포맷 정리
 */
fun formatJson(content: String): String {
    // 이미 JSON 형식이므로 간단한 정리만
    return content.trim()
}