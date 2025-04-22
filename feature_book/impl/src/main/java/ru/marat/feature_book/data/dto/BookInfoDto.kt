package ru.marat.feature_book.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class BookInfoDto(
    val id: Long,
    val displayedName: String,
    val description: String,
    val isFavorite: Boolean,
    val rating: RatingDto,
    val price: Int? = null,
)