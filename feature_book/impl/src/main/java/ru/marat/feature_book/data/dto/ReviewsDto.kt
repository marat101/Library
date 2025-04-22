package ru.marat.feature_book.data.dto

import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
data class ReviewsDto(
    val reviewOwnerName: String,
    val reviewOwner: String,
    val rating: Int,
    val date: Instant,
    val comment: String? = null,
)