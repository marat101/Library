package ru.marat.core_ui.components.app

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.drawOutline
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import ru.marat.core_ui.components.common.shapes.HeartShape
import ru.marat.core_ui.theme.AppTheme
import ru.marat.library.ui.theme.LibraryTheme

@Composable
fun BookItem(
    modifier: Modifier = Modifier,
    title: String,
    description: String,
    image: ImageBitmap? = null,
    isFavorite: Boolean = false,
    onItemClick: () -> Unit = {},
    onFavoriteClick: () -> Unit = {},
    onButtonClick: () -> Unit = {}
) {
    val shape = RoundedCornerShape(8.dp)
    val isFavoriteAnim = remember { Animatable(if (isFavorite) 1f else 0f).apply { updateBounds(0f,2f) } }
    val scope = rememberCoroutineScope()
    val primaryColor = MaterialTheme.colorScheme.primary
    Row(
        modifier = Modifier
            .height(160.dp)
            .background(AppTheme.colorScheme.bookItemBackground, shape)
            .clip(shape)
            .clickable(
                indication = ripple(),
                interactionSource = remember { MutableInteractionSource() },
                onClick = onItemClick
            )
            .then(modifier)
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .aspectRatio(0.65f)
                .background(Color.Blue, shape)
        )
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f)
                .padding(
                    vertical = 12.dp,
                    horizontal = 10.dp
                ),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        modifier = Modifier,
                        text = title,
                        color = AppTheme.colorScheme.bookItemTitle,
                        maxLines = 1,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.ExtraBold,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = description,
                        maxLines = 3,
                        fontSize = 12.sp,
                        overflow = TextOverflow.Ellipsis,
                        color = AppTheme.colorScheme.bookItemDescription,
                        style = TextStyle(
                            lineHeightStyle = LineHeightStyle(
                                LineHeightStyle.Alignment.Top,
                                LineHeightStyle.Trim.Both
                            )
                        )
                    )
                }
                Box(
                    Modifier
                        .size(28.dp)
                        .drawBehind {
                            drawOutline(
                                outline = HeartShape.createOutline(
                                    size,
                                    layoutDirection,
                                    this
                                ),
                                color = primaryColor,
                                style = Stroke(width = 2.dp.toPx())
                            )
                            scale(isFavoriteAnim.value) {
                                drawOutline(
                                    outline = HeartShape.createOutline(
                                        size,
                                        layoutDirection,
                                        this
                                    ),
                                    color = primaryColor,
                                    style = Fill
                                )
                            }
                        }
                        .clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() },
                            onClick = {
                                if (isFavoriteAnim.isRunning) return@clickable
                                scope.launch {
                                    onFavoriteClick()
                                    isFavoriteAnim.animateTo(
                                        if (isFavorite) 1f else 0f,
                                        spring(
                                            dampingRatio = 0.31f,
                                            stiffness = 350f
                                        ),
                                    )
                                }
                            }
                        )
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Box(
                    modifier = Modifier
                )
                Button(
                    onClick = onButtonClick
                ) {
                    Text("BUY")
                }
            }
        }
    }
}

@Preview
@Composable
private fun BookItemLightPreview() {
    LibraryTheme(
        false
    ) {
        BookItemPreview()
    }
}

@Preview
@Composable
fun BookItemDarkPreview() {
    LibraryTheme(
        true
    ) {
        BookItemPreview()
    }
}

@Composable
private fun BookItemPreview() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        repeat(5) {
            BookItem(
                modifier = Modifier,
                title = "Как написать хороший код".uppercase(),
                description = "Как написать хороший код?Как написать хороший код? Как написать хороший код?",
                isFavorite = it % 2 == 1
            )
        }
    }
}