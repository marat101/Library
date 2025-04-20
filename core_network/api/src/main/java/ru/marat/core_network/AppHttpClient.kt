package ru.marat.core_network

import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import java.io.File

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

    /** @param progress from 0.0(0%) to 1.0(100%) */
    suspend fun downloadFile(
        url: String,
        onCreateFile: (totalFileSize: Long) -> File,
        progress: suspend (progress: Float) -> Unit
    )
}