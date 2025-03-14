package ru.marat.feature_root

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import ru.marat.navigation_api.NavigationApi
import ru.marat.navigation_api.Screen
import ru.marat.navigation_api.register

@Composable
fun RootScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: Screen,
    apis: Array<NavigationApi>
){

    NavHost(
        modifier = Modifier.fillMaxSize(),
        navController = navController,
        startDestination = startDestination
    ) {
        apis.forEach {
            register(
                featureApi = it,
                navController = navController,
                modifier = modifier
            )
        }
    }
}