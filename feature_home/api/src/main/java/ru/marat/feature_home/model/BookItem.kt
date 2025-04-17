package ru.marat.feature_home.model

data class BookItem(
    val name: String,
    val imageUrl: String,
    val isFavorite: Boolean,
    val id: Long
)