package dev.patbeagan.resourceful

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import dev.patbeagan.resourceful.ColorUnresolved.ColorAttr
import dev.patbeagan.resourceful.ColorUnresolved.ColorResource
import dev.patbeagan.resourceful.Resourceful.UnresolvedCompose
import dev.patbeagan.resourceful.Resourceful.UnresolvedComposeBridge
import dev.patbeagan.resourceful.Resourceful.UnresolvedTraditional

object Styles {
    sealed class Colors(
        private val color: ColorUnresolved,
        private val colorCompose: Color = Color.Magenta
    ) : UnresolvedTraditional<ColorResolved> by color,
        UnresolvedComposeBridge<Color> by color,
        UnresolvedCompose<Color> {

        @Composable
        override fun resolveCompose(): Color = colorCompose

        object Primary : Colors(ColorResource(R.color.purple_700), Color(0))
        object Secondary : Colors(ColorResource(R.color.red), Color.Green)

        sealed class TextColors(color: ColorUnresolved) : Colors(color) {
            object Label : TextColors(ColorResource(R.color.teal_700))
        }
    }
}


