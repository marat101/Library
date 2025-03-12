package ru.marat.feature_profile

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable

class ProfileNavigationApiImpl : ProfileNavigationApi {
    override val baseRoute = ProfileScreen

    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController,
        modifier: Modifier
    ) {
        navGraphBuilder.composable<ProfileScreen> {
            ProfileScreen(
                modifier = modifier,
            )
        }
    }
}