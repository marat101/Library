package ru.marat.feature_home.network

import io.ktor.client.call.body
import io.ktor.client.request.parameter
import io.ktor.http.HttpMethod
import io.ktor.http.parametersOf
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonArray
import ru.marat.core_network.AppAuthHttpClient

const val BASE_URL = "http://193.188.20.158:8989" //todo

class MainApi( //todo потом переделать
    private val client: AppAuthHttpClient
) {
    suspend fun getMain(): Array<BookResponse> {
        val response = client.request(
            "$BASE_URL/books"
        ) {
            parameter("offset", 0)
            parameter("limit", 20)
        }
        return response.body()
    }

    suspend fun addToFavorites(id: Long) {
        client.request(
            "$BASE_URL/favorite?bookId=$id"
        ){
            method = HttpMethod.Post
        }
    }
    suspend fun removeFromFavorites(id: Long) {
        client.request(
            "$BASE_URL/favorite?bookId=$id"
        ){
            method = HttpMethod.Delete
        }
    }
}

@Serializable
data class BookResponse(
    val displayedName: String,
    val id: Long,
    val isFavorite: Boolean
)