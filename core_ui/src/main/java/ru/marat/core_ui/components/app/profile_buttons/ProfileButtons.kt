package ru.marat.core_ui.components.app.profile_buttons

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

@Immutable
data class ProfileButtonData(
    val text: String,
    @DrawableRes
    val icon: Int? = null,
    val arrow: Boolean = false,
    val textColor: Color = Color.Unspecified,
    val action: () -> Unit
)