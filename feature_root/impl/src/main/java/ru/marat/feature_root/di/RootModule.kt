package ru.marat.feature_root.di

import dagger.Module
import dagger.Provides
import ru.marat.feature_root.AppNavigationController
import ru.marat.navigation_api.AppNavController
import javax.inject.Singleton

@Module
class RootModule {

    @Provides
    @Singleton
    fun provideNavController(): AppNavController = AppNavigationController()
}