package ru.marat.auth.domain.repository

import ru.marat.auth.Tokens
import ru.marat.auth.api.AuthApi

class AuthRepositoryImpl(
    private val authApi: AuthApi,
    private val tokenRepository: TokenRepository
) : AuthRepository {

    override suspend fun auth(email: String, isLogin: Boolean) {
        if (isLogin) authApi.login(email)
        else authApi.register(email)
    }

    override suspend fun confirmEmail(email: String, code: Int, isLogin: Boolean) {
        val response = if (isLogin) authApi.loginConfirm(email, code)
        else authApi.registerConfirm(email, code)

        tokenRepository.saveTokens(
            Tokens(
                refreshToken = response.refreshToken,
                accessToken = response.accessToken,
                accessTokenExpiresAt = response.expireIn.toEpochMilliseconds()
            )
        )
    }
}