package ru.marat.auth.domain.use_cases

interface AuthUseCase {

    suspend fun invoke(isLogin: Boolean, email: String)
}
