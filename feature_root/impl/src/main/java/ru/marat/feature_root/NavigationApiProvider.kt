package ru.marat.feature_root

import ru.marat.navigation_api.NavigationApi

interface NavigationApiProvider {
    fun getAll(): List<NavigationApi>
}