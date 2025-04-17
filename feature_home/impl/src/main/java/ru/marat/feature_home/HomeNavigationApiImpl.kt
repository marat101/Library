package ru.marat.feature_home

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import ru.marat.feature_home.presentation.HomeScreen

class HomeNavigationApiImpl : HomeNavigationApi {

    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController,
        modifier: Modifier
    ) {
        navGraphBuilder.composable<HomeScreen> {
            HomeScreen(
                modifier = modifier
            )
        }
    }
}