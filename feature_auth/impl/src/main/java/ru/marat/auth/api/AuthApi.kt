package ru.marat.auth.api

import io.ktor.client.call.body
import io.ktor.client.request.setBody
import io.ktor.http.HttpMethod
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable
import ru.marat.core_auth.api.BuildConfig
import ru.marat.core_network.AppHttpClient

class AuthApiImpl(
    private val client: AppHttpClient,
) : AuthApi {

    override suspend fun login(email: String) {
        client.request(BuildConfig.API_URL + "/api/login") {
            method = HttpMethod.Post
            setBody(AuthRequestBody(email))
        }
    }

    override suspend fun loginConfirm(email: String, code: Int): AuthResponse {
        val response = client.request(BuildConfig.API_URL + "/api/login/confirm") {
            method = HttpMethod.Post
            setBody(AuthRequestBody(email, code))
        }
        return response.body()
    }

    override suspend fun register(email: String) {
        client.request(BuildConfig.API_URL + "/api/auth") {
            method = HttpMethod.Post
            setBody(AuthRequestBody(email))
        }
    }

    override suspend fun registerConfirm(email: String, code: Int): AuthResponse {
        val response = client.request(BuildConfig.API_URL + "/api/auth/confirm") {
            method = HttpMethod.Post
            setBody(AuthRequestBody(email, code))
        }
        return response.body()
    }
}

interface AuthApi {
    suspend fun login(email: String)
    suspend fun loginConfirm(email: String, code: Int): AuthResponse
    suspend fun register(email: String)
    suspend fun registerConfirm(email: String, code: Int): AuthResponse
}

@Serializable
data class AuthRequestBody(
    val email: String,
    val code: Int? = null
)

@Serializable
data class AuthResponse(
    val accessToken: String,
    val refreshToken: String,
    val expireIn: Instant
)