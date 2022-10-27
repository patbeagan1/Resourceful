package dev.patbeagan.resourceful

import android.content.Context
import android.graphics.Color as AndroidColor
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color as ComposeColor
import androidx.compose.ui.platform.LocalContext
import dev.patbeagan.resourceful.Resourceful.*

sealed interface ColorUnresolved :
    UnresolvedTraditional<ColorResolved>,
    UnresolvedComposeBridge<ComposeColor> {

    val attrIdOrDefault: Int get() = (this as? ColorAttr)?.colorAttr ?: DefaultColor
    val unsafeId: Int
        get() = when (this) {
            is ColorAttr -> colorAttr
            is ColorResource -> colorResource
            is ColorString -> 0
        }
    val isValid get() = unsafeId != 0
    fun isValidAfterResolving(context: Context) =
        isValid && resolve(context).colorInt != DefaultColor

    override fun resolve(context: Context) = when (this) {
        is ColorAttr -> context.getColorAttr(colorAttr)
        is ColorResource -> context.getColor(colorResource)
        is ColorString -> safeParse()
    }.let { ColorResolved(it) }

    @Composable
    override fun resolveComposeBridge(): ComposeColor =
        ComposeColor(resolve(LocalContext.current).colorInt)

    @JvmInline
    value class ColorResource(@ColorRes val colorResource: Int) : ColorUnresolved

    @JvmInline
    value class ColorAttr(@AttrRes val colorAttr: Int) : ColorUnresolved {
        fun Context.getColorAttr(@AttrRes attrId: Int): Int {
            val typedArray = obtainStyledAttributes(intArrayOf(attrId))
            val color = typedArray.getColor(0, DefaultColor)
            typedArray.recycle()
            return color
        }
    }

    @JvmInline
    value class ColorString(private val colorString: String) : ColorUnresolved {
        @ColorInt
        internal fun safeParse(): Int = try {
            AndroidColor.parseColor(colorString)
        } catch (e: IllegalArgumentException) {
            DefaultColor
        } catch (e: StringIndexOutOfBoundsException) {
            DefaultColor
        }
    }

    companion object {
        const val DefaultColor = AndroidColor.MAGENTA
    }
}