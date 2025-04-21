package ru.marat.feature_book.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import coil3.request.crossfade
import ru.marat.core_ui.components.common.ErrorPlaceHolder
import ru.marat.library.ui.theme.LibraryTheme

@Composable
fun PreviewImageWithBackground(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(),
    url: String? = null
) {
    val context = LocalContext.current
    val image = rememberAsyncImagePainter(
        model = ImageRequest.Builder(context)
            .data(url)
            .crossfade(true)
            .error { ErrorPlaceHolder }
            .listener(
                onError = { _, result ->
                    result.throwable.printStackTrace()
                }
            )
            .build()
    )
    val backgroundColor = MaterialTheme.colorScheme.background
    Box(
        modifier = modifier
            .background(backgroundColor)
            .clipToBounds()
    ) {
        Image(
            modifier = Modifier
                .fillMaxSize()
                .drawWithContent {
                    drawContent()
                    drawRect(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                backgroundColor.copy(0.75f),
                                Color.Transparent,
                                Color.Transparent,
                                backgroundColor,
                            )
                        )
                    )
                    drawRect(
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                backgroundColor.copy(0.85f),
                                Color.Transparent,
                                Color.Transparent,
                                Color.Transparent,
                                backgroundColor.copy(0.85f),
                            )
                        )
                    )
                }
                .blur(20.dp, edgeTreatment = BlurredEdgeTreatment.Unbounded),
            painter = image,
            contentScale = ContentScale.Crop,
            contentDescription = null
        )
        Image(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(contentPadding)
                .shadow(8.dp,RoundedCornerShape(6.dp))
                .aspectRatio(3f / 4f)
                .clip(RoundedCornerShape(6.dp))
                .background(Color.LightGray),
            painter = image,
            contentScale = ContentScale.Crop,
            contentDescription = null
        )
    }
}

@Preview
@Composable
fun PreviewImageWithBackgroundDarkPreview() {
    LibraryTheme(
        darkTheme = true
    ) {
        PreviewImageWithBackground(
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp),
            url = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT138g2_YHUH157rAU35EYWYfj5-tiQKfGyaQ&s"
        )
    }
}

@Preview
@Composable
fun PreviewImageWithBackgroundPreview() {
    LibraryTheme(
        darkTheme = false
    ) {
        PreviewImageWithBackground(
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp),
            url = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT138g2_YHUH157rAU35EYWYfj5-tiQKfGyaQ&s"
        )
    }
}