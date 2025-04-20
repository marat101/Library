package ru.marat.feature_book

import ru.marat.core_ui.view_model.LoadingState
import ru.marat.feature_book.model.BookFile
import ru.marat.feature_book.model.BookInfo

data class BookDetailState(
    val bookInfo: LoadingState<BookInfo> = LoadingState.Loading,
    val book: BookFile = BookFile.Undefined,
)
