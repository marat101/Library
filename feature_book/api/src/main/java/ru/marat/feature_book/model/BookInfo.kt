package ru.marat.feature_book.model

data class BookInfo(
    val id: Long,
    val title: String,
    val description: String,
    val imageUrl: String,
    val isFavorite: Boolean,
    val rating: Rating,
    val price: Int? = null,
)
