package dev.patbeagan.resourceful

import android.content.res.ColorStateList
import android.graphics.drawable.ColorDrawable
import androidx.annotation.ColorInt
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@JvmInline
value class ColorResolved(@ColorInt val colorInt: Int) : Resourceful.UnresolvedCompose<Color> {
    fun asColorDrawable() = ColorDrawable(colorInt)
    fun asColorStateList() = ColorStateList.valueOf(colorInt)
    @Composable
    override fun resolveCompose(): Color = Color(colorInt)
}