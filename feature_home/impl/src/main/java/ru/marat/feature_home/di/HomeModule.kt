package ru.marat.feature_home.di

import dagger.Module
import dagger.Provides
import ru.marat.core_network.AppAuthHttpClient
import ru.marat.feature_home.network.MainApi
import ru.marat.feature_home.network.MainRepository
import ru.marat.feature_home.presentation.HomeViewModel
import ru.marat.navigation_api.AppNavController

@Module
class HomeModule {

    @HomeScope
    @Provides
    fun provideHomeViewModelFactory(navController: AppNavController, client: AppAuthHttpClient): HomeViewModel.Factory =
        HomeViewModel.Factory(navController, MainRepository(api = MainApi(client))) //todo

}