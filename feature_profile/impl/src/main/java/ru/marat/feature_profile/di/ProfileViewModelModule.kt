package ru.marat.feature_profile.di

import dagger.Module
import dagger.Provides
import ru.marat.feature_profile.presentation.ProfileViewModel
import ru.marat.navigation_api.AppNavController

@Module
class ProfileViewModelModule {
    @Provides
    @ProfileScope
    fun bindProfileViewModelFactory(appNavigation: AppNavController): ProfileViewModel.Factory {
        return ProfileViewModel.Factory(appNavigation)
    }
}