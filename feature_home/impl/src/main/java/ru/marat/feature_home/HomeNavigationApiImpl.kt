package ru.marat.feature_home

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import ru.marat.feature_home.presentation.HomeScreen
import ru.marat.navigation_api.Screen

class HomeNavigationApiImpl: HomeNavigationApi {

    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController,
        modifier: Modifier
    ) {
        navGraphBuilder.composable<HomeScreen> {
            HomeScreen(
                modifier = modifier,
            )
        }
    }
}