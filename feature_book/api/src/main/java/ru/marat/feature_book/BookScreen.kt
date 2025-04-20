package ru.marat.feature_book

import kotlinx.serialization.Serializable
import ru.marat.navigation_api.Screen

@Serializable
data class BookScreen(
    val id: Long,
    override val root: Boolean = false
): Screen()
