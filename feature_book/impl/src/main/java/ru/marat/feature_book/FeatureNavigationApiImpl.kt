package ru.marat.feature_book

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import ru.marat.feature_book.di.bookViewModel

class BookNavigationApiImpl: BookNavigationApi {
    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController,
        modifier: Modifier
    ) {
       navGraphBuilder.composable<BookScreen> {
           val (id, _) = it.toRoute<BookScreen>()
           BookDetailScreen(
               modifier = modifier,
               viewModel = bookViewModel(id)
           )
       }
    }
}