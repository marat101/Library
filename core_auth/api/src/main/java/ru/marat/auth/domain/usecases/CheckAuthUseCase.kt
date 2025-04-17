package ru.marat.auth.domain.usecases

import ru.marat.auth.Tokens
import ru.marat.auth.domain.repository.TokenRepository

class CheckAuthUseCase(
    private val tokenRepository: TokenRepository,
) {
    suspend fun execute(tokens: Tokens) {
        tokenRepository.saveTokens(tokens)
    }
}