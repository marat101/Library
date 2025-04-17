package ru.marat.feature_settings.presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.marat.auth.AuthScreen
import ru.marat.core_ui.view_model.BaseViewModel
import ru.marat.feature_settings.AppSettings
import ru.marat.feature_settings.SettingsScreens
import ru.marat.feature_settings.use_cases.LogoutUseCase
import ru.marat.library.ui.theme.Theme
import ru.marat.navigation_api.AppNavController
import javax.inject.Inject

class SettingsViewModel(
    private val navigation: AppNavController,
    private val settings: AppSettings,
    private val logoutUseCase: LogoutUseCase
) : BaseViewModel() {

    private val _state = MutableStateFlow(SettingsState())
    val state = _state.combine(settings.themeFlow) { state, theme ->
        state.copy(theme)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(3000),
        initialValue = SettingsState()
    )

    fun onLogoutClick() {
        _state.update {
            it.copy(confirmExitDialog = true)
        }
    }

    fun onCloseConfirmDialog() {
        _state.update {
            it.copy(confirmExitDialog = false)
        }
    }

    fun onLogoutConfirmClick() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) { logoutUseCase.invoke() }
            onCloseConfirmDialog()
            navigation.navigate(AuthScreen) {
                popUpTo(0) {
                    inclusive = true
                }
            }
        }
    }

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
        private val settings: AppSettings,
        private val logoutUseCase: LogoutUseCase
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(SettingsViewModel::class.java))
                return SettingsViewModel(appNavigation, settings, logoutUseCase) as T
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}