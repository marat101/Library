package ru.marat.feature_home.di

import ru.marat.navigation_api.AppNavController

interface HomeDependencies {
    fun provideAppNavigation(): AppNavController
}