package scripts

import java.io.File

/**
 * 탄생화 데이터 JSON 생성 스크립트
 * SRP: 텍스트 파일을 JSON으로 변환하는 책임만 담당
 * 실행: kotlin GenerateBirthFlowerJson.kt
 */
object GenerateBirthFlowerJson {
    
    private const val INPUT_FILE = "탄생화/탄생화정리.txt"
    private const val OUTPUT_FILE = "core/data/src/commonMain/resources/birthflowers.json"
    private const val INDENT = "  "
    
    @JvmStatic
    fun main(args: Array<String>) {
        try {
            println("Starting birth flower JSON generation...")
            
            val inputFile = File(INPUT_FILE)
            if (!inputFile.exists()) {
                error("Input file not found: $INPUT_FILE")
            }
            
            val jsonContent = generateJson(inputFile)
            writeJsonFile(jsonContent)
            
            println("Successfully generated JSON file: $OUTPUT_FILE")
        } catch (e: IllegalStateException) {
            System.err.println("Error generating JSON: ${e.message}")
            e.printStackTrace()
        } catch (e: SecurityException) {
            System.err.println("File access error: ${e.message}")
            e.printStackTrace()
        } catch (e: java.io.IOException) {
            System.err.println("IO error: ${e.message}")
            e.printStackTrace()
        }
    }
    
    /**
     * 텍스트 파일에서 JSON 생성
     */
    private fun generateJson(inputFile: File): String {
        val lines = inputFile.readLines()
        val jsonBuilder = StringBuilder()
        
        jsonBuilder.append("{\n")
        
        lines.forEachIndexed { index, line ->
            if (line.isNotBlank()) {
                // 마지막 줄이 아니면 콤마 추가
                val comma = if (index < lines.size - 1) "," else ""
                jsonBuilder.append("$INDENT$line$comma\n")
            }
        }
        
        jsonBuilder.append("}")
        
        return jsonBuilder.toString()
    }
    
    /**
     * JSON 파일 쓰기
     */
    private fun writeJsonFile(content: String) {
        val outputFile = File(OUTPUT_FILE)
        outputFile.parentFile?.mkdirs()
        outputFile.writeText(content)
    }
}