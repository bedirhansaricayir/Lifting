package com.lifting.app.core.common.response

sealed interface Response<out T> {
    class Success<T>(val data: T?) : Response<T>
    class Error<T>(val errorMessage: String) : Response<T>
    data object Loading : Response<Nothing>
}

fun <T> Response<T>.successOr(fallback: T): T {
    return (this as? Response.Success<T>)?.data ?: fallback
}