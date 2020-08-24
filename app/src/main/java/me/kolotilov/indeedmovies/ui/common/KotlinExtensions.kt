package me.kolotilov.indeedmovies.ui.common

import android.view.View

/**
 * Casts variable to type T.
 */
@Suppress("UNCHECKED_CAST")
fun <T> Any.castTo(): T {
    return this as T
}

fun Boolean.toVisibleOrGone(): Int {
    return if (this) View.VISIBLE else View.GONE
}

fun Any?.toStringOrEmpty(): String {
    return this?.toString() ?: ""
}