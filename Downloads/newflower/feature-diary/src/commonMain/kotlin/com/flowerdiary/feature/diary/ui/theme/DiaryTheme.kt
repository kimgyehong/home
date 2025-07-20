package com.flowerdiary.feature.diary.ui.theme

import com.flowerdiary.feature.diary.domain.model.DiaryStatus

data class DiaryTheme(
  val colorScheme: DiaryColorScheme = DiaryColorScheme.light(),
  val typography: DiaryTypography = DiaryTypography.default(),
  val spacing: DiarySpacing = DiarySpacing.default(),
  val shapes: DiaryShapes = DiaryShapes.default()
) {
  
  fun getStatusColor(status: DiaryStatus): String {
    return when (status) {
      DiaryStatus.Draft -> colorScheme.draft
      DiaryStatus.Published -> colorScheme.published
      DiaryStatus.Archived -> colorScheme.archived
    }
  }

  fun getContentBackgroundColor(): String {
    return colorScheme.background
  }

  fun getCardBackgroundColor(): String {
    return colorScheme.surface
  }

  fun getPrimaryTextColor(): String {
    return colorScheme.onSurface
  }

  fun getSecondaryTextColor(): String {
    return colorScheme.onSurfaceVariant
  }

  companion object {
    fun light(): DiaryTheme {
      return DiaryTheme(
        colorScheme = DiaryColorScheme.light()
      )
    }

    fun dark(): DiaryTheme {
      return DiaryTheme(
        colorScheme = DiaryColorScheme.dark()
      )
    }
  }
}

data class DiaryColorScheme(
  val primary: String,
  val onPrimary: String,
  val secondary: String,
  val onSecondary: String,
  val background: String,
  val onBackground: String,
  val surface: String,
  val onSurface: String,
  val onSurfaceVariant: String,
  val draft: String,
  val published: String,
  val archived: String,
  val error: String,
  val warning: String,
  val success: String
) {
  companion object {
    fun light(): DiaryColorScheme {
      return DiaryColorScheme(
        primary = "#6750A4",
        onPrimary = "#FFFFFF",
        secondary = "#625B71",
        onSecondary = "#FFFFFF",
        background = "#FFFBFE",
        onBackground = "#1C1B1F",
        surface = "#FFFBFE",
        onSurface = "#1C1B1F",
        onSurfaceVariant = "#49454F",
        draft = "#FFA726",
        published = "#66BB6A",
        archived = "#BDBDBD",
        error = "#F44336",
        warning = "#FF9800",
        success = "#4CAF50"
      )
    }

    fun dark(): DiaryColorScheme {
      return DiaryColorScheme(
        primary = "#D0BCFF",
        onPrimary = "#381E72",
        secondary = "#CCC2DC",
        onSecondary = "#332D41",
        background = "#1C1B1F",
        onBackground = "#E6E1E5",
        surface = "#1C1B1F",
        onSurface = "#E6E1E5",
        onSurfaceVariant = "#CAC4D0",
        draft = "#FFB74D",
        published = "#81C784",
        archived = "#9E9E9E",
        error = "#EF5350",
        warning = "#FFA726",
        success = "#66BB6A"
      )
    }
  }
}

data class DiaryTypography(
  val titleLarge: DiaryTextStyle,
  val titleMedium: DiaryTextStyle,
  val titleSmall: DiaryTextStyle,
  val bodyLarge: DiaryTextStyle,
  val bodyMedium: DiaryTextStyle,
  val bodySmall: DiaryTextStyle,
  val labelLarge: DiaryTextStyle,
  val labelMedium: DiaryTextStyle,
  val labelSmall: DiaryTextStyle
) {
  companion object {
    fun default(): DiaryTypography {
      return DiaryTypography(
        titleLarge = DiaryTextStyle(fontSize = 22f, lineHeight = 28f),
        titleMedium = DiaryTextStyle(fontSize = 16f, lineHeight = 24f),
        titleSmall = DiaryTextStyle(fontSize = 14f, lineHeight = 20f),
        bodyLarge = DiaryTextStyle(fontSize = 16f, lineHeight = 24f),
        bodyMedium = DiaryTextStyle(fontSize = 14f, lineHeight = 20f),
        bodySmall = DiaryTextStyle(fontSize = 12f, lineHeight = 16f),
        labelLarge = DiaryTextStyle(fontSize = 14f, lineHeight = 20f),
        labelMedium = DiaryTextStyle(fontSize = 12f, lineHeight = 16f),
        labelSmall = DiaryTextStyle(fontSize = 11f, lineHeight = 16f)
      )
    }
  }
}

data class DiaryTextStyle(
  val fontSize: Float,
  val lineHeight: Float,
  val fontWeight: String = "normal",
  val fontStyle: String = "normal"
)

data class DiarySpacing(
  val extraSmall: Float,
  val small: Float,
  val medium: Float,
  val large: Float,
  val extraLarge: Float
) {
  companion object {
    fun default(): DiarySpacing {
      return DiarySpacing(
        extraSmall = 4f,
        small = 8f,
        medium = 16f,
        large = 24f,
        extraLarge = 32f
      )
    }
  }
}

data class DiaryShapes(
  val small: Float,
  val medium: Float,
  val large: Float
) {
  companion object {
    fun default(): DiaryShapes {
      return DiaryShapes(
        small = 4f,
        medium = 8f,
        large = 16f
      )
    }
  }
}