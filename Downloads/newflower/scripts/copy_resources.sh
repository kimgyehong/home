#!/bin/bash

# 리소스 파일 복사 스크립트
# SRP: 탄생화 폴더의 모든 리소스를 프로젝트에 복사하는 작업만 담당
# 하드코딩 금지, SOLID 원칙 준수

set -e

# 설정 파일 로드
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
source "$SCRIPT_DIR/resource_config.sh"

echo "=== Flower Diary 리소스 파일 복사 스크립트 ==="

# 설정 검증
validate_config

# 플랫폼 선택
PLATFORM="${1:-android}"

case "$PLATFORM" in
    "android")
        TARGET_DIR=$(get_android_target_dir)
        ASSETS_DIR=$(get_android_assets_dir)
        ;;
    "ios")
        TARGET_DIR=$(get_ios_target_dir)
        ASSETS_DIR="$TARGET_DIR"
        ;;
    *)
        echo "❌ 지원하지 않는 플랫폼: $PLATFORM"
        echo "사용법: $0 [android|ios]"
        exit 1
        ;;
esac

echo "🎯 플랫폼: $PLATFORM"
echo "📂 타겟 디렉토리: $TARGET_DIR"
echo "📁 에셋 디렉토리: $ASSETS_DIR"

# 디렉토리 생성
create_target_directories "$TARGET_DIR" "$ASSETS_DIR"

# 리소스 복사 함수들
copy_birthflower_images() {
    echo "🌸 탄생화 이미지 복사 중..."
    local copied=0
    
    for month_dir in "$SOURCE_BASE_DIR"/*/; do
        if [[ -d "$month_dir" ]]; then
            local month_name=$(basename "$month_dir")
            if [[ "$month_name" =~ ^[0-9]월$ ]] || [[ "$month_name" == "８월" ]]; then
                echo "  - $month_name 처리 중..."
                for image_file in "$month_dir"/*.jpg; do
                    if [[ -f "$image_file" ]]; then
                        local filename=$(basename "$image_file")
                        if copy_file_safely "$image_file" "$ASSETS_DIR/${RESOURCE_CATEGORIES[BIRTHFLOWER]}/$filename"; then
                            ((copied++))
                        fi
                    fi
                done
            fi
        fi
    done
    
    echo "  ✅ 탄생화 이미지 $copied 개 복사 완료"
}

copy_bgm_files() {
    echo "🎵 BGM 파일 복사 중..."
    local copied=0
    
    local bgm_files=("bgm.mp3" "bgm2.mp3" "bgm3.mp3" "bgm4.mp3")
    
    for bgm_file in "${bgm_files[@]}"; do
        if copy_file_safely "$SOURCE_BASE_DIR/$bgm_file" "$ASSETS_DIR/${RESOURCE_CATEGORIES[BGM]}/$bgm_file"; then
            ((copied++))
        fi
    done
    
    echo "  ✅ BGM 파일 $copied 개 복사 완료"
}

copy_loading_images() {
    echo "⏳ 로딩 배경 이미지 복사 중..."
    local copied=0
    
    if [[ -d "$SOURCE_BASE_DIR/로딩 배경" ]]; then
        for i in {1..7}; do
            if copy_file_safely "$SOURCE_BASE_DIR/로딩 배경/loading$i.png" "$ASSETS_DIR/${RESOURCE_CATEGORIES[LOADING]}/loading$i.png"; then
                ((copied++))
            fi
        done
    fi
    
    echo "  ✅ 로딩 이미지 $copied 개 복사 완료"
}

copy_ui_resources() {
    echo "🎨 UI 리소스 복사 중..."
    local copied=0
    
    if [[ -d "$SOURCE_BASE_DIR/ui" ]]; then
        local ui_files=("gardenwindow.png" "gear.png" "notebook.png" "seal.png" "sidebar.png" "start.png" "underbar.png")
        
        for ui_file in "${ui_files[@]}"; do
            if copy_file_safely "$SOURCE_BASE_DIR/ui/$ui_file" "$ASSETS_DIR/${RESOURCE_CATEGORIES[UI]}/$ui_file"; then
                ((copied++))
            fi
        done
    fi
    
    echo "  ✅ UI 리소스 $copied 개 복사 완료"
}

copy_app_resources() {
    echo "🖼️ 앱 리소스 복사 중..."
    local copied=0
    
    if [[ -d "$SOURCE_BASE_DIR/app cover art" ]]; then
        local app_files=("icon.png" "title.png" "title2.png" "title3.png" "opening.png" "flowering.png" "flowering3.jpg")
        
        for app_file in "${app_files[@]}"; do
            if copy_file_safely "$SOURCE_BASE_DIR/app cover art/$app_file" "$ASSETS_DIR/${RESOURCE_CATEGORIES[APP]}/$app_file"; then
                ((copied++))
            fi
        done
        
        # 플랫폼별 앱 아이콘 복사
        if [[ "$PLATFORM" == "android" ]]; then
            copy_file_safely "$SOURCE_BASE_DIR/app cover art/icon.png" "$TARGET_DIR/drawable/ic_launcher.png"
        fi
    fi
    
    echo "  ✅ 앱 리소스 $copied 개 복사 완료"
}

copy_video_resources() {
    echo "🎬 비디오 파일 복사 중..."
    local copied=0
    
    # UI 디렉토리의 비디오
    if copy_file_safely "$SOURCE_BASE_DIR/ui/opening.mp4" "$ASSETS_DIR/${RESOURCE_CATEGORIES[VIDEOS]}/opening.mp4"; then
        ((copied++))
    fi
    
    # 애니메이션 디렉토리의 비디오
    if [[ -d "$SOURCE_BASE_DIR/애니메이션" ]]; then
        local video_files=("Blooming_flower.mp4" "flowering.mp4" "flowering1.mp4" "flowering2.mp4" "scattering.mp4")
        
        for video_file in "${video_files[@]}"; do
            if copy_file_safely "$SOURCE_BASE_DIR/애니메이션/$video_file" "$ASSETS_DIR/${RESOURCE_CATEGORIES[VIDEOS]}/$video_file"; then
                ((copied++))
            fi
        done
    fi
    
    echo "  ✅ 비디오 파일 $copied 개 복사 완료"
}

copy_animation_resources() {
    echo "🎭 애니메이션 이미지 복사 중..."
    local copied=0
    
    if [[ -d "$SOURCE_BASE_DIR/애니메이션" ]]; then
        local animation_files=("blooming.png" "blooming1.png" "blooming2.png" "falling.png" "falling1.png" "falling2.png" "falling3.png" "falling4.png" "growing.png" "growing1.png" "growing2.png" "scatter.png" "scatter1.png" "scatter2.png" "garden.png" "garden1.png")
        
        for animation_file in "${animation_files[@]}"; do
            if copy_file_safely "$SOURCE_BASE_DIR/애니메이션/$animation_file" "$ASSETS_DIR/${RESOURCE_CATEGORIES[ANIMATIONS]}/$animation_file"; then
                ((copied++))
            fi
        done
        
        # 북마크 애니메이션
        if [[ -d "$SOURCE_BASE_DIR/애니메이션/bookmark" ]]; then
            for bookmark_file in "$SOURCE_BASE_DIR/애니메이션/bookmark/"*; do
                if [[ -f "$bookmark_file" ]]; then
                    local filename=$(basename "$bookmark_file")
                    if copy_file_safely "$bookmark_file" "$ASSETS_DIR/${RESOURCE_CATEGORIES[ANIMATIONS]}/bookmark/$filename"; then
                        ((copied++))
                    fi
                fi
            done
        fi
    fi
    
    echo "  ✅ 애니메이션 이미지 $copied 개 복사 완료"
}

copy_collection_resources() {
    echo "📚 도감 관련 이미지 복사 중..."
    local copied=0
    
    if [[ -d "$SOURCE_BASE_DIR/data" ]]; then
        local collection_files=("Silhouette.png" "flowering.png" "frame.png" "frame2.png" "layout.png" "poke.png" "pokedex.png")
        
        for collection_file in "${collection_files[@]}"; do
            if copy_file_safely "$SOURCE_BASE_DIR/data/$collection_file" "$ASSETS_DIR/${RESOURCE_CATEGORIES[COLLECTION]}/$collection_file"; then
                ((copied++))
            fi
        done
        
        # 포켓덱스 아이콘
        if [[ -d "$SOURCE_BASE_DIR/data/pokedex icon" ]]; then
            for icon_file in "$SOURCE_BASE_DIR/data/pokedex icon/"*; do
                if [[ -f "$icon_file" ]]; then
                    local filename=$(basename "$icon_file")
                    if copy_file_safely "$icon_file" "$ASSETS_DIR/${RESOURCE_CATEGORIES[COLLECTION]}/pokedex_icon/$filename"; then
                        ((copied++))
                    fi
                fi
            done
        fi
    fi
    
    echo "  ✅ 도감 리소스 $copied 개 복사 완료"
}

# 리소스 복사 실행
copy_birthflower_images
copy_bgm_files
copy_loading_images
copy_ui_resources
copy_app_resources
copy_video_resources
copy_animation_resources
copy_collection_resources

# 결과 요약
echo "📊 복사 결과 요약:"
echo "  - 탄생화 이미지: $(count_files "$ASSETS_DIR/${RESOURCE_CATEGORIES[BIRTHFLOWER]}" "*.jpg")개"
echo "  - BGM 파일: $(count_files "$ASSETS_DIR/${RESOURCE_CATEGORIES[BGM]}" "*.mp3")개"
echo "  - 로딩 이미지: $(count_files "$ASSETS_DIR/${RESOURCE_CATEGORIES[LOADING]}" "*.png")개"
echo "  - UI 이미지: $(count_files "$ASSETS_DIR/${RESOURCE_CATEGORIES[UI]}" "*.png")개"
echo "  - 앱 리소스: $(count_files "$ASSETS_DIR/${RESOURCE_CATEGORIES[APP]}" "*.*")개"
echo "  - 비디오 파일: $(count_files "$ASSETS_DIR/${RESOURCE_CATEGORIES[VIDEOS]}" "*.mp4")개"
echo "  - 애니메이션 이미지: $(count_files "$ASSETS_DIR/${RESOURCE_CATEGORIES[ANIMATIONS]}" "*.png")개"
echo "  - 도감 이미지: $(count_files "$ASSETS_DIR/${RESOURCE_CATEGORIES[COLLECTION]}" "*.*")개"

# 권한 설정
echo "🔐 파일 권한 설정 중..."
if [[ -d "$ASSETS_DIR" ]]; then
    chmod -R 644 "$ASSETS_DIR"/* 2>/dev/null || true
fi
if [[ -d "$TARGET_DIR" ]]; then
    chmod -R 644 "$TARGET_DIR"/* 2>/dev/null || true
fi

echo "✅ 리소스 파일 복사 완료!"
echo ""
echo "📝 다음 단계:"
echo "1. IDE에서 프로젝트 동기화"
echo "2. 리소스 파일 경로 확인"
echo "3. 빌드 테스트 실행"
echo ""
echo "💡 참고사항:"
echo "- 플랫폼: $PLATFORM"
echo "- 타겟 디렉토리: $TARGET_DIR"
echo "- 에셋 디렉토리: $ASSETS_DIR"
echo ""
echo "🚀 복사 완료! 이제 앱을 빌드할 수 있습니다."