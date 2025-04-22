package ru.marat.auth.local

import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.serialization.json.Json
import ru.marat.auth.dto.TokensDto
import ru.marat.core_data.AppPreferences

class TokenStorage(
    private val appPreferences: AppPreferences
) {

    companion object {
        private const val TOKEN_KEY = "tokens_key"
    }
    private var inMemoryCache: TokensDto? = null

    private val mutex = Mutex()

    suspend fun getTokens(): TokensDto? = mutex.withLock {
        if (inMemoryCache != null) return inMemoryCache
        val str = appPreferences[TOKEN_KEY] ?: return null
        return Json.decodeFromString<TokensDto>(str)
    }

    suspend fun saveTokens(tokens: TokensDto) = mutex.withLock {
        val str = Json.encodeToString(TokensDto.serializer(), tokens)
        appPreferences[TOKEN_KEY] = str
        inMemoryCache = tokens
    }

    suspend fun clearData() = mutex.withLock {
        inMemoryCache = null
        appPreferences[TOKEN_KEY] = null
    }
}