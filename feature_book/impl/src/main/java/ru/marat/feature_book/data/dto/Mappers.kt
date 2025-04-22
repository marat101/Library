package ru.marat.feature_book.data.dto

import ru.marat.feature_book.impl.BuildConfig
import ru.marat.feature_book.model.BookInfo
import ru.marat.feature_book.model.Rating
import ru.marat.feature_book.model.Review

fun BookInfo.toDto() = BookInfoDto(
    id = id,
    displayedName = title,
    description = description,
    isFavorite = isFavorite,
    rating = rating.toDto(),
    price = price
)

fun BookInfoDto.toModel() = BookInfo(
    id = id,
    title = displayedName,
    description = description,
    isFavorite = isFavorite,
    imageUrl = "${BuildConfig.API_URL}/preview?id=$id&isLowQuality=false",
    rating = rating.toModel(),
    price = price
)

fun Rating.toDto() = RatingDto(
    star1 = star1,
    star2 = star2,
    star3 = star3,
    star4 = star4,
    star5 = star5
)


fun RatingDto.toModel() = Rating(
    star1 = star1,
    star2 = star2,
    star3 = star3,
    star4 = star4,
    star5 = star5
)

fun ReviewsDto.toModel() = Review(
    name = reviewOwnerName,
    text = comment,
    rating = rating,
    date = date,
    avatarUrl = "${BuildConfig.API_URL}/api/users/image/$reviewOwner"
)

fun Review.toDto() = ReviewsDto(
    reviewOwnerName = name,
    comment = text,
    rating = rating,
    date = date,
    reviewOwner = avatarUrl.split("/").last()
)