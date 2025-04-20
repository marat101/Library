package ru.marat.feature_home.presentation

import ru.marat.core_ui.view_model.LoadingState
import ru.marat.feature_home.model.BookItem

data class HomeState(
    val books: LoadingState<List<BookItem>> = LoadingState.Loading,
    val selectedBook: Long? = null //todo delete
)
