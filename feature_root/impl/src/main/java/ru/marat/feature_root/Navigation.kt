package ru.marat.feature_root

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import ru.marat.navigation_api.Screen
import ru.marat.navigation_api.register

@Composable
fun Navigation(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: Screen,
    provider: NavigationApiProvider
){
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        provider.getAll().forEach {
            register(
                featureApi = it,
                navController = navController,
                modifier = modifier
            )
        }
    }
}