package com.fitness.app.data.remote

import com.fitness.app.core.constants.Constants.Companion.BASE
import com.fitness.app.core.constants.Constants.Companion.END_POINT
import com.fitness.app.data.remote.model.AntrenmanProgramlari
import retrofit2.http.GET


interface ApiClient {

    companion object {
        const val BASE_URL = BASE
    }

    @GET(END_POINT)
    suspend fun getProgramData(): AntrenmanProgramlari

}