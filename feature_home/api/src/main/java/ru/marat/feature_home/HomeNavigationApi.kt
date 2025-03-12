package ru.marat.feature_home

import ru.marat.navigation_api.NavigationApi

interface HomeNavigationApi: NavigationApi {

    val route: HomeScreen
}