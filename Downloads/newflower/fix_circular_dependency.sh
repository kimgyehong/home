#!/bin/bash

echo "🔧 순환 의존성 문제 해결 시작..."

# 1. 모든 빌드 디렉토리 삭제
echo "1️⃣ 빌드 디렉토리 정리 중..."
find . -type d -name "build" -exec rm -rf {} + 2>/dev/null || true
find . -type d -name ".gradle" -exec rm -rf {} + 2>/dev/null || true

# 2. Gradle 캐시 정리
echo "2️⃣ Gradle 캐시 정리 중..."
rm -rf ~/.gradle/caches/modules-* 2>/dev/null || true
rm -rf ~/.gradle/caches/build-cache-* 2>/dev/null || true
rm -rf ~/.gradle/caches/transforms-* 2>/dev/null || true

# 3. Android 빌드 캐시 정리
echo "3️⃣ Android 빌드 캐시 정리 중..."
rm -rf ~/.android/build-cache 2>/dev/null || true
rm -rf ~/.android/cache 2>/dev/null || true

# 4. 프로젝트 clean
echo "4️⃣ 프로젝트 clean 중..."
./gradlew clean --no-daemon --no-build-cache || true

# 5. Gradle wrapper 재생성
echo "5️⃣ Gradle wrapper 재생성 중..."
./gradlew wrapper --no-daemon || true

# 6. 의존성 다시 다운로드
echo "6️⃣ 의존성 새로 다운로드 중..."
./gradlew dependencies --refresh-dependencies --no-daemon || true

echo "✅ 정리 완료!"
echo ""
echo "🔨 이제 다음 명령어로 빌드를 시도하세요:"
echo "./gradlew :app:android:assembleDebug --no-daemon --no-build-cache --stacktrace"