package ru.marat.feature_book.components

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.datetime.toLocalDateTime
import ru.marat.core_ui.components.common.DefaultAvatar
import ru.marat.core_ui.components.common.Rating
import ru.marat.core_ui.theme.AppTheme
import ru.marat.feature_book.model.Review
import ru.marat.library.ui.theme.LibraryTheme
import java.time.format.DateTimeFormatter


@SuppressLint("SimpleDateFormat")
@Composable
fun Comment(
    modifier: Modifier = Modifier,
    review: Review
) {
    val context = LocalContext.current
    val colorScheme = AppTheme.colorScheme
    val defaultImage = remember(colorScheme) {
        DefaultAvatar(
            context,
            backgroundColor = colorScheme.avatarBackground,
            imageColor = colorScheme.avatarIcon
        )
    }
    Column(modifier) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                modifier = Modifier
                    .size(30.dp)
                    .clip(CircleShape),
                model = ImageRequest.Builder(context)
                    .placeholder(defaultImage)
                    .crossfade(true)
                    .error(defaultImage)
                    .data(review.avatarUrl)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
            Text(
                text = review.name,
                color = colorScheme.profileText,
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        Row(
            Modifier.padding(top = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Rating(
                modifier = Modifier
                    .height(16.dp)
                    .width((16 * 5).dp),
                rating = review.rating.toFloat(),
                color = MaterialTheme.colorScheme.primary,
                starsCount = 5,
            )
            val date = remember {
                review.date
                    .toLocalDateTime(TimeZone.currentSystemDefault())
                    .toJavaLocalDateTime()
                    .format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
            }
            Text(
                modifier = Modifier.padding(start = 8.dp),
                text = date,
                color = colorScheme.settingsCategory,
                fontWeight = FontWeight.Medium,
                fontSize = 12.sp,
            )
        }
        review.text?.let {
            Text(
                modifier = Modifier.padding(top = 8.dp),
                text = it,
                color = colorScheme.profileText,
                fontWeight = FontWeight.Medium,
            )
        }
    }
}

@Preview
@Composable
fun CommentPreview() {
    LibraryTheme(
        false
    ) {
        Comment(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(16.dp),
            Review(
                "Akalsma askjdnaskda",
                "LKMLk SKJkjSLKMLk SKJkjSLKMLk SKJkjSLKMLk SKJkjSLKMLk SKJkjSLKMLk SKJkjSLKMLk SKJkjS",
                3,
                Clock.System.now(),
                ""
            )
        )
    }
}

@Preview
@Composable
fun CommentDarkPreview() {
    LibraryTheme(
        true
    ) {
        Comment(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp),
            Review(
                "Akalsma askjdnaskda",
                "LKMLk SKJkjSLKMLk SKJkjSLKMLk SKJkjSLKMLk SKJkjSLKMLk SKJkjSLKMLk SKJkjSLKMLk SKJkjS",
                3,
                Clock.System.now(),
                ""
            )
        )
    }
}