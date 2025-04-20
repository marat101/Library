package ru.marat.feature_book.use_cases

import ru.marat.feature_book.repository.BookFileRepository

class CancelDownloadBookUseCase(
    private val repository: BookFileRepository
) {
    suspend operator fun invoke(id: Long) = repository.cancelDownload(id)
}