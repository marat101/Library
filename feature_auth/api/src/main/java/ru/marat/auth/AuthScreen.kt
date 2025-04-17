package ru.marat.auth

import kotlinx.serialization.Serializable
import ru.marat.navigation_api.Screen

@Serializable
object AuthScreen: Screen() {
    override val root: Boolean
        get() = true
}