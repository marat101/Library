package ru.marat.core_network

import io.ktor.client.HttpClient
import io.ktor.client.plugins.timeout
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.header
import io.ktor.client.request.prepareGet
import io.ktor.client.request.request
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsChannel
import io.ktor.http.ContentType
import io.ktor.http.contentLength
import io.ktor.http.contentType
import io.ktor.util.cio.writeChannel
import io.ktor.utils.io.core.isEmpty
import io.ktor.utils.io.core.readBytes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import ru.marat.auth.domain.repository.TokenRepository
import java.io.File

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

    override suspend fun downloadFile(
        url: String,
        onCreateFile: (totalFileSize: Long) -> File,
        progress: suspend (Float) -> Unit
    ) = coroutineScope {
        client.prepareGet(
            urlString = url,
            block = {
                header("Authorization", "Bearer ${tokenRepository.getAccessToken()}")
                timeout {
                    socketTimeoutMillis = 3_600_000 // 1 hour
                    connectTimeoutMillis = 3_600_000
                    requestTimeoutMillis = 3_600_000
                }
            }).execute { response ->
            val channel = response.bodyAsChannel()
            val size = response.contentLength() ?: 0L
            val toFile = onCreateFile(size)
            var downloadedBytes = 0L

            while (!channel.isClosedForRead) {
                val packet = channel.readRemaining(limit = DEFAULT_BUFFER_SIZE.toLong())
                while (!packet.isEmpty) {
                    val bytes = packet.readBytes()
                    toFile.appendBytes(bytes)
                    downloadedBytes += bytes.size
                    progress(
                        (downloadedBytes.toDouble() / size.toDouble()).toFloat().coerceIn(0f, 1f)
                    )
                }
            }
        }
    }
}