package ru.marat.feature_book.data

import ru.marat.feature_book.data.dto.toModel
import ru.marat.feature_book.data.network.ReviewsApi
import ru.marat.feature_book.model.Review
import ru.marat.feature_book.repository.ReviewsRepository


class ReviewsRepositoryImpl(
    private val api: ReviewsApi
): ReviewsRepository {
    override suspend fun fetchReviews(
        bookId: String,
        offset: Int,
        limit: Int
    ): List<Review> {
        return api.fetchReviews(bookId, offset, limit).map { it.toModel() }
    }
}
