package ru.marat.auth.local

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

    fun getTokens(): TokensDto? {
        if (inMemoryCache != null) return inMemoryCache
        val str = appPreferences[TOKEN_KEY] ?: return null
        return Json.decodeFromString<TokensDto>(str)
    }

    fun saveTokens(tokens: TokensDto) {
        val str = Json.encodeToString(TokensDto.serializer(), tokens)
        appPreferences[TOKEN_KEY] = str
        inMemoryCache = tokens
    }

    fun clearData() {
        inMemoryCache = null
        appPreferences[TOKEN_KEY] = null
    }
}