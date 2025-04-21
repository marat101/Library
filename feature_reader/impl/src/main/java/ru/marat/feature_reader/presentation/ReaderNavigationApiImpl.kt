package ru.marat.feature_reader.presentation

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import ru.marat.feature_reader.ReaderNavigationApi
import ru.marat.feature_reader.ReaderScreen
import ru.marat.feature_reader.ReaderScreenUi
import ru.marat.feature_reader.di.readerViewModel


class ReaderNavigationApiImpl: ReaderNavigationApi {
    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController,
        modifier: Modifier
    ) {
        navGraphBuilder.composable<ReaderScreen>() {
            val args = it.toRoute<ReaderScreen>()
            ReaderScreenUi(
                viewModel = readerViewModel(args.id)
            )
        }
    }
}