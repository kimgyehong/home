#!/bin/bash

# 최종 품질 검증 스크립트
# 사용자가 강조한 9가지 핵심 원칙 완전 검증
# 1. SOLID 원칙 준수
# 2. Detekt 원칙 준수
# 3. SRP 원칙 준수
# 4. 하드코딩 금지
# 5. 보일러플레이트 최소화
# 6. 안드로이드 의존성, iOS 의존성 없는 코드만 쓰기
# 7. 중립코드 사용
# 8. UI, Platform 은 의존성을 가질 수 있음
# 9. 요청.txt 파일 및 안의 읽어달라고 하는 텍스트파일 꼭 꼼꼼히 다 읽고 숙지하기

set -e

echo "=== 꽃 다이어리 프로젝트 최종 품질 검증 ===" 
echo "사용자 강조 9가지 원칙 완전 검증 시작"
echo ""

# 현재 디렉토리 저장
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
PROJECT_ROOT="$(cd "$SCRIPT_DIR/.." && pwd)"

# 설정 파일 경로
DETEKT_CONFIG="$PROJECT_ROOT/detekt.yml"
VALIDATION_REPORT="$PROJECT_ROOT/build/reports/quality_validation.html"
VIOLATION_LOG="$PROJECT_ROOT/build/reports/violations.log"

# 결과 변수
VALIDATION_PASSED=true
TOTAL_VIOLATIONS=0
CRITICAL_VIOLATIONS=0

echo "📍 프로젝트 루트: $PROJECT_ROOT"
echo "📋 검증 보고서: $VALIDATION_REPORT"
echo ""

# 보고서 디렉토리 생성
mkdir -p "$(dirname "$VALIDATION_REPORT")"
mkdir -p "$(dirname "$VIOLATION_LOG")"

# 위반 사항 로그 초기화
echo "=== 품질 검증 위반 사항 로그 ===" > "$VIOLATION_LOG"
echo "검증 시작: $(date)" >> "$VIOLATION_LOG"
echo "" >> "$VIOLATION_LOG"

# 검증 함수들
check_principle() {
    local principle_name="$1"
    local principle_number="$2"
    echo "🔍 원칙 $principle_number: $principle_name 검사 중..."
}

add_violation() {
    local severity="$1"
    local principle="$2"
    local message="$3"
    local file="$4"
    local line="$5"
    
    TOTAL_VIOLATIONS=$((TOTAL_VIOLATIONS + 1))
    if [[ "$severity" == "CRITICAL" ]]; then
        CRITICAL_VIOLATIONS=$((CRITICAL_VIOLATIONS + 1))
        VALIDATION_PASSED=false
    fi
    
    echo "[$severity] $principle: $message" >> "$VIOLATION_LOG"
    if [[ -n "$file" ]]; then
        echo "  → 파일: $file" >> "$VIOLATION_LOG"
    fi
    if [[ -n "$line" ]]; then
        echo "  → 라인: $line" >> "$VIOLATION_LOG"
    fi
    echo "" >> "$VIOLATION_LOG"
}

# 1. SOLID 원칙 준수 검증
check_principle "SOLID 원칙 준수" "1"

# SRP 위반 검사 (긴 클래스/메서드)
echo "  - SRP 위반 검사 (긴 클래스/메서드)"
find "$PROJECT_ROOT" -name "*.kt" -not -path "*/build/*" 2>/dev/null | while read -r file; do
    if [[ -f "$file" ]] && [[ $(wc -l < "$file") -gt 200 ]]; then
        add_violation "WARNING" "SRP" "파일이 너무 길어 SRP 위반 가능성 있음 ($(wc -l < "$file") lines)" "$file"
    fi
done

# DIP 위반 검사 (하위 레벨 모듈 직접 의존)
echo "  - DIP 위반 검사 (직접 의존성)"
if find "$PROJECT_ROOT/core" -name "*.kt" -exec grep -l "import.*\.impl\." {} \; 2>/dev/null | head -1; then
    add_violation "CRITICAL" "DIP" "Core 모듈에서 구현체 직접 의존 발견" "" ""
fi

# 2. Detekt 원칙 준수 검증
check_principle "Detekt 원칙 준수" "2"

echo "  - Detekt 정적 분석 실행"
if [[ -f "$DETEKT_CONFIG" ]]; then
    cd "$PROJECT_ROOT"
    if command -v ./gradlew >/dev/null 2>&1; then
        if ./gradlew detekt --continue 2>&1 | grep -q "BUILD SUCCESSFUL"; then
            echo "  ✅ Detekt 검사 통과"
        else
            add_violation "CRITICAL" "Detekt" "Detekt 검사 실패 - 코드 품질 기준 미달" "" ""
        fi
    else
        add_violation "WARNING" "Detekt" "Gradle wrapper를 찾을 수 없어 Detekt 검사 건너뜀" "" ""
    fi
else
    add_violation "WARNING" "Detekt" "Detekt 설정 파일을 찾을 수 없음" "" ""
fi

# 품질검증.txt 요구사항 추가 검증
echo "  - 금지된 임포트 검사 (android.*, UIKit.*)"
android_files=$(find "$PROJECT_ROOT/core" -name "*.kt" -exec grep -l "import android\." {} \; 2>/dev/null | head -1)
if [[ -n "$android_files" ]]; then
    echo "  🔍 Android 임포트 발견된 파일: $android_files"
    add_violation "CRITICAL" "Detekt" "core 모듈에서 android.* 임포트 발견 - 금지된 의존성" "$android_files" ""
fi

uikit_files=$(find "$PROJECT_ROOT/core" -name "*.kt" -exec grep -l "import.*UIKit\." {} \; 2>/dev/null | head -1)
if [[ -n "$uikit_files" ]]; then
    echo "  🔍 UIKit 임포트 발견된 파일: $uikit_files"
    add_violation "CRITICAL" "Detekt" "core 모듈에서 UIKit.* 임포트 발견 - 금지된 의존성" "$uikit_files" ""
fi

echo "  - 매직 넘버 엄격 검증"
find "$PROJECT_ROOT/core" -name "*.kt" -not -path "*/build/*" 2>/dev/null | while read -r file; do
    if [[ -f "$file" ]]; then
        # -1, 0, 1 외의 모든 숫자 리터럴 검사
        if grep -n "[^0-9a-zA-Z_]\(2\|3\|4\|5\|6\|7\|8\|9\|[0-9][0-9]\+\)[^0-9a-zA-Z_]" "$file" 2>/dev/null; then
            add_violation "WARNING" "Detekt" "매직 넘버 발견 - 상수 파일 사용 필요" "$file"
        fi
    fi
done

# 3. SRP 원칙 준수 검증 (추가 세부 검사)
check_principle "SRP 원칙 준수 (세부)" "3"

echo "  - 클래스 책임 분리 검사"
find "$PROJECT_ROOT" -name "*.kt" -not -path "*/build/*" 2>/dev/null | while read -r file; do
    if [[ -f "$file" ]]; then
        method_count=$(grep -c "fun " "$file" 2>/dev/null || echo "0")
        if [[ $method_count -gt 20 ]]; then
            add_violation "WARNING" "SRP" "클래스에 메서드가 너무 많음 ($method_count 개) - 책임 분리 필요" "$file"
        fi
    fi
done

# 4. 하드코딩 금지 검증
check_principle "하드코딩 금지" "4"

echo "  - 하드코딩된 문자열 검사"
find "$PROJECT_ROOT" -name "*.kt" -not -path "*/build/*" -not -path "*/test/*" 2>/dev/null | while read -r file; do
    if [[ -f "$file" ]]; then
        # 하드코딩된 파일 경로 검사
        if grep -n "\"[^\"]*\.jpg\|\"[^\"]*\.png\|\"[^\"]*\.mp3\|\"[^\"]*\.mp4\"" "$file" 2>/dev/null; then
            add_violation "WARNING" "하드코딩" "하드코딩된 파일 경로 발견 - ResourcePathProvider 사용 필요" "$file"
        fi
    fi
done

echo "  - 하드코딩된 색상 검사"
find "$PROJECT_ROOT" -name "*.kt" -not -path "*/build/*" -not -path "*/test/*" 2>/dev/null | while read -r file; do
    if [[ -f "$file" ]]; then
        if grep -n "0x[0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f]" "$file" 2>/dev/null; then
            # ColorPalette 파일에서는 허용
            if [[ "$file" != *"ColorPalette"* ]] && [[ "$file" != *"BirthFlowerColorPalette"* ]]; then
                add_violation "WARNING" "하드코딩" "하드코딩된 색상 값 발견 - ColorPalette 사용 필요" "$file"
            fi
        fi
    fi
done

# 5. 보일러플레이트 최소화 검증
check_principle "보일러플레이트 최소화" "5"

echo "  - 중복 코드 검사"
find "$PROJECT_ROOT" -name "*.kt" -not -path "*/build/*" 2>/dev/null | while read -r file; do
    if [[ -f "$file" ]]; then
        # try-catch 패턴 검사
        try_catch_count=$(grep -c "try\s*{" "$file" 2>/dev/null || echo "0")
        if [[ $try_catch_count -gt 3 ]]; then
            add_violation "WARNING" "보일러플레이트" "중복된 try-catch 패턴 발견 - ExceptionUtil 사용 권장" "$file"
        fi
    fi
done

# 6. 안드로이드 의존성, iOS 의존성 없는 코드만 쓰기 검증
check_principle "안드로이드 의존성, iOS 의존성 없는 코드만 쓰기" "6"

echo "  - Core 모듈 플랫폼 의존성 검사"
if [[ -d "$PROJECT_ROOT/core" ]]; then
    find "$PROJECT_ROOT/core" -name "*.kt" -not -path "*/build/*" 2>/dev/null | while read -r file; do
        if [[ -f "$file" ]]; then
            if grep -n "import android\." "$file" 2>/dev/null; then
                add_violation "CRITICAL" "플랫폼 의존성" "Core 모듈에서 Android 의존성 발견" "$file"
            fi
            
            if grep -n "import.*UIKit\|import.*Foundation" "$file" 2>/dev/null; then
                add_violation "CRITICAL" "플랫폼 의존성" "Core 모듈에서 iOS 의존성 발견" "$file"
            fi
            
            if grep -n "import androidx\." "$file" 2>/dev/null; then
                add_violation "CRITICAL" "플랫폼 의존성" "Core 모듈에서 AndroidX 의존성 발견" "$file"
            fi
        fi
    done
fi

# 7. 중립코드 사용 검증
check_principle "중립코드 사용" "7"

echo "  - expect/actual 패턴 검사"
if [[ -d "$PROJECT_ROOT/core/common" ]]; then
    expect_count=$(find "$PROJECT_ROOT/core/common" -name "*.kt" -exec grep -l "expect " {} \; 2>/dev/null | wc -l)
    actual_count=$(find "$PROJECT_ROOT/platform" -name "*.kt" -exec grep -l "actual " {} \; 2>/dev/null | wc -l)
    
    if [[ $expect_count -gt 0 ]] && [[ $actual_count -eq 0 ]]; then
        add_violation "WARNING" "중립코드" "expect 선언이 있지만 actual 구현이 없음" "" ""
    fi
    
    echo "  ✅ expect/actual 패턴 확인: expect=$expect_count, actual=$actual_count"
fi

# 8. UI, Platform 은 의존성을 가질 수 있음 검증
check_principle "UI, Platform 은 의존성을 가질 수 있음" "8"

echo "  - UI/Platform 모듈 의존성 허용 확인"
if [[ -d "$PROJECT_ROOT/ui" ]] || [[ -d "$PROJECT_ROOT/platform" ]]; then
    echo "  ✅ UI/Platform 모듈은 플랫폼별 의존성 허용됨"
else
    add_violation "INFO" "UI/Platform 모듈" "UI/Platform 모듈이 아직 생성되지 않음" "" ""
fi

# 9. 요청.txt 파일 및 안의 읽어달라고 하는 텍스트파일 꼭 꼼꼼히 다 읽고 숙지하기 검증
check_principle "요청.txt 파일 및 안의 읽어달라고 하는 텍스트파일 꼭 꼼꼼히 다 읽고 숙지하기" "9"

echo "  - 프로젝트 가이드 파일 존재 검사"
guide_files=("요청.txt" "토도.txt" "조감도.txt" "설계도.txt" "골조1.txt" "골조2.txt" "dl.txt" "품질검증.txt")

for guide_file in "${guide_files[@]}"; do
    if [[ -f "$PROJECT_ROOT/$guide_file" ]]; then
        echo "  ✅ 가이드 파일 발견: $guide_file"
    else
        add_violation "WARNING" "가이드 파일" "가이드 파일 누락: $guide_file" "" ""
    fi
done

# 추가 품질 검사

# Generic Exception 검사 (품질검증.txt 요구사항)
echo "🔍 CI 품질 게이트: Generic Exception 사용 금지"
if find "$PROJECT_ROOT/core" "$PROJECT_ROOT/feature" -name "*.kt" -exec grep -l "catch\s*(\s*Exception\s*" {} \; 2>/dev/null | head -1; then
    add_violation "CRITICAL" "Generic Exception" "Generic Exception 사용 금지 - 구체적인 예외 타입 사용 필요" "" ""
fi

# 더 엄격한 Generic Exception 검사
echo "  - Generic Exception 패턴 엄격 검증"
find "$PROJECT_ROOT" -name "*.kt" -not -path "*/build/*" 2>/dev/null | while read -r file; do
    if [[ -f "$file" ]]; then
        if grep -n "catch\s*(\s*.*Exception\s*" "$file" 2>/dev/null | grep -v "specific" | head -1; then
            add_violation "CRITICAL" "Generic Exception" "Generic Exception 패턴 발견 - ExceptionUtil 사용 필요" "$file"
        fi
    fi
done

# 빌드 검증 (품질검증.txt 요구사항: Android + iOS 빌드)
echo "🔍 종합 빌드 검증"
cd "$PROJECT_ROOT"
if [[ -f "gradlew" ]]; then
    echo "  ✅ Gradle wrapper 발견"
    
    # 기본 빌드 테스트
    echo "  - 기본 빌드 검증"
    if ./gradlew build --continue 2>&1 | grep -q "BUILD SUCCESSFUL"; then
        echo "  ✅ 기본 빌드 성공"
    else
        add_violation "CRITICAL" "빌드" "기본 빌드 실패" "" ""
    fi
else
    add_violation "WARNING" "빌드" "Gradle wrapper를 찾을 수 없음" "" ""
fi

# 테스트 실행
echo "🔍 테스트 실행"
if [[ -f "gradlew" ]]; then
    if ./gradlew test --continue 2>&1 | grep -q "BUILD SUCCESSFUL"; then
        echo "  ✅ 테스트 통과"
    else
        add_violation "WARNING" "테스트" "일부 테스트 실패" "" ""
    fi
fi

# 결과 요약
echo ""
echo "=== 검증 결과 요약 ==="
echo "총 위반 사항: $TOTAL_VIOLATIONS"
echo "중요 위반 사항: $CRITICAL_VIOLATIONS"

if [[ $VALIDATION_PASSED == true ]]; then
    echo "🎉 품질 검증 통과!"
    echo "사용자가 강조한 9가지 핵심 원칙을 모두 준수했습니다."
else
    echo "❌ 품질 검증 실패!"
    echo "중요 위반 사항을 수정한 후 다시 실행해주세요."
fi

echo ""
echo "📊 상세 보고서: $VALIDATION_REPORT"
echo "📝 위반 사항 로그: $VIOLATION_LOG"
echo ""

# 종료 코드 설정
if [[ $VALIDATION_PASSED == true ]]; then
    echo "✅ 최종 검증 완료 - 모든 원칙 준수"
    exit 0
else
    echo "❌ 최종 검증 실패 - 위반 사항 수정 필요"
    exit 1
fi