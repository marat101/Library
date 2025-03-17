package ru.marat.feature_home

import kotlinx.serialization.Serializable
import ru.marat.navigation_api.Screen

@Serializable
data object HomeScreen : Screen() {
    override val root: Boolean = false
}
