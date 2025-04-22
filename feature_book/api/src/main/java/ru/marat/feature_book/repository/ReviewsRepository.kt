package ru.marat.feature_book.repository

import ru.marat.feature_book.model.Review

interface ReviewsRepository {

    suspend fun fetchReviews(bookId: String, offset: Int, limit: Int): List<Review>
}