package ru.marat.feature_profile

import kotlinx.serialization.Serializable
import ru.marat.navigation_api.Screen

@Serializable
data class SearchScreen(override val root: Boolean = true): Screen()