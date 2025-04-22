package ru.marat.feature_book.components

import android.annotation.SuppressLint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.center
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.fastCoerceAtLeast
import ru.marat.core_ui.theme.AppTheme
import ru.marat.feature_book.api.R
import ru.marat.feature_book.model.Rating
import ru.marat.library.ui.theme.LibraryTheme


@Composable
fun CommonRating(
    modifier: Modifier = Modifier,
    rating: Rating,
) {
    Column(
        modifier = modifier,
    ) {
        Text(
            modifier = Modifier,
            text = "Рейтинг", //todo res
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            color = AppTheme.colorScheme.profileText
        )
        Row(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth()
                .padding(top = 10.dp)
                .height(intrinsicSize = IntrinsicSize.Min)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(end = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    modifier = Modifier,
                    text = "%.1f".format(rating.average).replace(',', '.'),
                    fontSize = 45.sp,
                    fontWeight = FontWeight.Bold,
                    color = AppTheme.colorScheme.profileText
                )
                Text(
                    text = pluralStringResource(
                        R.plurals.reviews_count,
                        rating.common,
                        rating.common
                    ),
                    fontSize = 10.sp,
                    color = AppTheme.colorScheme.settingsCategory
                )
            }
            Lines(
                modifier = Modifier.weight(1f),
                rating = rating
            )
        }
    }
}

@Composable
fun Lines(
    modifier: Modifier = Modifier,
    rating: Rating,
) {
    val lineColor = MaterialTheme.colorScheme.primary
    val lineBackgroundColor = AppTheme.colorScheme.ratingLineBackground

    Column(
        modifier = modifier,
    ) {
        for (it in intArrayOf(4,3,2,1,0)) {
            val count = when (it) {
                0 -> rating.star1
                1 -> rating.star2
                2 -> rating.star3
                3 -> rating.star4
                4 -> rating.star5
                else -> 0
            }
            Row(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth()
                    .height(17.dp)
                    .padding(top = 5.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier,
                    text = it.plus(1).toString(),
                    fontSize = 10.sp,
                    lineHeight = 4.sp,
                    color = AppTheme.colorScheme.settingsCategory
                )
                Canvas(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .padding(start = 5.dp)
                ) {
                    val cornerRadius = size.center.y
                    drawRoundRect(
                        color = lineBackgroundColor,
                        cornerRadius = CornerRadius(
                            x = cornerRadius,
                            y = cornerRadius
                        )
                    )
                    drawRoundRect(
                        color = lineColor,
                        size = size.copy(
                            width = (size.width * (count.toFloat() / rating.max))
                                .fastCoerceAtLeast(cornerRadius * 2)
                        ),
                        cornerRadius = CornerRadius(
                            x = cornerRadius,
                            y = cornerRadius
                        ),
                    )
                }
            }
        }
    }

}

@SuppressLint("UnnecessaryComposedModifier")
@Composable
private fun Modifier.previewModifier() = composed {
    fillMaxWidth()
        .background(MaterialTheme.colorScheme.background)
        .padding(16.dp)
}

@Preview
@Composable
fun CommonRatingPreview() {
    LibraryTheme(
        false
    ) {
        CommonRating(
            modifier = Modifier.previewModifier(),
            rating = Rating(
                star1 = 123,
                star2 = 234,
                star3 = 0,
                star4 = 56,
                star5 = 567,
            )
        )
    }
}

@Preview
@Composable
fun CommonRatingDarkPreview() {
    LibraryTheme(
        true
    ) {
        CommonRating(
            modifier = Modifier.previewModifier(),
            rating = Rating(
                star1 = 0,
                star2 = 0,
                star3 = 0,
                star4 = 0,
                star5 = 0,
            )
        )
    }
}