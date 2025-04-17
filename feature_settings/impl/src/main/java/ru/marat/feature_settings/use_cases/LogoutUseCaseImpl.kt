package ru.marat.feature_settings.use_cases

import ru.marat.auth.domain.repository.TokenRepository

class LogoutUseCaseImpl (
    private val tokenRepository: TokenRepository
) : LogoutUseCase {
    override suspend fun invoke() {
        tokenRepository.clearData()
    }
}