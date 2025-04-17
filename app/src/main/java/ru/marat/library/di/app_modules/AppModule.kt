package ru.marat.library.di.app_modules

import dagger.Module
import dagger.Provides
import ru.marat.feature_root.NavigationApiProvider
import ru.marat.feature_root.di.RootModule
import ru.marat.feature_settings.di.SettingsModule
import ru.marat.library.NavigationApiProviderImpl

@Module(
    includes = [
        RootModule::class,
        SettingsModule::class,
        NetworkModule::class
    ]
)
class AppModule {

    @Provides
    fun provideFeatures(): NavigationApiProvider = NavigationApiProviderImpl()
}