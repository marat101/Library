package ru.marat.feature_profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = Modifier.fillMaxSize().background(Color.Blue)
    ) {
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = "SearchScreen"
        )
    }
}