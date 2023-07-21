package com.lifting.app.feature_home.data.remote

import com.lifting.app.common.constants.Constants.Companion.BASE
import com.lifting.app.common.constants.Constants.Companion.END_POINT
import com.lifting.app.feature_home.data.remote.model.AntrenmanProgramlari
import retrofit2.http.GET


interface ApiClient {

    companion object {
        const val BASE_URL = BASE
    }

    @GET(END_POINT)
    suspend fun getProgramData(): AntrenmanProgramlari

}