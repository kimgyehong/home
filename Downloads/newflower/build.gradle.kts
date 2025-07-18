plugins {
    // 플러그인은 settings.gradle.kts에서 버전 관리
    kotlin("multiplatform") apply false
    id("com.android.library") apply false
    id("com.android.application") apply false
    id("app.cash.sqldelight") apply false
    id("io.gitlab.arturbosch.detekt") apply false
}

allprojects {
    group = "com.flowerdiary"
    version = "1.0.0"
}

subprojects {
    // Detekt 플러그인 적용을 Android 앱 모듈에서 제외
    if (name != "android") {
        apply(plugin = "io.gitlab.arturbosch.detekt")
        
        afterEvaluate {
            extensions.findByType<io.gitlab.arturbosch.detekt.extensions.DetektExtension>()?.apply {
                toolVersion = Versions.detekt
                config.setFrom("$rootDir/detekt.yml")
                buildUponDefaultConfig = true
            }
        }
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.layout.buildDirectory)
}