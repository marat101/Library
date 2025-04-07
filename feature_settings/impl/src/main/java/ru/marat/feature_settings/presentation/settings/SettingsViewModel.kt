package ru.marat.feature_settings.presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.WhileSubscribed
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ru.marat.core_ui.view_model.BaseViewModel
import ru.marat.feature_settings.AppSettings
import ru.marat.library.ui.theme.Theme
import ru.marat.navigation_api.AppNavController
import javax.inject.Inject

class SettingsViewModel(
    private val navigation: AppNavController,
    private val settings: AppSettings
) : BaseViewModel() {

    private val _state = MutableStateFlow(SettingsState())
    val state = _state.combine(settings.themeFlow) { state, theme ->
        state.copy(theme)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(3000),
        initialValue = SettingsState()
    )

    fun onThemeChanged(newTheme: Theme) {
        viewModelScope.launch(Dispatchers.IO) {
            settings.setNewTheme(newTheme)
        }
    }

    fun navigateUp() {
        navigation.popBackStack()
    }

    @Suppress("UNCHECKED_CAST")
    class Factory @Inject constructor(
        private val appNavigation: AppNavController,
        private val settings: AppSettings
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(SettingsViewModel::class.java))
                return SettingsViewModel(appNavigation, settings) as T
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}