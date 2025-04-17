package ru.marat.auth.dto

import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
data class TokensDto(
    val refreshToken: String,
    val accessToken: String,
    val expireIn: Instant
)