package ru.marat.feature_home.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import ru.marat.feature_home.presentation.HomeViewModel
import ru.marat.navigation_api.AppNavController

@Module
class HomeModule {

    @HomeScope
    @Provides
    fun provideHomeViewModelFactory(navController: AppNavController): HomeViewModel.Factory =
        HomeViewModel.Factory(navController)

}