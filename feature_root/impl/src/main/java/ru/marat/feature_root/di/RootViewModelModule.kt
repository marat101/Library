package ru.marat.feature_root.di

import dagger.Module
import dagger.Provides
import ru.marat.auth.domain.repository.TokenRepository
import ru.marat.feature_root.ui.RootViewModel
import ru.marat.feature_settings.AppSettings
import ru.marat.navigation_api.AppNavController


@Module
class RootViewModelModule {
    @Provides
    fun provideRootViewModelFactory(
        navigation: AppNavController,
        tokenRepository: TokenRepository,
        appSettings: AppSettings): RootViewModel.Factory {
        return RootViewModel.Factory(navigation, tokenRepository, appSettings)
    }
}