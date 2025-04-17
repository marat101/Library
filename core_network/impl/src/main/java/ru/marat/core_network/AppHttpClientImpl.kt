package ru.marat.core_network

import io.ktor.client.HttpClient
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.header
import io.ktor.client.request.request
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType
import ru.marat.auth.domain.repository.TokenRepository

class AppHttpClientImpl(
    private val client: HttpClient
) : AppHttpClient {
    override suspend fun request(
        url: String,
        contentType: ContentType,
        builder: HttpRequestBuilder.() -> Unit
    ): HttpResponse {
        return handleException {
            client.request(url) {
                contentType(contentType)
                builder()
            }
        }
    }
}

class AppAuthHttpClientImpl(
    private val client: HttpClient,
    private val tokenRepository: TokenRepository
) : AppAuthHttpClient {
    override suspend fun request(
        url: String,
        contentType: ContentType,
        builder: HttpRequestBuilder.() -> Unit
    ): HttpResponse {
        return handleException {
            client.request(url) {
                contentType(contentType)
                builder()
                header("Authorization", "Bearer ${tokenRepository.getAccessToken()}")
            }
        }
    }
}