package ru.marat.core_ui.components.common.shapes

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.PaintingStyle
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawOutline
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.VectorProperty
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

@Immutable
class StarShape(
    private val spikes: Int
) : Shape {

    companion object {
        fun createPath(size: Size, spikes: Int): Path {
            return Path().apply {
                val centerX = size.width / 2
                val centerY = size.height / 2
                val outerRadius = size.width / 2 * 0.9f
                val innerRadius = outerRadius * 0.4f

                var rotation = -PI / 2

                for (i in 0 until spikes * 2) {
                    val radius = if (i % 2 == 0) outerRadius else innerRadius
                    val x = centerX + (radius * cos(rotation)).toFloat()
                    val y = centerY + (radius * sin(rotation)).toFloat()

                    if (i == 0) {
                        moveTo(x, y)
                    } else {
                        lineTo(x, y)
                    }

                    rotation += PI / spikes
                }
                close()
            }
        }
    }

    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {

        return Outline.Generic(createPath(size, spikes))
    }
}

@Preview
@Composable
fun StarPreview() {
    Canvas(modifier = Modifier.size(60.dp)) {
        drawOutline(
            outline = StarShape(5).createOutline(size, LayoutDirection.Ltr, Density(1f)),
            color = Color.Yellow,
            style = Stroke(width = 4f)
        )
    }
}

@Preview
@Composable
fun RoundedStarPreview() {
    Canvas(modifier = Modifier.size(60.dp)) {
        drawContext.canvas.drawPath(
            path = StarShape.createPath(size, 5),
            paint = Paint().apply {
                color = Color.Yellow
                style = PaintingStyle.Stroke
                strokeWidth = 15f
                strokeJoin = StrokeJoin.Round
            }
        )
    }
}