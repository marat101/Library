package ru.marat.auth.domain.use_cases

import ru.marat.auth.domain.repository.AuthRepository
import javax.inject.Inject

class ConfirmEmailUseCaseImpl @Inject constructor(
    private val authRepository: AuthRepository
) : ConfirmEmailUseCase {

    override suspend fun invoke(isLogin: Boolean, email: String, code: String) {
        authRepository.confirmEmail(email.trim(), code.toInt(), isLogin)
    }
}