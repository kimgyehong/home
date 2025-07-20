plugins {
  id("com.flowerdiary.conventions")
  kotlin("plugin.serialization")
}

kotlin {
  sourceSets {
    commonMain.dependencies {
      implementation(projects.core)
      implementation(libs.kotlinx.coroutines.core)
      implementation(libs.kotlinx.collections.immutable)
      implementation(libs.kotlinx.serialization.json)
      implementation(libs.kotlinx.datetime)
    }

    commonTest.dependencies {
      implementation(libs.kotlin.test)
      implementation(libs.kotlinx.coroutines.test)
    }
  }
}