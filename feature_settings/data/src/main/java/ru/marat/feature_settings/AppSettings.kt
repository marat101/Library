package ru.marat.feature_settings

import kotlinx.coroutines.flow.Flow
import ru.marat.library.ui.theme.Theme

interface AppSettings {

    val themeFlow: Flow<Theme>

    suspend fun setNewTheme(theme: Theme)
}