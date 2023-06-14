package com.fitness.app.core.util

/**
 * Ağ isteği yapıldıktan sonra isteğin durumlarının(Başarılı,Başarısız,Yükleniyor) kontrol edildiği yardımcı sınıf
 */


sealed class Resource<out T> {
    object Loading : Resource<Nothing>()

    data class Success<out T>(
        val data: T?
    ) : Resource<T>()

    data class Error(
        val e: String?
    ) : Resource<Nothing>()
}
