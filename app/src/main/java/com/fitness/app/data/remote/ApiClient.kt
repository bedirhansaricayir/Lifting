package com.fitness.app.data.remote

import com.fitness.app.core.Constants.Companion.BASE
import com.fitness.app.core.Constants.Companion.END_POINT
import retrofit2.http.GET


interface ApiClient {

    companion object {
        const val BASE_URL = BASE
    }

    @GET(END_POINT)
    suspend fun getProgramData(): AntrenmanProgramlari

}