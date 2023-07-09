package com.lifting.app.data.remote

import com.lifting.app.core.constants.Constants.Companion.BASE
import com.lifting.app.core.constants.Constants.Companion.END_POINT
import com.lifting.app.data.remote.model.AntrenmanProgramlari
import retrofit2.http.GET


interface ApiClient {

    companion object {
        const val BASE_URL = BASE
    }

    @GET(END_POINT)
    suspend fun getProgramData(): AntrenmanProgramlari

}