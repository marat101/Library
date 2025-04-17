package ru.marat.core_network

import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode
import ru.marat.auth.HttpException

suspend inline fun handleException(
    request: () -> HttpResponse
): HttpResponse {
    val response = request()
    if (response.status != HttpStatusCode.OK)
        throw HttpException(response.status, response.status.description, response.bodyAsText())

    return response
}