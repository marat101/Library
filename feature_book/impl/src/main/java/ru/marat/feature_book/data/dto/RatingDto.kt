package ru.marat.feature_book.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class RatingDto(
    val star1: Int,
    val star2: Int,
    val star3: Int,
    val star4: Int,
    val star5: Int
)