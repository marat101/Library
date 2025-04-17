package ru.marat.auth

import io.ktor.http.HttpStatusCode
import kotlinx.datetime.Clock.System
import ru.marat.auth.domain.repository.TokenRepository
import ru.marat.auth.local.TokenStorage
import ru.marat.auth.network.TokenApi

class TokenRepositoryImpl(
    private val api: TokenApi,
    private val storage: TokenStorage
) : TokenRepository {

    override suspend fun getAccessToken(): String {
        val savedTokens = storage.getTokens()
        if (savedTokens != null && savedTokens.expireIn < System.now()) return savedTokens.accessToken

        val tokens = api.getTokens(
            savedTokens?.refreshToken ?: throw HttpException(
                HttpStatusCode.Unauthorized,
                "No refresh token",
                null
            )
        )
        storage.saveTokens(tokens)
        return tokens.accessToken
    }

    override suspend fun saveTokens(token: Tokens) {
        storage.saveTokens(token.toDto())
    }

    override suspend fun clearData() {
        storage.clearData()
    }

    override suspend fun isAuthorized(): Boolean {
        return storage.getTokens() != null
    }

}