package ru.marat.feature_book.use_cases

import ru.marat.feature_book.repository.BookFileRepository

class CheckIfFileExistsUseCase(
    private val repository: BookFileRepository
) {
    suspend operator fun invoke(id: Long) = repository.getFile(id)
}