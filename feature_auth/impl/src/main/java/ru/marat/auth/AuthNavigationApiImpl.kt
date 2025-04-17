package ru.marat.auth

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import ru.marat.auth.di.authViewModel
import ru.marat.feature_auth.AuthRoot

class AuthNavigationApiImpl: AuthNavigationApi {
    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController,
        modifier: Modifier
    ) {
        navGraphBuilder.composable<AuthScreen> {
            AuthRoot(
                viewModel = authViewModel()
            )
        }
    }
}