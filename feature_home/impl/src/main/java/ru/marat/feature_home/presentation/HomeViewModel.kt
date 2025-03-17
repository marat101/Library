package ru.marat.feature_home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import ru.marat.core_ui.view_model.BaseViewModel
import ru.marat.core_ui.view_model.LoadingState
import ru.marat.feature_profile.SearchScreen
import ru.marat.navigation_api.AppNavController

class HomeViewModel(
    private val navigation: AppNavController
) : BaseViewModel() {

    private val _state = MutableStateFlow(
        HomeState(
            loadingState = LoadingState.Loading
        )
    )
    val state: StateFlow<HomeState>
        get() = _state.asStateFlow()

    fun onSearchClick() {
        navigation.navigate(SearchScreen()) {
            launchSingleTop = true
        }
    }

    @Suppress("UNCHECKED_CAST")
    class Factory @Inject constructor(private val appNavigation: AppNavController) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(HomeViewModel::class.java))
                return HomeViewModel(appNavigation) as T
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}