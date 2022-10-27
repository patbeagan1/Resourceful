package dev.patbeagan.resourceful

import android.content.Context
import androidx.compose.runtime.Composable

interface Resourceful {

    interface UnresolvedTraditional<T> {
        fun resolve(context: Context): T
    }

    interface UnresolvedComposeBridge<T> {
        @Composable
        fun resolveComposeBridge(): T
    }

    interface UnresolvedCompose<T> {
        @Composable
        fun resolveCompose(): T
    }
}