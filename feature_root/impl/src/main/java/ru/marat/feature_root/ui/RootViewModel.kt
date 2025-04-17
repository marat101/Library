package ru.marat.feature_root.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.marat.auth.AuthScreen
import ru.marat.auth.domain.repository.TokenRepository
import ru.marat.core_ui.view_model.BaseViewModel
import ru.marat.feature_home.HomeScreen
import ru.marat.feature_root.AppNavigationController
import ru.marat.feature_root.ui.bottom_navigation.NavigationButton
import ru.marat.feature_settings.AppSettings
import ru.marat.navigation_api.AppNavController
import ru.marat.navigation_api.Screen
import javax.inject.Inject


class RootViewModel(
    private val navigation: AppNavController,
    private val tokenRepository: TokenRepository, //fixme
    appSettings: AppSettings
) : BaseViewModel() {

    private val _state = MutableStateFlow(RootState())
    val state = _state.combine(appSettings.themeFlow) { state, theme ->
        state.copy(theme = theme,)
    }.combine(flow {
        emit(tokenRepository.isAuthorized())
    }) {  state, isAuthorized ->
        state.copy(
            startDestination = if (isAuthorized) HomeScreen else AuthScreen
        )
    }.flowOn(Dispatchers.IO).stateIn(
        scope = viewModelScope,
        started = WhileSubscribed(3000),
        initialValue = RootState()
    )
    val isReadyToDraw: Boolean
        get() = state.value.theme != null

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
        private val tokenRepository: TokenRepository,
        private val appSettings: AppSettings
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(RootViewModel::class.java))
                return RootViewModel(appNavigation, tokenRepository, appSettings) as T
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}