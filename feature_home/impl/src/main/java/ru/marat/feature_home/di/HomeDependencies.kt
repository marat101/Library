package ru.marat.feature_home.di

import ru.marat.core_network.AppAuthHttpClient
import ru.marat.navigation_api.AppNavController

interface HomeDependencies {
    fun provideAppNavigation(): AppNavController
    fun provideClient(): AppAuthHttpClient
}