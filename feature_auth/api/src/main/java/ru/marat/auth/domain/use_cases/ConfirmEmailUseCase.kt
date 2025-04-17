package ru.marat.auth.domain.use_cases

interface ConfirmEmailUseCase {

    suspend fun invoke(isLogin: Boolean, email: String, code: String)
}