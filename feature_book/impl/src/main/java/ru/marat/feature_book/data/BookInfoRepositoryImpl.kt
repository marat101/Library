package ru.marat.feature_book.data

import ru.marat.feature_book.data.dto.toModel
import ru.marat.feature_book.data.network.BookApi
import ru.marat.feature_book.model.BookInfo
import ru.marat.feature_book.repository.BookInfoRepository

class BookInfoRepositoryImpl(
    private val bookApi: BookApi
): BookInfoRepository {
    override suspend fun fetchBookInfo(id: Long): BookInfo {
        //todo cache
        return bookApi.fetchBookInfo(id).toModel()
    }
}