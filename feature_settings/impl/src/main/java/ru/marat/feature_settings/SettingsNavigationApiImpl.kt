package ru.marat.feature_settings

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import ru.marat.feature_settings.presentation.about.AboutAppScreen
import ru.marat.feature_settings.presentation.feedback.FeedbackScreen
import ru.marat.feature_settings.presentation.settings.SettingsScreen

class SettingsNavigationApiImpl: SettingsNavigationApi {
    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController,
        modifier: Modifier
    ) {
        navGraphBuilder.composable<SettingsScreens.Settings> {
            SettingsScreen(
                modifier = modifier,
                navController = navController
            )
        }
        navGraphBuilder.composable<SettingsScreens.Feedback> {
            FeedbackScreen(
                modifier = modifier
            )
        }
        navGraphBuilder.composable<SettingsScreens.About> {
            AboutAppScreen(
                modifier = modifier
            )
        }
    }
}