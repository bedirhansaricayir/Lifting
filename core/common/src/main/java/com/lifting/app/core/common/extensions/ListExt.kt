package com.lifting.app.core.common.extensions

import kotlinx.coroutines.delay

/**
 * Created by bedirhansaricayir on 09.02.2025
 */

fun <T> List<T>.toArrayList(): ArrayList<T> = ArrayList(this)

suspend fun <T> ListIterator<T>.doWhenHasNextOrPrevious(
    delayMills: Long = 3000,
    doWork: suspend (T) -> Unit
) {
    while (hasNext() || hasPrevious()) {
        while (hasNext()) {
            delay(delayMills)
            doWork(next())
        }
        while (hasPrevious()) {
            delay(delayMills)
            doWork(previous())
        }
    }
}

fun <T> List<T>.head(): T {
    if (this.isNotEmpty()) {
        return this.first()
    }

    throw IllegalArgumentException("List is empty")
}

fun <T> List<T>.tail(): List<T> {
    if (this.isNotEmpty()) {
        return this.drop(1)
    }

    throw IllegalArgumentException("List is empty")
}
