package ru.marat.feature_profile.presentation.components.profile_buttons

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Immutable

@Immutable
data class ProfileButtonData(
    val text: String,
    @DrawableRes
    val icon: Int,
    val action: () -> Unit
)