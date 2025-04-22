package ru.marat.feature_book.di

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.marat.core_di.InjectUtils
import ru.marat.feature_book.BookDetailsViewModel
import javax.inject.Inject


class BookContainer {

    @Inject
    lateinit var viewModelFactory: BookDetailsViewModel.Factory
}

@Composable
fun bookViewModel(id: Long): BookDetailsViewModel {
    val container = remember {
        BookContainer().apply {
            DaggerBookComponent
                .factory()
                .create(
                    id,
                    InjectUtils.appDependencies<BookDependencies>()
                ).inject(this)
        }
    }

    return viewModel<BookDetailsViewModel>(
        factory = BookDetailsViewModel.create(
            id,
            container.viewModelFactory
        )
    )
}