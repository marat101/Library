package ru.marat.core_ui.components.common

import android.content.Context
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import ru.marat.core_ui.R
import kotlin.math.roundToInt

@Composable
fun Rating(
    modifier: Modifier = Modifier,
    rating: Float,
    maxRating: Float = maxOf(rating, 5f),
    starsCount: Int = 5,
    spacing: Dp = 0.dp,
    state: RatingState = rememberRatingState(),
    color: Color = Color.Yellow,
    outlineColor: Color = color
) {
    val context = LocalContext.current
    Canvas(
        modifier = modifier
    ) {
        val spacingPx = spacing.toPx()
        val starSize = size.height
        val totalWidth = starSize * starsCount + spacingPx * (starsCount - 1)
        state.createBitmaps(context, starSize)

        repeat(starsCount) { iteration ->
            val left = (size.height + spacingPx) * iteration
            state.outlinedStarIconBitmap?.let {
                drawImage(
                    topLeft = Offset(left, 0f),
                    image = it,
                    colorFilter = ColorFilter.tint(outlineColor)
                )
            }
        }
        clipRect(
            right = (rating / maxRating) * totalWidth,
        ) {
            repeat(starsCount) { iteration ->
                val left = (size.height + spacingPx) * iteration
                state.starIconBitmap?.let {
                    drawImage(
                        topLeft = Offset(left, 0f),
                        image = it,
                        colorFilter = ColorFilter.tint(color)
                    )
                }
            }
        }
    }
}

@Stable
class RatingState(
    @DrawableRes
    private val starIcon: Int,
    @DrawableRes
    private val outlinedStarIcon: Int,
) {

    var intSize: Int? = null
    var starIconBitmap: ImageBitmap? = null
    var outlinedStarIconBitmap: ImageBitmap? = null

    fun createBitmaps(context: Context, size: Float) {
        val newSize = size.roundToInt()
        if (intSize == newSize || newSize <= 0) return
        intSize = newSize
        starIconBitmap = ContextCompat.getDrawable(context, starIcon)
            ?.toBitmap(newSize, newSize)
            ?.asImageBitmap()
        outlinedStarIconBitmap = ContextCompat.getDrawable(context, outlinedStarIcon)
            ?.toBitmap(newSize, newSize)
            ?.asImageBitmap()
    }

    fun clear() {
        starIconBitmap = null
        outlinedStarIconBitmap = null
    }
}

@Composable
fun rememberRatingState(
    @DrawableRes
    starIcon: Int = R.drawable.ic_star,
    @DrawableRes
    outlinedStarIcon: Int = R.drawable.ic_star_outlined
): RatingState {
    return remember(starIcon, outlinedStarIcon) {
        RatingState(
            starIcon = starIcon,
            outlinedStarIcon = outlinedStarIcon
        )
    }
}

@Preview
@Composable
fun RatingPreview() {
    Column(
        Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        Rating(
            modifier = Modifier.height(20.dp),
            rating = 4.5f,
        )
        Rating(
            modifier = Modifier.height(40.dp),
            rating = 3.5f,
            spacing = 4.dp
        )
        Rating(
            modifier = Modifier.height(50.dp),
            rating = 0.4f,
            spacing = 8.dp
        )
        Rating(
            modifier = Modifier.height(60.dp),
            rating = 3.4f,
            spacing = 10.dp
        )

    }
}