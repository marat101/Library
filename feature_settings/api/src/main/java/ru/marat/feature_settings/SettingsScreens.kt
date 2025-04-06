package ru.marat.feature_settings

import kotlinx.serialization.Serializable
import ru.marat.navigation_api.Screen

@Serializable
sealed class SettingsScreens: Screen() {
    @Serializable
    data object Settings : SettingsScreens()
    @Serializable
    data object About : SettingsScreens()
    @Serializable
    data object Feedback : SettingsScreens()

    override val root: Boolean
        get() = false
}