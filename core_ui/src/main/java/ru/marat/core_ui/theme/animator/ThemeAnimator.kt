package com.turtleteam.ui.theme_animator

import android.graphics.Bitmap
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.lerp
import androidx.compose.ui.geometry.toRect
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.marat.library.ui.theme.Theme
import kotlin.math.pow
import kotlin.math.sqrt


val LocalThemeAnimator =
    staticCompositionLocalOf<ThemeAnimator> { error("theme animator is not provided") }

@Composable
fun ThemeAnimator(
    modifier: Modifier,
    onThemeChange: (Theme) -> Unit,
    content: @Composable () -> Unit
) {
    var composableBounds by remember { mutableStateOf<Rect?>(null) }

    Box(
        modifier = Modifier
            .background(Color.Transparent)
            .then(modifier)
            .onGloballyPositioned {
                composableBounds = it.boundsInWindow()
            }
    ) {
        val scope = rememberCoroutineScope()
        val density = LocalDensity.current
        val config = LocalConfiguration.current
        val view = LocalView.current
        val anim = remember(view) {
            ThemeAnimatorImpl(scope, onThemeChange) {
                density.run {
                    view.bitmap(
                        composableBounds ?: Rect(
                            0f,
                            0f,
                            config.screenWidthDp.dp.toPx(),
                            config.screenHeightDp.dp.toPx()
                        )
                    )
                }
            }
        }
        CompositionLocalProvider(LocalThemeAnimator provides anim) { content() }
        anim.bitmap.value?.let {
            Image(
                modifier = Modifier
                    .fillMaxSize()
                    .then(if (anim.bitmap.value != null) Modifier.pointerInput(Unit) { detectTapGestures {} } else Modifier)
                    .graphicsLayer(compositingStrategy = CompositingStrategy.Offscreen)
                    .drawWithCache {
                        val roundRect = RoundRect(rect = size.toRect())
                        onDrawWithContent {
                            drawContent()
                            val rect = lerp(
                                anim.clickRect,
                                roundRect,
                                anim.animatable.value
                            )
                            drawRoundRect(
                                color = Color.Transparent,
                                topLeft = Offset(
                                    rect.left,
                                    rect.top
                                ),
                                size = Size(
                                    width = rect.width,
                                    height = rect.height
                                ),
                                cornerRadius = rect.topLeftCornerRadius,
                                blendMode = BlendMode.DstIn,
                            )
                        }
                    },
                bitmap = it.asImageBitmap(),
                contentDescription = null
            )
        }
    }
}

@Stable
private class ThemeAnimatorImpl(
    val scope: CoroutineScope,
    val onThemeChange: (Theme) -> Unit,
    val onCreateImage: () -> Bitmap?,
) : ThemeAnimator {
    val animatable = Animatable(0f)
    var clickRect = RoundRect.Zero
    val bitmap: MutableState<Bitmap?> = mutableStateOf(null)

    override fun changeTheme(theme: Theme, offset: RoundRect, animationSpec: AnimationSpec<Float>) {
        if (!animatable.isRunning) {
            scope.launch {
                clickRect = offset
                onCreateImage().let {
                    if (it == null) {
                        onThemeChange(theme)
                        return@launch
                    }
                    bitmap.value = it
                }
                onThemeChange(theme)
                delay(150)
                animatable.animateTo(1f, animationSpec)
                bitmap.value = null
                animatable.snapTo(0f)
            }
        }
    }

    override fun fastChangeTheme(theme: Theme) {
        onThemeChange(theme)
    }
}

private fun getRadius(
    size: Rect,
    point: Offset
): Float {

    return maxOf(
        size.topLeft.getDistance(point),
        size.bottomLeft.getDistance(point),
        size.topRight.getDistance(point),
        size.bottomRight.getDistance(point)
    )
}

private fun Offset.getDistance(point: Offset) = sqrt(
    (x - point.x).toDouble().pow(2.toDouble()) + (point.y - y).toDouble()
        .pow(2.toDouble())
).toFloat()

interface ThemeAnimator {
    fun changeTheme(theme: Theme, offset: RoundRect, animationSpec: AnimationSpec<Float> = tween(400))
    fun fastChangeTheme(theme: Theme)
}