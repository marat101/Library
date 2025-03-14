package ru.marat.library

import ru.marat.feature_home.HomeNavigationApiImpl
import ru.marat.feature_profile.ProfileNavigationApiImpl
import ru.marat.feature_profile.SearchNavigationImpl
import ru.marat.feature_root.NavigationApiProvider
import ru.marat.navigation_api.NavigationApi

class NavigationApiProviderImpl: NavigationApiProvider {
    override fun getAll(): Array<NavigationApi> =
        arrayOf(
            HomeNavigationApiImpl() ,
            ProfileNavigationApiImpl(),
            SearchNavigationImpl()
        )
}