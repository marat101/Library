package ru.marat.core_ui.components.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.ui.Modifier

fun Modifier.clickWithoutIndication(enabled: Boolean = true, onClick: () -> Unit): Modifier =
    clickable(
        enabled = enabled,
        interactionSource = MutableInteractionSource(),
        indication = null
    ) {
        onClick()
    }