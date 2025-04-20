package ru.marat.feature_book.data.dto

import ru.marat.feature_book.impl.BuildConfig
import ru.marat.feature_book.model.BookInfo
import ru.marat.feature_book.model.Rating

fun BookInfo.toDto() = BookInfoDto(
    id = id,
    displayedName = title,
    description = description,
    isFavorite = isFavorite,
    rating = rating.toDto()
)

fun Rating.toDto() = RatingDto(
    star1 = star1,
    star2 = star2,
    star3 = star3,
    star4 = star4,
    star5 = star5
)

fun BookInfoDto.toModel() = BookInfo(
    id = id,
    title = displayedName,
    description = description,
    isFavorite = isFavorite,
    imageUrl = "${BuildConfig.API_URL}/preview?id=$id&isLowQuality=false",
    rating = rating.toModel()
)

fun RatingDto.toModel() = Rating(
    star1 = star1,
    star2 = star2,
    star3 = star3,
    star4 = star4,
    star5 = star5
)