package ru.marat.feature_settings.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import ru.marat.feature_settings.AppSettings
import ru.marat.feature_settings.data.AppSettingsImpl
import ru.marat.feature_settings.presentation.settings.SettingsViewModel
import ru.marat.navigation_api.AppNavController
import javax.inject.Singleton

@Module
interface SettingsModule {
    @Binds
    @Singleton
    fun bindAppSettings(settings: AppSettingsImpl): AppSettings
}

@Module
class SettingsViewModelModule {
    @Provides
    fun provideSettingsViewModelFactory(
        settings: AppSettings,
        navigation: AppNavController
    ): SettingsViewModel.Factory {
        return SettingsViewModel.Factory(navigation, settings)
    }
}