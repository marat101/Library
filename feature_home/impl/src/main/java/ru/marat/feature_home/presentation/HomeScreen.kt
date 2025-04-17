package ru.marat.feature_home.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import ru.marat.core_ui.components.app.GridBookItem
import ru.marat.core_ui.components.common.rememberRatingState
import ru.marat.core_ui.theme.AppTheme
import ru.marat.core_ui.view_model.LoadingState
import ru.marat.feature_home.api.R
import ru.marat.feature_home.di.homeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = homeViewModel()
) {
    val ratingState = rememberRatingState()
    val state = viewModel.state.collectAsState()
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
                IconButton(
                    onClick = {
                        viewModel.onSearchClick()
                    }
                ) {
                    Icon(
                        painter = painterResource(ru.marat.core_ui.R.drawable.ic_search),
                        contentDescription = null
                    )
                }
            }
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
        ) {
            LazyVerticalGrid(
                columns = GridCells.Adaptive(150.dp),
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(16.dp)
            ) {
                items(
                    state.value.books,
                    key = { it.id }
                ) { book ->
                    GridBookItem(
                        modifier = Modifier,
                        title = book.name,
                        imageUrl = book.imageUrl,
                        rating = 0f,
                        ratingState = ratingState,
                        price = "Бесплатно",
                        isFavorite = book.isFavorite,
                        onFavClick = {
                            viewModel.onFavoriteClick(book.id)
                            //todo
                        },
                        onClick = {
                            viewModel.select(book.id)
                        }
                    )
                }
//                items(count = 20) {
//                    val isFavorite = remember { mutableStateOf(false) }
//                    val rating =
//                        remember { (0..5).random().toFloat() + (1f / (0..9).random().toFloat()) }
//                    GridBookItem(
//                        modifier = Modifier,
//                        title = "The Hidden Path",
//                        rating = rating,
//                        ratingState = ratingState,
//                        price = "22.43$",
//                        isFavorite = isFavorite.value,
//                        onFavClick = { isFavorite.value = !isFavorite.value }
//                    )
//                }
            }
            when (state.value.loadingState) {
                is LoadingState.Error -> {
                    Column(
                        modifier = Modifier.align(Alignment.Center),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Ошибка",
                            color = AppTheme.colorScheme.profileText
                        )
                        Button(
                            onClick = {
                                viewModel.loadMainScreen()
                            }
                        ) {
                            Text(
                                "Повторить",
                                color = Color.White
                            )
                        }
                    }
                }

                LoadingState.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                is LoadingState.Success<*> -> {}
            }
        }
        DisposableEffect(Unit) {
            viewModel.loadMainScreen()
            onDispose {
                ratingState.clear()
            }
        }
        DownloadingDialog(
            id = state.value.selectedBook ?: 0,
            visible = state.value.selectedBook != null,
            onClose = { viewModel.select(null) },
            onOpenBook = {
                viewModel.openBook(it)
            }
        )
    }
}