package ru.marat.feature_profile.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import ru.marat.core_ui.components.app.GridBookItem
import ru.marat.feature_profile.presentation.components.SearchTextField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
) {
    val density = LocalDensity.current
    val systemBars = WindowInsets.systemBars

    val field = remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        SearchTextField(
            modifier = Modifier
                .padding(16.dp)
                .padding(
                    top = density.run{ systemBars.getTop(this).toDp() }
                )
                .height(45.dp)
                .fillMaxWidth(),
            value = field.value,
            onValueChange = {
                field.value = it
            }
        )
        Spacer(
            modifier = Modifier.fillMaxWidth().height(1.dp).background(Color.LightGray)
        )
        LazyVerticalGrid(
            modifier = Modifier.fillMaxSize(),
                    columns = GridCells.Adaptive(150.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(16.dp)
        ) {
            items(100) {
                GridBookItem(
                    modifier = Modifier.fillMaxWidth(),
                    title = "Example",
                    isFavorite = false,
                    rating = 3.5f,
                    price = "105 ₽",
                    onFavClick = {}
                )
            }
        }
    }
}