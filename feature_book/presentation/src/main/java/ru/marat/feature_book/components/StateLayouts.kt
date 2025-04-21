package ru.marat.feature_book.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.marat.core_ui.theme.AppTheme
import ru.marat.core_ui.view_model.LoadingState
import ru.marat.feature_book.model.BookInfo

fun LazyListScope.StateLayouts(
    state: LoadingState<BookInfo>,
    onRetry: () -> Unit
) {
    when (state) {
        is LoadingState.Error -> item(
            key = "Error"
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 40.dp, horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Ошибка загрузки",
                    color = AppTheme.colorScheme.profileText
                )
                Button(
                    modifier = Modifier.padding(top = 16.dp),
                    onClick = onRetry
                ) {
                    Text(
                        text = "Повторить"
                    )
                }
            }
        }

        LoadingState.Loading -> item(key = "Loading") {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        else -> {}
    }

}