package me.kolotilov.indeedmovies.ui.common

import timber.log.Timber

inline fun <T> withAll(vararg targets: T, action:  T.() -> Unit) {
    for (target in targets)
        target.action()
}

fun doNothing() {}

fun <T> doNothing(item: T) {}

fun logError(error: Throwable) {
    Timber.e(error)
}