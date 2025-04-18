package ru.marat.feature_book.model

import java.time.Instant

data class Review(
    val name: String,
    val text: String,
    val grade: Int,
    val date: Instant,
    val imageUrl: String? = null,
)
