package ru.marat.feature_profile.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.marat.core_ui.view_model.BaseViewModel
import ru.marat.feature_settings.SettingsScreens
import ru.marat.navigation_api.AppNavController
import javax.inject.Inject

class ProfileViewModel(
    private val navController: AppNavController
): BaseViewModel() {

    fun onSettingsClick() {
        navController.navigate(SettingsScreens.Settings)
    }

    @Suppress("UNCHECKED_CAST")
    class Factory(
        private val appNavigation: AppNavController,
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ProfileViewModel::class.java))
                return ProfileViewModel(appNavigation) as T
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}