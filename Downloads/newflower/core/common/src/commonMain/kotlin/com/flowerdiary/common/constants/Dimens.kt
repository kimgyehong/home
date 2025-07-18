package com.flowerdiary.common.constants

import kotlin.jvm.JvmInline

/**
 * UI 크기 관련 상수 - 플랫폼 중립적 논리적 픽셀 단위
 * 매직넘버 제거 및 일관된 UI 스케일 보장
 */
@JvmInline
value class LogicalDp(val value: Float) {
    operator fun plus(other: LogicalDp) = LogicalDp(value + other.value)
    operator fun minus(other: LogicalDp) = LogicalDp(value - other.value)
    operator fun times(multiplier: Float) = LogicalDp(value * multiplier)
    operator fun div(divisor: Float) = LogicalDp(value / divisor)
}

object Dimens {
    // 패딩 값
    object Padding {
        val XXSmall = LogicalDp(2f)
        val XSmall = LogicalDp(4f)
        val Small = LogicalDp(8f)
        val Medium = LogicalDp(16f)
        val Large = LogicalDp(24f)
        val XLarge = LogicalDp(32f)
        val XXLarge = LogicalDp(48f)
    }
    
    // 컴포넌트 높이
    object Height {
        val Button = LogicalDp(48f)
        val TextField = LogicalDp(56f)
        val TopBar = LogicalDp(56f)
        val BottomNav = LogicalDp(56f)
        val Card = LogicalDp(120f)
        val FlowerCard = LogicalDp(150f)
        val LoadingIndicator = LogicalDp(24f)
    }
    
    // 아이콘 크기
    object Icon {
        val Small = LogicalDp(16f)
        val Medium = LogicalDp(24f)
        val Large = LogicalDp(32f)
        val XLarge = LogicalDp(48f)
        val XXLarge = LogicalDp(64f)
        val FlowerThumbnail = LogicalDp(80f)
        val FlowerDetail = LogicalDp(200f)
        val Lock = LogicalDp(24f)
    }
    
    // 코너 반경
    object Radius {
        val Small = LogicalDp(4f)
        val Medium = LogicalDp(8f)
        val Large = LogicalDp(16f)
        val Round = LogicalDp(999f)
        val Card = LogicalDp(12f)
    }
    
    // 그리드 관련
    object Grid {
        val ColumnCount = 3
        val Spacing = LogicalDp(8f)
        val ContentPadding = LogicalDp(16f)
        val CardAspectRatio = 1f
    }
    
    // 텍스트 크기 (상대값)
    object TextSize {
        const val Small = 0.875f  // 14sp on 16sp base
        const val Medium = 1.0f    // 16sp base
        const val Large = 1.125f   // 18sp on 16sp base
        const val XLarge = 1.5f    // 24sp on 16sp base
        const val XXLarge = 2.0f   // 32sp on 16sp base
    }
    
    // 투명도 값
    object Alpha {
        const val Disabled = 0.38f
        const val Medium = 0.6f
        const val High = 0.87f
        const val Opaque = 1.0f
        const val Background = 0.8f
        const val Overlay = 0.6f
        const val FlowerBackground = 0.3f
    }
}
