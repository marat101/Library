package ru.marat.auth

import kotlinx.datetime.Instant
import ru.marat.auth.dto.TokensDto


fun Tokens.toDto() = TokensDto(
    refreshToken = refreshToken,
    accessToken = accessToken,
    expireIn = Instant.fromEpochMilliseconds(accessTokenExpiresAt)
)

fun TokensDto.toTokens() = Tokens(
    refreshToken = refreshToken,
    accessToken = accessToken,
    accessTokenExpiresAt = expireIn.toEpochMilliseconds()
)