package ru.marat.auth.network

import io.ktor.client.call.body
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.contentType
import kotlinx.datetime.toJavaInstant
import kotlinx.datetime.toKotlinInstant
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import ru.marat.auth.dto.TokensDto
import ru.marat.core_auth.api.BuildConfig
import ru.marat.core_network.AppHttpClient
import java.time.ZoneId
import java.util.Locale

class TokenApi(
    private val client: AppHttpClient,
) {
    suspend fun getTokens(refreshToken: String): TokensDto {
        val response =  client.request(BuildConfig.API_URL + "/api/token/refresh") {
            method = HttpMethod.Post
            contentType(ContentType.Application.Json)
            setBody(JsonObject(mapOf("token" to JsonPrimitive(refreshToken))))
        }.body<TokensDto>()
        val zonedDateTime = response.expireIn.toJavaInstant().atZone(ZoneId.systemDefault())
        return response.copy(
            expireIn = zonedDateTime.toInstant().toKotlinInstant()
        )
    }
}