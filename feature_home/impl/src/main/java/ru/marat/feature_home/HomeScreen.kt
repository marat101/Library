package ru.marat.feature_home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import kotlinx.coroutines.FlowPreview
import ru.marat.feature_profile.SearchScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .then(modifier)
    ) {
        TopAppBar(
            modifier = Modifier.align(Alignment.TopCenter),
            title = {
                Text("Главная")
            },
            actions = {
                Button(
                    onClick = {
                        navController.navigate(SearchScreen) {
                            launchSingleTop = true
                        }
                    }
                ) {
                    Text("Search")
                }
            }
        )
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = "HomeScreen"
        )
    }
}