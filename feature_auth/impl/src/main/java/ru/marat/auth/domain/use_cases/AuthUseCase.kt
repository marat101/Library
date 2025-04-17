package ru.marat.auth.domain.use_cases

import ru.marat.auth.domain.repository.AuthRepository
import javax.inject.Inject

class AuthUseCaseImpl @Inject constructor(
    private val authRepository: AuthRepository
) : AuthUseCase {

    override suspend fun invoke(isLogin: Boolean, email: String) {
        authRepository.auth(email.trim(), isLogin)
    }
}
