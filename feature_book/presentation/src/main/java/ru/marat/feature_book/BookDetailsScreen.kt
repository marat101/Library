package ru.marat.feature_book

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import ru.marat.core_ui.theme.AppTheme
import ru.marat.feature_book.components.BookDescription
import ru.marat.feature_book.components.Comment
import ru.marat.feature_book.components.CommonRating
import ru.marat.feature_book.components.PreviewImageWithBackground
import ru.marat.feature_book.components.StateLayouts
import ru.marat.feature_book.model.BookFile
import ru.marat.library.ui.theme.LocalTheme
import ru.marat.library.ui.theme.Theme

@Composable
fun BookDetailScreen(
    modifier: Modifier = Modifier,
    viewModel: BookDetailsViewModel
) {
    val state = viewModel.state.collectAsState()
    val reviews = viewModel.reviews.collectAsLazyPagingItems()
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        LazyColumn(
            modifier = Modifier,
            contentPadding = PaddingValues(
                bottom = WindowInsets.statusBars.asPaddingValues().calculateBottomPadding() + 16.dp
            )
        ) {
            item(
                key = "Preview"
            ) {
                PreviewImageWithBackground(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(380.dp),
                    contentPadding = PaddingValues(
                        top = WindowInsets.statusBars.asPaddingValues().calculateTopPadding(),
                        bottom = 16.dp
                    ),
                    url = state.value.bookInfo.dataOrNull()?.imageUrl
                )
            }
            StateLayouts(
                state = state.value.bookInfo,
                onRetry = viewModel::loadBookInfo
            )
            if (!state.value.bookInfo.isSuccess) return@LazyColumn
            item {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                        .padding(horizontal = 16.dp),
                    text = state.value.bookInfo.dataOrNull()?.title ?: "",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = AppTheme.colorScheme.profileText
                )
            }
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 18.dp)
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(onClick = {
                        when (state.value.book) {
                            BookFile.Loaded -> viewModel.onOpenBook()
                            is BookFile.Loading -> viewModel.onCancelDownload()

                            else -> viewModel.onDownloadBook()
                        }
                    }) {
                        when (val loadingState = state.value.book) {
                            BookFile.Loaded -> {
                                Text("Читать")
                            }

                            is BookFile.Loading -> {
                                Text("Отмена")
                                CircularProgressIndicator(
                                    modifier = Modifier.size(35.dp),
                                    progress = { loadingState.progress },
                                    color = Color.White
                                )
                            }

                            else -> Text("Загрузить")
                        }
                    }
                    Button(
                        modifier = Modifier,
                        onClick = {
                            viewModel.onCancelDownload()
                        }
                    ) {
                        Text(
                            text = "delete"
                        )
                    }
                }
            }
            item(
                key = "Description"
            ) {
                BookDescription(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.background)
                        .padding(top = 16.dp, bottom = 10.dp)
                        .padding(horizontal = 16.dp),
                    text = state.value.bookInfo.dataOrNull()?.description ?: ""
                )
            }
            item {
                CommonRating(
                    modifier = Modifier
                        .fillMaxWidth()

                        .drawBehind {
                            val strokeWidth = 1.dp.toPx()
                            val color = Color.Gray.copy(0.3f)
                            drawLine(
                                color = color,
                                start = Offset(0f, 0f),
                                end = Offset(size.width, 0f),
                                strokeWidth = strokeWidth
                            )
                            drawLine(
                                color = color,
                                start = Offset(0f, size.height),
                                end = Offset(size.width, size.height),
                                strokeWidth = strokeWidth
                            )
                        }
                        .padding(horizontal = 16.dp, vertical = 16.dp),
                    rating = state.value.bookInfo.dataOrNull()?.rating!!
                )
            }
            item {
                reviews.retry() //todo
            }
            items(reviews.itemCount) { index ->
                reviews[index]?.let { review ->
                    Comment(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.background)
                            .padding(16.dp),
                        review = review
                    )
                }
            }
            when {
                reviews.loadState.append is LoadState.Loading ||
                        reviews.loadState.refresh is LoadState.Loading -> {
                    item {
                        Box(
                            Modifier
                                .fillMaxWidth()
                                .height(60.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }

                reviews.loadState.append is LoadState.Error -> { //todo res
                    item {
                        Column(
                            Modifier
                                .fillMaxWidth()
                                .height(70.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text("Ошибка загрузки")
                            Button(onClick = reviews::retry) {
                                Text("Повторить")
                            }
                        }
                    }
                }

                reviews.loadState.refresh is LoadState.Error -> {
                    item {
                        Column(
                            Modifier
                                .fillMaxWidth()
                                .height(70.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text("Ошибка загрузки")
                            Button(onClick = reviews::retry) {
                                Text("Повторить")
                            }
                        }
                    }
                }
            }
        }
        val btnShape = RoundedCornerShape(10.dp)
        Box(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(WindowInsets.safeDrawing.asPaddingValues())
                .padding(horizontal = 16.dp)
                .shadow(4.dp, btnShape)
                .size(45.dp)
                .background(MaterialTheme.colorScheme.secondaryContainer, btnShape)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = ripple(),
                    onClick = viewModel::onBackClick
                ),
            contentAlignment = Alignment.Center,
        ) {
            val theme = LocalTheme.current
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = null,
                tint = if (theme == Theme.DARK) Color.LightGray else Color.Gray,
                modifier = Modifier.size(24.dp)
            )
        }
    }

    DisposableEffect(Unit) {
        viewModel.loadBookInfo()
        onDispose {}
    }
}

@Preview
@Composable
fun BookDetailScreenPreview() {
}