package ru.marat.core_ui.components.common

import android.content.Context
import android.graphics.Paint
import androidx.annotation.DrawableRes
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.asAndroidColorFilter
import androidx.compose.ui.graphics.toArgb
import androidx.core.graphics.drawable.toBitmap
import coil3.Canvas
import coil3.Image
import ru.marat.core_ui.R as CoreRes

@Immutable
class DefaultAvatar(
    context: Context,
    @DrawableRes
    id: Int = CoreRes.drawable.ic_person_outlined,
    private val backgroundColor: Color = Color.White,
    imageColor: Color = Color.Black,
    imageSize: Int = 384
) : Image {

    private val bitmap = context.getDrawable(id)?.toBitmap(imageSize, imageSize)

    override val size: Long
        get() = bitmap?.allocationByteCount?.toLong() ?: 0
    override val width: Int = imageSize
    override val height: Int = imageSize
    override val shareable: Boolean
        get() = true

    private val paint = Paint()
    private val colorFilter = ColorFilter.tint(imageColor).asAndroidColorFilter()
    override fun draw(canvas: Canvas) {
        if (bitmap == null) return
        paint.color = backgroundColor.toArgb()
        canvas.drawRect(
            0f, 0f, width.toFloat(), height.toFloat(), paint
        )
        paint.colorFilter = colorFilter
        canvas.scale(0.6f,0.6f)
        canvas.drawBitmap(bitmap, width * 0.3f, height * 0.3f, paint)
        paint.reset()
    }
}