package ru.marat.auth.domain.repository

interface AuthRepository {


    suspend fun auth(email: String, isLogin: Boolean)

    suspend fun confirmEmail(email: String, code: Int, isLogin: Boolean)
}