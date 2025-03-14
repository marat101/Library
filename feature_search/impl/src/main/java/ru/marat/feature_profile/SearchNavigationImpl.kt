package ru.marat.feature_profile

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable

class SearchNavigationImpl: SearchNavigationApi {
    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController,
        modifier: Modifier
    ) {
        navGraphBuilder.composable<SearchScreen> {
            SearchScreen(
                modifier = modifier,
            )
        }
    }
}