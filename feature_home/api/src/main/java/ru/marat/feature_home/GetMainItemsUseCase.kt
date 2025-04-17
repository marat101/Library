package ru.marat.feature_home

import ru.marat.feature_home.model.BookItem

interface GetMainItemsUseCase {
    suspend fun invoke(): List<BookItem> //todo
}