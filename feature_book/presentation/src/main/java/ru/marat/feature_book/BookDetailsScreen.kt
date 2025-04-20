package ru.marat.feature_book

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.marat.core_ui.theme.AppTheme
import ru.marat.feature_book.model.BookFile

@Composable
fun BookDetailScreen(
    modifier: Modifier = Modifier,
    viewModel: BookDetailsViewModel
) {
    val state = viewModel.state.collectAsState()
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        Column {
            Text(
                text = "Book Screen",
                color = AppTheme.colorScheme.profileText
            )
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