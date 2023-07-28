package com.lifting.app.common.util

sealed class Resource<out T> {
    object Loading : Resource<Nothing>()

    data class Success<out T>(
        val data: T?
    ) : Resource<T>()

    data class Error(
        val e: String?
    ) : Resource<Nothing>()
}

fun <T> Resource<T>.successOr(fallback: T): T {
    return (this as? Resource.Success<T>)?.data ?: fallback
}
