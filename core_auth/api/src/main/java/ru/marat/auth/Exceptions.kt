package ru.marat.auth

import io.ktor.http.HttpStatusCode

class HttpException(
    val code: HttpStatusCode,
    override val message: String,
    val body: String?
) : Exception()