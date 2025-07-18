#!/bin/bash

echo "🧹 Cleaning build caches and outputs..."

# Clean Gradle build cache
./gradlew cleanBuildCache || true

# Clean all module build directories
find . -type d -name "build" -exec rm -rf {} + 2>/dev/null || true

# Clean Gradle cache
rm -rf ~/.gradle/caches/build-cache-* 2>/dev/null || true

# Clean Android build cache
rm -rf ~/.android/build-cache 2>/dev/null || true

# Clean project
./gradlew clean || true

echo "✅ Clean completed!"
echo "🔨 Starting fresh build..."

# Build project
./gradlew build --no-build-cache --no-configuration-cache

echo "✅ Build completed!"