package ru.marat.core_ui.components.common

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.systemBars
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max

@Composable
fun calculateHorizontalPadding(defaultPadding: Dp = 16.dp): PaddingValues {
    val layoutDirection = LocalLayoutDirection.current
    val horizontalInsets = WindowInsets.safeDrawing.asPaddingValues()
    return PaddingValues(
        start = max(horizontalInsets.calculateLeftPadding(layoutDirection), defaultPadding),
        end = max(horizontalInsets.calculateRightPadding(layoutDirection), defaultPadding)
    )
}