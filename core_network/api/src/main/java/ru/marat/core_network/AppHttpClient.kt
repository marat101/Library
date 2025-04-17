package ru.marat.core_network

import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType

interface AppHttpClient {
    suspend fun request(
        url: String,
        contentType: ContentType = ContentType.Application.Json,
        builder: HttpRequestBuilder.() -> Unit
    ): HttpResponse
}

interface AppAuthHttpClient {
    suspend fun request(
        url: String,
        contentType: ContentType = ContentType.Application.Json,
        builder: HttpRequestBuilder.() -> Unit
    ): HttpResponse
}