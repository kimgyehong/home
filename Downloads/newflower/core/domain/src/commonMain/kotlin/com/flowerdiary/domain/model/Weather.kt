package com.flowerdiary.domain.model

/**
 * 일기 작성 시 날씨 상태
 * 날씨에 따른 배경 테마나 추천 기능에 활용
 */
enum class Weather(val icon: String, val displayName: String) {
    SUNNY("☀️", "맑음"),
    CLOUDY("☁️", "흐림"),
    RAINY("🌧️", "비"),
    SNOWY("❄️", "눈"),
    WINDY("🌬️", "바람"),
    FOGGY("🌫️", "안개"),
    STORMY("⛈️", "폭풍"),
    PARTLY_CLOUDY("⛅", "구름 조금");
    
    companion object {
        /**
         * 문자열로부터 Weather 찾기
         */
        fun fromName(name: String): Weather? = entries.find { it.name == name }
    }
}
