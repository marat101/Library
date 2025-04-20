package ru.marat.feature_reader

import kotlinx.serialization.Serializable
import ru.marat.navigation_api.Screen

@Serializable
data class ReaderScreen(
    val id: Long,
    override val root: Boolean = true
) : Screen()