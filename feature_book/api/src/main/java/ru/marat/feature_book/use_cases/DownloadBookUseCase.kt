package ru.marat.feature_book.use_cases

import ru.marat.feature_book.repository.BookFileRepository

class DownloadBookUseCase(
    private val repository: BookFileRepository
) {
    suspend operator fun invoke(id: Long) = repository.startDownloadFile(id)
}