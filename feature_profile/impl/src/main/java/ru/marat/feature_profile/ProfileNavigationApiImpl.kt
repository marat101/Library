package ru.marat.feature_profile

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import ru.marat.feature_profile.presentation.ProfileScreen

class ProfileNavigationApiImpl : ProfileNavigationApi {

    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController,
        modifier: Modifier
    ) {
        navGraphBuilder.composable<ProfileScreen> {
            ProfileScreen(
                modifier = modifier,
                navController = navController
            )
        }
    }
}