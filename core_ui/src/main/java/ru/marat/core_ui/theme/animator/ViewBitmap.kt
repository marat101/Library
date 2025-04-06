package com.turtleteam.ui.theme_animator

import android.app.Activity
import android.graphics.Bitmap
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.view.PixelCopy
import android.view.View
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.nativeCanvas
import androidx.core.graphics.createBitmap

fun View.bitmap(
    bounds: Rect
): Bitmap? {

    try {
        val bitmap = createBitmap(bounds.width.toInt(), bounds.height.toInt())

        PixelCopy.request(
            (this.context as Activity).window,
            android.graphics.Rect(
                bounds.left.toInt(),
                bounds.top.toInt(),
                bounds.right.toInt(),
                bounds.bottom.toInt()
            ),
            bitmap,
            {},
            Handler(Looper.getMainLooper())
        )
        return bitmap
    } catch (e: Exception) {
        e.printStackTrace()
        return null
    }
}