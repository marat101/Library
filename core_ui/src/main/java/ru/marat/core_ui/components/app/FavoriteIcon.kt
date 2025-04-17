package ru.marat.core_ui.components.app

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import ru.marat.core_ui.R
import ru.marat.core_ui.theme.AppTheme
import ru.marat.library.ui.theme.LibraryTheme


@Composable
fun FavoriteButton(
    modifier: Modifier = Modifier,
    isFavorite: Boolean,
    onFavoriteClick: (Boolean) -> Unit
) {
    val shape = RoundedCornerShape(100.dp)
    val scope = rememberCoroutineScope()
    val isFavoriteAnim =
        remember { Animatable(if (isFavorite) 1f else 0f).apply { updateBounds(0f, 2f) } }
    Box(
        modifier =
            modifier
                .shadow(4.dp, shape)
                .background(AppTheme.colorScheme.bookItemBackground, shape)
                .clickable(
                    indication = ripple(),
                    interactionSource = remember { MutableInteractionSource() },
                    onClick = {
                        if (isFavoriteAnim.isRunning) return@clickable
                        val targetValue = if (isFavorite) 0f else 1f
                        onFavoriteClick(!isFavorite)
                        scope.launch {
                            isFavoriteAnim.animateTo(
                                targetValue,
                                spring(
                                    dampingRatio = 0.31f,
                                    stiffness = 350f
                                ),
                            )
                        }
                    }
                )
                .padding(
                    horizontal = 6.dp,
                    vertical = 11.dp
                )
    ) {
        val iconModifier = Modifier.size(20.dp)
        Icon(
            painter = painterResource(id = R.drawable.ic_heart_outined),
            contentDescription = null,
            modifier = iconModifier,
            tint = MaterialTheme.colorScheme.primary
        )
        Icon(
            painter = painterResource(id = R.drawable.ic_heart),
            contentDescription = null,
            modifier = iconModifier.graphicsLayer {
                scaleX = isFavoriteAnim.value
                scaleY = isFavoriteAnim.value
            },
            tint = MaterialTheme.colorScheme.primary
        )
    }
}

@Preview
@Composable
fun FavoriteButtonLightPreview() {
    LibraryTheme(false) {
        val isFavorite = remember { mutableStateOf(false) }
        GridBookItem(
            title = "Как написать хороший код",
            price = "Бесплатно",
            isFavorite = isFavorite.value,
            onFavClick = {
                isFavorite.value = it
            }
        )
    }
}

@Preview
@Composable
fun FavoriteButtonDarkPreview() {
    LibraryTheme(true) {
        val isFavorite = remember { mutableStateOf(true) }
        GridBookItem(
            title = "Как написать хороший код",
            price = "Бесплатно",
            isFavorite = isFavorite.value,
            onFavClick = {
                isFavorite.value = it
            }
        )
    }
}