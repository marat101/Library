package ru.marat.navigation_api

import kotlinx.serialization.Serializable

@Serializable
abstract class Screen {
    abstract val root: Boolean
}