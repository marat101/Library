package ru.marat.core_data

interface AppPreferences {

    operator fun get(key: String): String?

    operator fun set(key: String, value: Any?)
}