enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
    plugins {
        id("org.jetbrains.kotlin.multiplatform") version "2.1.0"
        id("com.android.library") version "8.6.1"
        id("com.android.application") version "8.6.1"
        id("app.cash.sqldelight") version "2.0.2"
        id("io.gitlab.arturbosch.detekt") version "1.23.7"
        id("org.jetbrains.kotlin.plugin.compose") version "2.1.0"
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "FlowerDiary"

// 플랫폼 중립 모듈들을 먼저 include
include(":core:common")
include(":core:domain")
include(":core:data")
include(":feature:diary")

// 플랫폼 종속 모듈들
include(":platform:android")
include(":platform:ios")
include(":ui:android")
include(":ui:ios")

// 앱 모듈들 - 플랫폼 중립적인 shared 추가
include(":app:shared")
include(":app:android")
include(":app:ios")