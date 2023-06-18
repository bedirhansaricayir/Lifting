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

    @GET("bedirhansaricayir/51bed929bca74d888f3dd5e20a11f80e/raw/5f5050607cfb6c4f20694758bada1ad41634bd7d/program_data.json")
    suspend fun getProgramData(): AntrenmanProgramlari

}