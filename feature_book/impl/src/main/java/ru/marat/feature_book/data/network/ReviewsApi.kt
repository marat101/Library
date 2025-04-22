package ru.marat.feature_book.data.network

import io.ktor.client.call.body
import io.ktor.client.request.parameter
import io.ktor.http.HttpMethod
import ru.marat.core_network.AppAuthHttpClient
import ru.marat.feature_book.data.dto.ReviewsDto
import ru.marat.feature_book.impl.BuildConfig

class ReviewsApi(
    private val client: AppAuthHttpClient
) {
    suspend fun fetchReviews(
        bookId: String,
        offset: Int,
        limit: Int
    ): List<ReviewsDto> {
        val response = client.request(
            url = "${BuildConfig.API_URL}/review?bookId=$bookId&offset=$offset&limit=$limit"
        ) {
            method = HttpMethod.Get
        }

        return response.body()
    }

    suspend fun sendReview(
        bookId: String,
        comment: String? = null,
        rating: Int
    ): List<ReviewsDto> {
        val response = client.request(
            url = "${BuildConfig.API_URL}/review?bookId=$bookId&rating=$rating"
        ) {
            if (comment != null) parameter("comment", comment)
            method = HttpMethod.Get
        }

        return response.body()
    }
}