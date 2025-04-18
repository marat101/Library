package ru.marat.auth.local

import android.annotation.SuppressLint
import android.content.Context
import kotlinx.serialization.json.Json
import ru.marat.auth.dto.TokensDto

@SuppressLint("ApplySharedPref", "UseKtx")
class TokenStorage(
    context: Context
) {

    companion object {
        private const val TOKEN_KEY = "tokens_key"
    }
    private var inMemoryCache: TokensDto? = null

    private val sharedPrefs = context.getSharedPreferences("tokens", Context.MODE_PRIVATE)

    fun getTokens(): TokensDto? {
        if (inMemoryCache != null) return inMemoryCache
        val str = sharedPrefs.getString(TOKEN_KEY, null) ?: return null
        return Json.decodeFromString<TokensDto>(str)
    }

    fun saveTokens(tokens: TokensDto) {
        val str = Json.encodeToString(TokensDto.serializer(), tokens)
        sharedPrefs.edit().putString(TOKEN_KEY, str).commit()
        inMemoryCache = tokens
    }

    fun clearData() {
        inMemoryCache = null
        sharedPrefs.edit().clear().commit()
    }
}