package com.flowerdiary.ui.ios.theme

import com.flowerdiary.common.constants.ColorPalette
import kotlinx.cinterop.*
import platform.CoreGraphics.CGFloat
import platform.UIKit.UIColor

/**
 * 색상 확장 함수
 * SRP: ColorPalette 상수를 UIColor로 변환만 담당
 * 하드코딩 없이 중앙 집중식 색상 관리
 */
object ColorExtensions {
    
    /**
     * ARGB Long 값을 UIColor로 변환
     */
    fun Long.toUIColor(): UIColor {
        val alpha = ((this shr 24) and 0xFF) / 255.0
        val red = ((this shr 16) and 0xFF) / 255.0
        val green = ((this shr 8) and 0xFF) / 255.0
        val blue = (this and 0xFF) / 255.0
        
        return UIColor(
            red = red.toCGFloat(),
            green = green.toCGFloat(),
            blue = blue.toCGFloat(),
            alpha = alpha.toCGFloat()
        )
    }
    
    /**
     * Double을 CGFloat으로 변환
     */
    private fun Double.toCGFloat(): CGFloat = this
}

/**
 * iOS 테마 색상 정의
 * SRP: iOS 플랫폼용 색상 시스템만 담당
 */
object IOSThemeColors {
    
    // Primary Colors
    val primaryLight = ColorPalette.Primary.Light.toUIColor()
    val primaryDefault = ColorPalette.Primary.Default.toUIColor()
    val primaryDark = ColorPalette.Primary.Dark.toUIColor()
    
    // Secondary Colors
    val secondaryLight = ColorPalette.Secondary.Light.toUIColor()
    val secondaryDefault = ColorPalette.Secondary.Default.toUIColor()
    val secondaryDark = ColorPalette.Secondary.Dark.toUIColor()
    
    // Background Colors
    val backgroundPrimary = ColorPalette.Background.Primary.toUIColor()
    val backgroundSecondary = ColorPalette.Background.Secondary.toUIColor()
    val backgroundTertiary = ColorPalette.Background.Tertiary.toUIColor()
    val backgroundCard = ColorPalette.Background.Card.toUIColor()
    val backgroundOverlay = ColorPalette.Background.Overlay.toUIColor()
    
    // Text Colors
    val textPrimary = ColorPalette.Text.Primary.toUIColor()
    val textSecondary = ColorPalette.Text.Secondary.toUIColor()
    val textTertiary = ColorPalette.Text.Tertiary.toUIColor()
    val textOnPrimary = ColorPalette.Text.OnPrimary.toUIColor()
    val textOnSecondary = ColorPalette.Text.OnSecondary.toUIColor()
    val textDisabled = ColorPalette.Text.Disabled.toUIColor()
    
    // Seasonal Colors
    val seasonalSpring = ColorPalette.Seasonal.Spring.toUIColor()
    val seasonalSummer = ColorPalette.Seasonal.Summer.toUIColor()
    val seasonalAutumn = ColorPalette.Seasonal.Autumn.toUIColor()
    val seasonalWinter = ColorPalette.Seasonal.Winter.toUIColor()
    
    // Status Colors
    val statusSuccess = ColorPalette.Status.Success.toUIColor()
    val statusWarning = ColorPalette.Status.Warning.toUIColor()
    val statusError = ColorPalette.Status.Error.toUIColor()
    val statusInfo = ColorPalette.Status.Info.toUIColor()
    
    // Utility Colors
    val utilityDivider = ColorPalette.Utility.Divider.toUIColor()
    val utilityShadow = ColorPalette.Utility.Shadow.toUIColor()
    val utilityRipple = ColorPalette.Utility.Ripple.toUIColor()
    val utilityLocked = ColorPalette.Utility.Locked.toUIColor()
    
    // Birth Flower Background Colors
    val flowerBackgroundJanuary = ColorPalette.FlowerBackground.January.toUIColor()
    val flowerBackgroundFebruary = ColorPalette.FlowerBackground.February.toUIColor()
    val flowerBackgroundMarch = ColorPalette.FlowerBackground.March.toUIColor()
    val flowerBackgroundApril = ColorPalette.FlowerBackground.April.toUIColor()
    val flowerBackgroundMay = ColorPalette.FlowerBackground.May.toUIColor()
    val flowerBackgroundJune = ColorPalette.FlowerBackground.June.toUIColor()
    val flowerBackgroundJuly = ColorPalette.FlowerBackground.July.toUIColor()
    val flowerBackgroundAugust = ColorPalette.FlowerBackground.August.toUIColor()
    val flowerBackgroundSeptember = ColorPalette.FlowerBackground.September.toUIColor()
    val flowerBackgroundOctober = ColorPalette.FlowerBackground.October.toUIColor()
    val flowerBackgroundNovember = ColorPalette.FlowerBackground.November.toUIColor()
    val flowerBackgroundDecember = ColorPalette.FlowerBackground.December.toUIColor()
    
    /**
     * 월별 탄생화 배경색 반환
     */
    fun getFlowerBackgroundColor(month: Int): UIColor {
        return when (month) {
            1 -> flowerBackgroundJanuary
            2 -> flowerBackgroundFebruary
            3 -> flowerBackgroundMarch
            4 -> flowerBackgroundApril
            5 -> flowerBackgroundMay
            6 -> flowerBackgroundJune
            7 -> flowerBackgroundJuly
            8 -> flowerBackgroundAugust
            9 -> flowerBackgroundSeptember
            10 -> flowerBackgroundOctober
            11 -> flowerBackgroundNovember
            12 -> flowerBackgroundDecember
            else -> backgroundCard
        }
    }
}