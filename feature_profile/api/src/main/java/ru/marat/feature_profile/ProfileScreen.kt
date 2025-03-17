package ru.marat.feature_profile

import kotlinx.serialization.Serializable
import ru.marat.navigation_api.Screen

@Serializable
data object ProfileScreen : Screen() {
    override val root: Boolean = false
}