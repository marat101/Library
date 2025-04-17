package ru.marat.auth.domain.repository

import ru.marat.auth.Tokens

interface TokenRepository {

    suspend fun getAccessToken(): String

    suspend fun saveTokens(token: Tokens)

    suspend fun clearData()

    suspend fun isAuthorized(): Boolean
}