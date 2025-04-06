package ru.marat.feature_home.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import ru.marat.core_ui.components.app.GridBookItem
import ru.marat.core_ui.components.common.rememberRatingState
import ru.marat.feature_home.api.R
import ru.marat.feature_home.di.daggerViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = daggerViewModel()
) {
    val ratingState = rememberRatingState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .then(modifier)
    ) {
        TopAppBar(
            modifier = Modifier.fillMaxWidth(),
            title = {
                Text(
                    text = stringResource(R.string.top_app_bar_screen_title),
                    fontWeight = FontWeight.SemiBold
                )
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
        LazyVerticalGrid(
            columns = GridCells.Adaptive(150.dp),
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(16.dp)
        ) {
            items(count = 20) {
                val isFavorite = remember { mutableStateOf(false) }
                val rating = remember { (0.. 5).random().toFloat() +(1f/(0..9).random().toFloat()) }
                GridBookItem(
                    modifier = Modifier,
                    title= "The Hidden Path",
                    rating = rating,
                    ratingState = ratingState,
                    price = "22.43$",
                    isFavorite = isFavorite.value,
                    onFavClick = { isFavorite.value = !isFavorite.value }
                )
            }
        }
        DisposableEffect(Unit) {
            ratingState.clear()
            onDispose {}
        }
    }
}