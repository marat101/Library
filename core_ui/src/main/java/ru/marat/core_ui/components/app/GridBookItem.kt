package ru.marat.core_ui.components.app

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import coil3.request.crossfade
import ru.marat.core_ui.components.common.ErrorPlaceHolder
import ru.marat.core_ui.components.common.Rating
import ru.marat.core_ui.components.common.RatingState
import ru.marat.core_ui.components.common.rememberRatingState
import ru.marat.core_ui.theme.AppTheme
import ru.marat.library.ui.theme.LibraryTheme

@Composable
fun GridBookItem(
    modifier: Modifier = Modifier,
    title: String,
    imageUrl: String? = null,
    maxRating: Float = 5f,
    rating: Float = 0f,
    ratingState: RatingState = rememberRatingState(),
    price: String,
    isFavorite: Boolean,
    onClick: () -> Unit = {},
    onFavClick: (Boolean) -> Unit
) {
    val shape = RoundedCornerShape(8.dp)
    Column(
        modifier = Modifier
            .shadow(4.dp, shape)
            .fillMaxWidth()
            .background(AppTheme.colorScheme.bookItemBackground)
            .then(modifier)
            .clickable(
                indication = ripple(),
                interactionSource = remember { MutableInteractionSource() },
                onClick = onClick
            )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(3f / 4f)
                .background(Color.LightGray)
        ) {
           val context = LocalContext.current
            AsyncImage(
                modifier = Modifier.fillMaxSize(),
                model = ImageRequest.Builder(context)
                    .data(imageUrl)
                    .crossfade(true)
                    .error { ErrorPlaceHolder }
                    .listener(
                        onError = { result, error ->
                            error.throwable.printStackTrace()
                    })
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
            FavoriteButton(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp),
                isFavorite = isFavorite,
                onFavoriteClick = onFavClick
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    vertical = 10.dp,
                    horizontal = 10.dp
                ),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                modifier = Modifier,
                text = title,
                color = AppTheme.colorScheme.bookItemTitle,
                maxLines = 1,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                overflow = TextOverflow.Ellipsis
            )
            Row(
                modifier = Modifier.padding(top = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                Rating(
                    modifier = Modifier
                        .height(15.dp)
                        .aspectRatio(5f / 1),
                    rating = rating,
                    state = ratingState,
                    maxRating = maxRating,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "(${"%.1f".format(rating).replace(",", ".")})",
                    fontSize = 13.sp,
                    lineHeight = 15.sp,
                    letterSpacing = 0.sp,
                    color = MaterialTheme.colorScheme.primary,
                )
            }
            Text(
                modifier = Modifier.padding(top = 8.dp),
                text = price,
                color = AppTheme.colorScheme.bookItemTitle,
                maxLines = 1,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Preview
@Composable
private fun GridBookItemLightPreview() {
    LibraryTheme(
        darkTheme = false
    ) {
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
                GridBookItem(
                    modifier = Modifier,
                    title = "The Hidden Path",
                    price = "10.22$",
                    isFavorite = isFavorite.value,
                    onFavClick = { isFavorite.value = !isFavorite.value }
                )
            }
        }
    }
}

@Preview
@Composable
private fun GridBookItemDarkPreview() {
    LibraryTheme(
        darkTheme = true
    ) {
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
                GridBookItem(
                    modifier = Modifier,
                    title = "The Hidden Path",
                    price = "Бесплатно",
                    isFavorite = isFavorite.value,
                    onFavClick = { isFavorite.value = !isFavorite.value }
                )
            }
        }
    }
}