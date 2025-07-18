# 꽃 다이어리 ProGuard 규칙

# Kotlin
-keep class kotlin.** { *; }
-keep class kotlin.Metadata { *; }
-dontwarn kotlin.**

# Kotlinx Serialization
-keepattributes *Annotation*, InnerClasses
-dontnote kotlinx.serialization.AnnotationsKt
-keepclassmembers class kotlinx.serialization.json.** {
    *** Companion;
}
-keepclasseswithmembers class kotlinx.serialization.json.** {
    kotlinx.serialization.KSerializer serializer(...);
}

# SQLDelight
-keep class com.flowerdiary.data.database.** { *; }
-keep class com.squareup.sqldelight.** { *; }

# Koin
-keep class org.koin.** { *; }
-keep class org.koin.core.** { *; }
-keep class org.koin.dsl.** { *; }

# Compose
-keep class androidx.compose.** { *; }
-dontwarn androidx.compose.**

# 도메인 모델
-keep class com.flowerdiary.domain.model.** { *; }

# ViewModel
-keep class com.flowerdiary.feature.diary.viewmodel.** { *; }

# Coroutines
-keepnames class kotlinx.coroutines.internal.MainDispatcherFactory {}
-keepnames class kotlinx.coroutines.CoroutineExceptionHandler {}
-keepclassmembernames class kotlinx.** {
    volatile <fields>;
}