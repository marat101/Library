package ru.marat.auth

data class Tokens(
    val refreshToken: String,
    val accessToken: String,
    val accessTokenExpiresAt: Long
)