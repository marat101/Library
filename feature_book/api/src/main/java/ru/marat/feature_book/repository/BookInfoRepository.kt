package ru.marat.feature_book.repository

import ru.marat.feature_book.model.BookInfo

interface BookInfoRepository {

    suspend fun fetchBookInfo(id: Long): BookInfo
}