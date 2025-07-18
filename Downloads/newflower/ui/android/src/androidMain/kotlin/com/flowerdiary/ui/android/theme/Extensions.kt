package com.flowerdiary.ui.android.theme

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.flowerdiary.common.constants.LogicalDp

/**
 * 플랫폼 중립적 LogicalDp를 Compose Dp로 변환
 * SRP: 단위 변환만 담당
 */
fun LogicalDp.toDp(): Dp = value.dp