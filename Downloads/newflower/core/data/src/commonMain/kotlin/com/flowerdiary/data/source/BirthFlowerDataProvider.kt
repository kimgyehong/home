package com.flowerdiary.data.source

/**
 * 탄생화 원본 데이터 제공자
 * SRP: 탄생화 정적 데이터 제공만 담당
 * 실제 애플리케이션에서는 JSON 파일이나 API에서 데이터를 가져올 수 있음
 */
class BirthFlowerDataProvider {
    
    /**
     * 1월 탄생화 데이터
     */
    fun getJanuaryData(): Map<String, BirthFlowerData> = mapOf(
        "01-01" to BirthFlowerData("스노드롭", "Snowdrop", "희망", "새해 첫날을 장식하는 하얀 꽃"),
        "01-02" to BirthFlowerData("노랑수선화", "Yellow Narcissus", "사랑에 답하여", "사랑하는 이의 마음에 답하는 꽃"),
        "01-03" to BirthFlowerData("샤프란", "Saffron", "후회없는 청춘", "젊음의 열정을 상징하는 꽃"),
        "01-04" to BirthFlowerData("히아신스", "Hyacinth", "차분한 사랑", "조용하고 깊은 사랑을 의미"),
        "01-05" to BirthFlowerData("노루귀", "Hepatica", "인내", "추위를 견디며 피어나는 꽃"),
        "01-06" to BirthFlowerData("흰제비꽃", "White Violet", "순진무구한 사랑", "순수한 사랑을 상징"),
        "01-07" to BirthFlowerData("튤립", "Tulip", "실연", "사랑의 아픔을 의미"),
        "01-08" to BirthFlowerData("보라빛 제비꽃", "Purple Violet", "사랑", "깊은 사랑을 의미"),
        "01-09" to BirthFlowerData("노랑 제비꽃", "Yellow Violet", "수줍은 사랑", "부끄러운 마음을 담은 꽃"),
        "01-10" to BirthFlowerData("회양목", "Boxwood", "참고 견뎌냄", "어려움을 견디는 강인함"),
        "01-11" to BirthFlowerData("측백나무", "Thuja", "견고한 우정", "변치 않는 우정을 상징"),
        "01-12" to BirthFlowerData("스위트 알리섬", "Sweet Alyssum", "빼어난 미모", "아름다운 외모를 칭찬"),
        "01-13" to BirthFlowerData("수선화", "Narcissus", "신비", "신비로운 매력을 지닌 꽃"),
        "01-14" to BirthFlowerData("시클라멘", "Cyclamen", "내성적 성격", "조용한 성격을 의미"),
        "01-15" to BirthFlowerData("가시", "Thorn", "엄격", "엄격함과 보호를 상징"),
        "01-16" to BirthFlowerData("노랑 히아신스", "Yellow Hyacinth", "승부", "경쟁과 승리를 의미"),
        "01-17" to BirthFlowerData("수영", "Rumex", "친근한 정", "따뜻한 정을 나누는 풀"),
        "01-18" to BirthFlowerData("어저귀", "Buttercup", "약속", "소중한 약속을 의미"),
        "01-19" to BirthFlowerData("소나무", "Pine", "불로장수", "영원한 생명력을 상징"),
        "01-20" to BirthFlowerData("미나리아재비", "Buttercup", "천진난만", "순수한 마음을 의미"),
        "01-21" to BirthFlowerData("담쟁이덩굴", "Ivy", "우정", "끈끈한 우정을 상징"),
        "01-22" to BirthFlowerData("이끼", "Moss", "모성애", "어머니의 사랑을 상징"),
        "01-23" to BirthFlowerData("부들", "Bulrush", "순종", "순종적인 마음을 의미"),
        "01-24" to BirthFlowerData("가을에 피는 샤프란", "Autumn Crocus", "절도의 미", "절제된 아름다움"),
        "01-25" to BirthFlowerData("점나도나물", "Cerastium", "순진", "순진한 마음을 상징"),
        "01-26" to BirthFlowerData("미모사", "Mimosa", "예민한 마음", "섬세한 감성을 의미"),
        "01-27" to BirthFlowerData("마가목", "Rowan", "게으름을 모르는 마음", "부지런함을 상징"),
        "01-28" to BirthFlowerData("검은 포폴라", "Black Poplar", "용기", "용기와 결단력을 상징"),
        "01-29" to BirthFlowerData("이끼", "Moss", "모성애", "어머니의 따뜻한 사랑"),
        "01-30" to BirthFlowerData("메리 애쉬골드", "Marigold", "반드시 오고야 말 행복", "확실한 행복을 의미"),
        "01-31" to BirthFlowerData("노란 샤프란", "Yellow Saffron", "청춘의 환희", "젊음의 기쁨을 상징")
    )
    
    /**
     * 모든 탄생화 데이터 통합
     * 실제 구현에서는 12개월 모든 데이터를 포함해야 함
     */
    fun getAllData(): Map<String, BirthFlowerData> {
        return getJanuaryData() + 
            // getFebruaryData() +
            // getMarchData() +
            // ... 12개월 데이터
            emptyMap()
    }
}

/**
 * 탄생화 데이터 클래스
 */
data class BirthFlowerData(
    val nameKr: String,
    val nameEn: String,
    val meaning: String,
    val description: String
)