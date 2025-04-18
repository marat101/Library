package ru.marat.feature_book.model

data class BookInfo(
    val id: Long,
    val title: String,
    val description: String,
    val imageUrl: String,
    val rating: Float //todo
)
