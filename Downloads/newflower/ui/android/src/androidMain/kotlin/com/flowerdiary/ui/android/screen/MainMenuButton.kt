package com.flowerdiary.ui.android.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.flowerdiary.common.constants.ColorPalette

/**
 * 메인 메뉴 버튼
 * SRP: 메인 메뉴 버튼 UI만 담당
 */
@Composable
internal fun MainMenuButton(
    icon: ImageVector,
    label: String,
    backgroundColor: Color,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable { onClick() }
    ) {
        Box(
            modifier = Modifier
                .size(MainScreenConstants.MENU_BUTTON_SIZE.dp)
                .clip(CircleShape)
                .background(backgroundColor),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = Color(ColorPalette.Text.Primary.toInt()),
                modifier = Modifier.size(MainScreenConstants.MENU_ICON_SIZE.dp)
            )
        }
        
        Spacer(modifier = Modifier.height(MainScreenConstants.SMALL_SPACING.dp))
        
        Text(
            text = label,
            style = MaterialTheme.typography.labelLarge,
            color = Color(ColorPalette.Text.Primary.toInt())
        )
    }
}
