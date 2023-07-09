package com.lifting.app.core.util

sealed class Resource<out T> {
    object Loading : Resource<Nothing>()

    data class Success<out T>(
        val data: T?
    ) : Resource<T>()

    data class Error(
        val e: String?
    ) : Resource<Nothing>()
}
