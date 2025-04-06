package ru.marat.feature_settings.presentation.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight.Companion.W400
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.fastForEach
import com.turtleteam.ui.theme_animator.LocalThemeAnimator
import ru.marat.core_ui.components.common.clickWithoutIndication
import ru.marat.core_ui.theme.AppTheme
import ru.marat.library.ui.theme.LibraryTheme
import ru.marat.library.ui.theme.LocalTheme
import ru.marat.library.ui.theme.Theme
import ru.marat.library.ui.theme.isDark

@Composable
fun ThemeSelector( //todo надо бы по-нормальному сделать
    modifier: Modifier = Modifier
) {
    val systemIsDark = isSystemInDarkTheme()
    val themeAnimator = LocalThemeAnimator.current
    val shape = RoundedCornerShape(14.dp)
    val density = LocalDensity.current
    val currentLocalTheme = LocalTheme.current
    val currentTheme = remember { mutableStateOf(currentLocalTheme) }
    val cornerRadiusPx = remember(density) { density.run { 16.dp.toPx() } }
    val paddingPx = remember(density) { density.run { 4.dp.toPx() } }
    val spacingPx = remember(density) { density.run { 8.dp.toPx() } }
    val bounds = remember { mutableStateOf<Rect?>(null) }
    Box(
        modifier = modifier
            .background(AppTheme.colorScheme.themeButtonsBackground, shape),
    ) {
        Themes(
            modifier = Modifier
                .fillMaxWidth(),
            onClick = {
                currentTheme.value = it
                if (currentLocalTheme.isDark(systemIsDark) != it.isDark(systemIsDark)) {
                    val inWindow = bounds.value!!
                    val btnWidth = (inWindow.width - ((spacingPx * 2) + (paddingPx * 2))) / 3
                    val btnHeight = inWindow.height - (paddingPx * 2)
                    val left = inWindow.left + paddingPx
                    val top = inWindow.top + paddingPx
                    val rect = Rect(
                        left = left,
                        top = top,
                        right = left + btnWidth,
                        bottom = top + btnHeight
                    )
                    val buttonBounds = rect.translate(
                        translateX = (btnWidth + spacingPx) * offsetFraction(currentTheme.value),
                        translateY = 0f
                    )
                    themeAnimator.changeTheme(
                        it,
                        RoundRect(buttonBounds, CornerRadius(cornerRadiusPx)),
                    )
                } else themeAnimator.fastChangeTheme(it)
            },
        )
        SelectedTheme(
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned {
                    bounds.value = it.boundsInWindow()
                },
            cornerRadiusPx = cornerRadiusPx,
            offsetFraction = offsetFraction(currentTheme.value),
            paddingPx = paddingPx,
            spacingPx = spacingPx
        )
//        Text(
//            text = currentLocalTheme.name + " ${offsetFraction}",
//        )
    }
}

@Composable
private fun SelectedTheme(
    modifier: Modifier = Modifier,
    cornerRadiusPx: Float,
    offsetFraction: Float,
    paddingPx: Float,
    spacingPx: Float
) {
    val buttonBackgroundColor = animateColorAsState(MaterialTheme.colorScheme.secondaryContainer)
    Row(
        modifier = Modifier
            .height(IntrinsicSize.Max)
            .drawWithCache {
                val rectWidth = (size.width - (16.dp.toPx() + 8.dp.toPx())) / 3
                var rect = Rect(
                    offset = Offset(paddingPx, paddingPx),
                    size = Size(rectWidth, size.height - (paddingPx * 2))
                )
                val multiplier = rect.width + spacingPx
                rect = rect.translate(
                    translateY = 0f,
                    translateX = multiplier * offsetFraction
                )
                onDrawWithContent {
                    clipRect(
                        left = rect.left,
                        top = rect.top,
                        right = rect.right,
                        bottom = rect.bottom,
                    ) {
                        val strokeWidthPx = (1).dp.toPx()
                        drawRoundRect(
                            color = buttonBackgroundColor.value,
                            topLeft = rect.topLeft,
                            size = rect.size,
                            cornerRadius = CornerRadius(cornerRadiusPx, cornerRadiusPx)
                        )
                        scale(
                            scale = rect.width / rect.inflate(strokeWidthPx).width,
                            pivot = rect.center
                        ) {
                            drawRoundRect(
                                color = Color.Gray.copy(alpha = 0.5f),
                                topLeft = rect.topLeft,
                                size = rect.size,
                                style = Stroke(strokeWidthPx),
                                cornerRadius = CornerRadius(cornerRadiusPx, cornerRadiusPx)
                            )
                        }
                        this@onDrawWithContent.drawContent()
                    }
                }
            }
            .then(modifier),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Theme.entries.fastForEach {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .padding(top = 12.dp, bottom = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {

                Icon(
                    modifier = Modifier
                        .fillMaxWidth(0.25f)
                        .aspectRatio(1f),
                    painter = painterResource(id = it.icon),
                    tint = MaterialTheme.colorScheme.primary,
                    contentDescription = null
                )
                Text(
                    text = when (it) { //todo res
                        Theme.SYSTEM -> "Системная"
                        Theme.DARK -> "Тёмная"
                        Theme.LIGHT -> "Светлая"
                    },
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 13.sp,
                    fontWeight = W400,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Composable
private fun Themes(
    modifier: Modifier = Modifier,
    onClick: (Theme) -> Unit = {},
) {
    Row(
        modifier = Modifier
            .height(IntrinsicSize.Max)
            .then(modifier),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Theme.entries.fastForEach {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .padding(top = 12.dp, bottom = 8.dp)
                    .clickWithoutIndication {
                        onClick(it)
                    },
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {

                Icon(
                    modifier = Modifier
                        .fillMaxWidth(0.25f)
                        .aspectRatio(1f),
                    painter = painterResource(id = it.icon),
                    tint = AppTheme.colorScheme.themeDisabled,
                    contentDescription = null
                )
                Text(
                    text = when (it) { //todo res
                        Theme.SYSTEM -> "Системная"
                        Theme.DARK -> "Тёмная"
                        Theme.LIGHT -> "Светлая"
                    },
                    color = AppTheme.colorScheme.themeDisabled,
                    fontSize = 13.sp,
                    fontWeight = W400,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

private fun offsetFraction(theme: Theme): Float {
    return when (theme) {
        Theme.SYSTEM -> 0f
        Theme.DARK -> 1f
        Theme.LIGHT -> 2f
    }
}

@Preview
@Composable
fun ThemeSelectorLightPreview() {
    LibraryTheme(
        darkTheme = false
    ) {
        Box(
            Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.5f)
                .padding(16.dp)
        ) {
            ThemeSelector(
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Preview
@Composable
fun ThemeSelectorDarkPreview() {
    LibraryTheme(
        darkTheme = true
    ) {
        Box(
            Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.5f)
                .padding(16.dp)
        ) {
            ThemeSelector(
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}