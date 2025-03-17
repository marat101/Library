package ru.marat.navigation_api

import androidx.annotation.MainThread
import androidx.navigation.NavOptionsBuilder

@MainThread
interface AppNavController {

    fun navigate(route: Screen, options: (NavOptionsBuilder.() -> Unit)? = null)

    fun popBackStack()
}