package ru.marat.feature_home.use_cases

import ru.marat.feature_home.GetMainItemsUseCase
import ru.marat.feature_home.model.BookItem

class GetMainItemsUseCaseImpl(

) : GetMainItemsUseCase {
    override suspend fun invoke(): List<BookItem> {
        return listOf()
    }
}