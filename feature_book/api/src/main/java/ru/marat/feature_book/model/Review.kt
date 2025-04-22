package ru.marat.feature_book.model

import kotlinx.datetime.Instant


data class Review(
    val name: String,
    val text: String? = null,
    val rating: Int,
    val date: Instant,
    val avatarUrl: String,
)
