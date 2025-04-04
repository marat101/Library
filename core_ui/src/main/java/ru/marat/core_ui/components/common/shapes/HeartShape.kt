package ru.marat.core_ui.components.common.shapes

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawOutline
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import kotlin.math.cos
import kotlin.math.sin

@Immutable
object HeartShape : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val width = size.width
        val height = size.height
        val centerX = width / 2
        val centerY = height / 2
        val radius = minOf(width, height) * 0.4f

        val path = Path()

        val points = (0..100).map { i ->
            val t = i * 2 * Math.PI / 100
            val x = 16 * sin(t) * sin(t) * sin(t)
            val y = 13 * cos(t) - 5 * cos(2 * t) - 2 * cos(3 * t) - cos(4 * t)
            Offset(
                (centerX + x * radius / 16).toFloat(),
                (centerY - y * radius / 16).toFloat()
            )
        }

        path.moveTo(points.first().x, points.first().y)
        points.drop(1).forEach { point ->
            path.lineTo(point.x, point.y)
        }
        path.close()
        return Outline.Generic(path)
    }

}

@Preview
@Composable
fun HeartPreview() {
    Canvas(modifier = Modifier.size(60.dp)) {
        drawOutline(
            outline = HeartShape.createOutline(size, LayoutDirection.Ltr, Density(density)),
            color = Color.Red,
            style = Stroke(width = 8f)
        )
    }
}