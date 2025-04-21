package ru.marat.feature_reader

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import ru.marat.pdf_reader.layout.ReaderLayout
import ru.marat.pdf_reader.layout.state.rememberReaderLayoutState


@Composable
fun ReaderScreenUi(
    modifier: Modifier = Modifier,
    viewModel: ReaderViewModel
) {
    val state = viewModel.state.collectAsState()
    val readerState = rememberReaderLayoutState(
        uri = state.value.fileUri,
        minZoom = 0.4f,
        maxZoom = 20f
    )
    Column(
        modifier =
            modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(WindowInsets.safeDrawing.asPaddingValues())
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Button(
                onClick = {

                }
            ) {
                Text("Настройки")
            }
        }
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            ReaderLayout(
                modifier = Modifier
                    .fillMaxSize()
                    .clipToBounds(),
                layoutState = readerState
            ) {}
        }
    }
}