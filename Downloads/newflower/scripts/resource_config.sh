#!/bin/bash

# 리소스 설정 파일
# SRP: 리소스 경로 설정만 담당
# 하드코딩 금지 원칙 준수

# 기본 경로 설정
export SOURCE_BASE_DIR="/mnt/c/Users/endof/Downloads/newflower/탄생화"
export PROJECT_BASE_DIR="/mnt/c/Users/endof/Downloads/newflower"

# 플랫폼별 타겟 디렉토리 설정 함수
get_android_target_dir() {
    echo "$PROJECT_BASE_DIR/app/android/src/main/res"
}

get_android_assets_dir() {
    echo "$PROJECT_BASE_DIR/app/android/src/main/assets"
}

get_ios_target_dir() {
    echo "$PROJECT_BASE_DIR/app/ios/Resources"
}

# 리소스 카테고리 설정
declare -A RESOURCE_CATEGORIES=(
    ["BIRTHFLOWER"]="birthflower"
    ["LOADING"]="loading"
    ["UI"]="ui"
    ["BGM"]="bgm"
    ["VIDEOS"]="videos"
    ["ANIMATIONS"]="animations"
    ["COLLECTION"]="collection"
    ["APP"]="app"
)

# 지원하는 이미지 확장자
declare -a IMAGE_EXTENSIONS=("jpg" "jpeg" "png" "gif" "bmp" "webp")

# 지원하는 오디오 확장자
declare -a AUDIO_EXTENSIONS=("mp3" "wav" "aac" "ogg" "m4a")

# 지원하는 비디오 확장자
declare -a VIDEO_EXTENSIONS=("mp4" "avi" "mov" "wmv" "flv" "webm")

# 설정 검증 함수
validate_config() {
    if [[ ! -d "$SOURCE_BASE_DIR" ]]; then
        echo "❌ 소스 디렉토리가 존재하지 않습니다: $SOURCE_BASE_DIR"
        exit 1
    fi
    
    if [[ ! -d "$PROJECT_BASE_DIR" ]]; then
        echo "❌ 프로젝트 디렉토리가 존재하지 않습니다: $PROJECT_BASE_DIR"
        exit 1
    fi
    
    echo "✅ 설정 검증 완료"
}

# 파일 유형 확인 함수
is_image_file() {
    local file="$1"
    local extension="${file##*.}"
    extension="${extension,,}"
    
    for ext in "${IMAGE_EXTENSIONS[@]}"; do
        if [[ "$extension" == "$ext" ]]; then
            return 0
        fi
    done
    return 1
}

is_audio_file() {
    local file="$1"
    local extension="${file##*.}"
    extension="${extension,,}"
    
    for ext in "${AUDIO_EXTENSIONS[@]}"; do
        if [[ "$extension" == "$ext" ]]; then
            return 0
        fi
    done
    return 1
}

is_video_file() {
    local file="$1"
    local extension="${file##*.}"
    extension="${extension,,}"
    
    for ext in "${VIDEO_EXTENSIONS[@]}"; do
        if [[ "$extension" == "$ext" ]]; then
            return 0
        fi
    done
    return 1
}

# 디렉토리 생성 함수
create_target_directories() {
    local target_base="$1"
    local assets_base="$2"
    
    echo "📁 디렉토리 생성 중..."
    
    # 리소스 카테고리별 디렉토리 생성
    for category in "${RESOURCE_CATEGORIES[@]}"; do
        mkdir -p "$assets_base/$category"
    done
    
    # 기본 디렉토리 생성
    mkdir -p "$target_base/drawable"
    mkdir -p "$target_base/raw"
    mkdir -p "$assets_base/collection/pokedex_icon"
    mkdir -p "$assets_base/animations/bookmark"
    
    echo "✅ 디렉토리 생성 완료"
}

# 파일 복사 함수
copy_file_safely() {
    local source="$1"
    local target="$2"
    
    if [[ -f "$source" ]]; then
        cp "$source" "$target"
        return 0
    else
        echo "  ⚠️  파일 없음: $(basename "$source")"
        return 1
    fi
}

# 파일 개수 계산 함수
count_files() {
    local dir="$1"
    local pattern="$2"
    
    if [[ -d "$dir" ]]; then
        find "$dir" -name "$pattern" 2>/dev/null | wc -l
    else
        echo "0"
    fi
}

# 설정 내보내기
export -f get_android_target_dir
export -f get_android_assets_dir
export -f get_ios_target_dir
export -f validate_config
export -f is_image_file
export -f is_audio_file
export -f is_video_file
export -f create_target_directories
export -f copy_file_safely
export -f count_files