package ru.marat.feature_root.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import ru.marat.core_ui.view_model.BaseViewModel
import ru.marat.feature_root.AppNavigationController
import ru.marat.feature_root.ui.bottom_navigation.NavigationButton
import ru.marat.feature_settings.AppSettings
import ru.marat.navigation_api.AppNavController
import javax.inject.Inject


class RootViewModel(
    private val navigation: AppNavController,
    appSettings: AppSettings
) : BaseViewModel() {

    private val _state = MutableStateFlow(RootState())
    val state = _state.combine(appSettings.themeFlow) { state, theme ->
        state.copy(theme = theme)
    }.stateIn(
        scope = viewModelScope,
        started = WhileSubscribed(3000),
        initialValue = RootState()
    )

    fun initNavController(navController: NavController) {
        (navigation as AppNavigationController).init(navController)
    }

    fun bottomNavigation(graphId: Int, button: NavigationButton) {
        navigation.navigate(button.route) {
            launchSingleTop = true
            popUpTo(graphId) {
                saveState = true
            }
            restoreState = true
        }
        _state.update { it.copy(activeButton = button) }
    }

    @Suppress("UNCHECKED_CAST")
    class Factory @Inject constructor(
        private val appNavigation: AppNavController,
        private val appSettings: AppSettings
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(RootViewModel::class.java))
                return RootViewModel(appNavigation, appSettings) as T
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}