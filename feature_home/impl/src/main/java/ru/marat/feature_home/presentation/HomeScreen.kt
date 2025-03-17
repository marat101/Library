package ru.marat.feature_home.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import ru.marat.feature_home.di.daggerViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = daggerViewModel()
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
                        viewModel.onSearchClick()
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