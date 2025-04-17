package ru.marat.feature_settings.presentation.settings

import androidx.compose.runtime.Immutable
import ru.marat.library.ui.theme.Theme

@Immutable
data class SettingsState(
    val theme: Theme = Theme.LIGHT,
    val confirmExitDialog: Boolean = false
)
