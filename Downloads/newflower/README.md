# 🌸 꽃 다이어리 (Flower Diary)

매일매일 피어나는 당신의 이야기를 담는 감성 일기 앱

## 📱 프로젝트 개요

꽃 다이어리는 Kotlin Multiplatform(KMP)으로 개발된 크로스 플랫폼 일기 앱입니다. 
365일 각각의 탄생화와 함께 특별한 일기를 작성하고, 아름다운 꽃 도감을 수집해보세요.

### 주요 기능
- 📝 **일기 작성**: 기분, 날씨와 함께 오늘의 이야기 기록
- 🌺 **365일 탄생화**: 매일 새로운 탄생화를 발견하고 수집
- 🎨 **커스터마이징**: 폰트, 색상, 배경 테마로 나만의 일기 꾸미기
- 🎵 **BGM**: 4가지 감성 배경음악과 함께하는 힐링 타임
- 📖 **도감**: 수집한 탄생화들을 한눈에 볼 수 있는 컬렉션

## 🏗️ 프로젝트 구조

```
root/
├── core/                    # 핵심 비즈니스 로직
│   ├── common/             # 플랫폼 중립적 공통 모듈
│   ├── domain/             # 도메인 모델 및 유스케이스
│   └── data/               # 데이터 레이어 (SQLDelight)
├── feature/                # 기능별 프레젠테이션 로직
│   └── diary/              # 일기 관련 ViewModel, State
├── platform/               # 플랫폼별 구현체
│   ├── android/            # Android actual 구현
│   └── ios/                # iOS actual 구현
├── ui/                     # UI 레이어
│   ├── android/            # Android Compose UI
│   └── ios/                # iOS SwiftUI
└── app/                    # 애플리케이션 진입점
    ├── android/            # Android 앱
    └── ios/                # iOS 앱
```

## 🛠️ 기술 스택

### 공통
- **Kotlin Multiplatform**: 크로스 플랫폼 코드 공유
- **SQLDelight**: 타입 안전한 SQL 데이터베이스
- **Kotlinx Coroutines**: 비동기 프로그래밍
- **Kotlinx Serialization**: JSON 직렬화
- **Koin**: 의존성 주입

### Android
- **Jetpack Compose**: 선언적 UI 프레임워크
- **Material 3**: 최신 Material Design 시스템
- **Navigation Compose**: 화면 전환 관리
- **Media3 ExoPlayer**: 비디오 재생

### 코드 품질
- **Detekt**: 정적 코드 분석
- **SOLID 원칙**: 객체 지향 설계 원칙 준수
- **Clean Architecture**: 계층 분리 아키텍처

## 🚀 시작하기

### 요구사항
- JDK 17 이상
- Android Studio Hedgehog 이상
- Kotlin 2.0.0 이상

### 빌드 방법

```bash
# 프로젝트 클론
git clone https://github.com/your-username/flower-diary.git
cd flower-diary

# Android 앱 빌드
./gradlew :app:android:assembleDebug

# 테스트 실행
./gradlew test

# Detekt 실행
./gradlew detekt
```

## 📋 주요 설계 원칙

### 1. 플랫폼 중립성
- core 모듈에는 Android/iOS 의존성 없음
- expect/actual 패턴으로 플랫폼별 구현 분리
- 비즈니스 로직은 100% 공유

### 2. SOLID 원칙
- **SRP**: 각 클래스는 단일 책임만 담당
- **OCP**: 확장에는 열려있고 수정에는 닫혀있음
- **LSP**: 리스코프 치환 원칙 준수
- **ISP**: 인터페이스 분리 원칙 적용
- **DIP**: 의존성 역전 원칙으로 유연한 구조

### 3. 코드 품질
- 매직 넘버 금지 (모든 상수는 Config, Dimens, ColorPalette에 정의)
- Generic Exception 금지 (ExceptionUtil로 통합 처리)
- Detekt 규칙 100% 준수
- CI/CD로 자동 품질 검증

## 🎨 주요 화면

1. **오프닝**: 스킵 가능한 인트로 영상
2. **타이틀**: 앱 진입 화면
3. **메인**: Garden Window 배경의 메뉴 화면
4. **일기 목록**: 작성한 일기들을 한눈에
5. **일기 편집**: 탄생화 배경에 노트북 레이어로 감성적인 작성
6. **도감**: 365일 탄생화 컬렉션
7. **설정**: BGM, 테마, 폰트 등 커스터마이징

## 📄 라이선스

이 프로젝트는 MIT 라이선스 하에 배포됩니다.

## 🤝 기여하기

프로젝트에 기여하고 싶으시다면:
1. Fork 하기
2. Feature 브랜치 만들기 (`git checkout -b feature/amazing-feature`)
3. 변경사항 커밋하기 (`git commit -m 'Add amazing feature'`)
4. 브랜치에 Push 하기 (`git push origin feature/amazing-feature`)
5. Pull Request 열기

## 📞 문의

프로젝트 관련 문의사항은 [Issues](https://github.com/your-username/flower-diary/issues)에 남겨주세요.