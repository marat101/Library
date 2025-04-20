package ru.marat.feature_book.use_cases

import ru.marat.feature_book.model.BookInfo
import ru.marat.feature_book.repository.BookInfoRepository

class FetchBookInfoUseCase(
    private val repository: BookInfoRepository
) {
    suspend operator fun invoke(id: Long): BookInfo {
        return repository.fetchBookInfo(id)
    }
}