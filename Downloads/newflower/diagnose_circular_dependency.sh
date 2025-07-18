#!/bin/bash

echo "🔍 순환 의존성 진단 시작..."
echo ""

# 1. Gradle 데몬 종료
echo "1️⃣ Gradle 데몬 종료 중..."
./gradlew --stop

# 2. 빌드 캐시 삭제
echo "2️⃣ 빌드 캐시 삭제 중..."
rm -rf .gradle/
rm -rf ~/.gradle/caches/
find . -type d -name "build" -exec rm -rf {} + 2>/dev/null || true

# 3. processDebugResources 의존성 그래프 확인
echo "3️⃣ processDebugResources 태스크 의존성 확인..."
echo ""
echo "========== Task Dependencies =========="
./gradlew :app:android:processDebugResources --dry-run --info 2>&1 | grep -A 20 -B 5 "processDebugResources" || true

# 4. 순환 참조 패턴 검색
echo ""
echo "4️⃣ 순환 참조 패턴 검색 중..."
echo ""
echo "========== Searching for circular patterns =========="

# build.gradle 파일에서 의심스러운 패턴 검색
echo ">>> build.gradle.kts 파일에서 processDebugResources 검색:"
find . -name "*.gradle.kts" -exec grep -Hn "processDebugResources" {} \; 2>/dev/null || true

echo ""
echo ">>> dependsOn 패턴 검색:"
find . -name "*.gradle.kts" -exec grep -Hn "dependsOn.*processDebugResources" {} \; 2>/dev/null || true

echo ""
echo ">>> task configuration 검색:"
find . -name "*.gradle.kts" -exec grep -Hn "tasks\.\(named\|register\|configureEach\|whenTaskAdded\)" {} \; 2>/dev/null || true

# 5. 간단한 빌드 테스트
echo ""
echo "5️⃣ 간단한 빌드 테스트..."
echo ""
./gradlew :app:android:assembleDebug --stacktrace --no-build-cache --no-daemon 2>&1 | head -50

echo ""
echo "✅ 진단 완료!"
echo ""
echo "🔧 해결 방법:"
echo "1. 위 출력에서 'processDebugResources -> processDebugResources' 패턴을 찾으세요"
echo "2. 해당 파일에서 순환 참조를 제거하세요"
echo "3. 필요시 mustRunAfter를 사용하세요"