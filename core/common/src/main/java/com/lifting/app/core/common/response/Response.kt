package com.lifting.app.core.common.response

import kotlin.coroutines.cancellation.CancellationException

sealed interface Response<out T> {
    class Success<T>(val data: T?) : Response<T>
    class Error<T>(val errorMessage: String) : Response<T>
    data object Loading : Response<Nothing>
}

fun <T> Response<T>.successOr(fallback: T): T {
    return (this as? Response.Success<T>)?.data ?: fallback
}
suspend inline fun <R> runSuspendCatching(block: suspend () -> R): Result<R> {
    return runCatching {
        block()
    }.onFailure {
        if (it is CancellationException) throw it
    }
}