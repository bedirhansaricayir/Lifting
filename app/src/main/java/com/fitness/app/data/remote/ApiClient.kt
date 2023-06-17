package com.fitness.app.data.remote

import retrofit2.http.GET

/**
 * Ağ isteklerinin yapılacağı endpoint(uç nokta)'in belirlendiği sınıf
 */
interface ApiClient {

    companion object {
        const val BASE_URL = "https://gist.github.com/"
    }

    //Get isteği ile bu adresten bir veri alma isteğinde bulunuyoruz.

    @GET("bedirhansaricayir/51bed929bca74d888f3dd5e20a11f80e/raw/f4d3b0e70bb0e539b9547a476703b21cc0893a0c/program_data.json")
    suspend fun getProgramData(): AntrenmanProgramlari

}