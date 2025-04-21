package ru.marat.core_ui.components.common

import androidx.compose.runtime.Immutable
import androidx.core.graphics.createBitmap
import coil3.Canvas
import coil3.Image

@Immutable
object ErrorPlaceHolder : Image {
    private val bm = createBitmap(100, 100)
    override val size: Long
        get() = bm.allocationByteCount.toLong()
    override val width: Int
        get() = 100
    override val height: Int
        get() = 100
    override val shareable: Boolean
        get() = true

    override fun draw(canvas: Canvas) {
        canvas.drawBitmap(bm, 0f, 0f, null)
    }

}