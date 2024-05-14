package com.lifting.app.core.network.api

import com.lifting.app.core.common.response.Response
import com.lifting.app.core.network.BuildConfig
import com.lifting.app.core.network.model.request.GetRecordsRequestModel
import com.lifting.app.core.network.model.request.InsertRecordRequestModel
import com.lifting.app.core.network.model.request.SignInRequestModel
import com.lifting.app.core.network.model.request.SignUpRequestModel
import com.lifting.app.core.network.model.request.UpdateRecordRequestModel
import com.lifting.app.core.network.model.request.UserRequestModel
import com.lifting.app.core.network.model.response.NetworkAuthResponse
import com.lifting.app.core.network.model.response.NetworkRecordResponse
import com.lifting.app.core.network.model.response.NetworkUserInfoResponse
import com.lifting.app.core.network.model.response.NetworkUserInfoSecretResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST


internal interface LiftingApi {

    companion object {
        const val BASE_URL = BuildConfig.BASE_URL
    }

    @POST(value = "signin")
    suspend fun signIn(
        @Body signInRequestModel: SignInRequestModel
    ): Response<NetworkAuthResponse>

    @POST(value = "signup")
    suspend fun signUp(
        @Body signUpRequestModel: SignUpRequestModel
    ): Response<Unit>

    @GET(value = "authenticate")
    suspend fun getAuthenticate(): Response<Unit>

    @GET(value = "secret")
    suspend fun getSecret(): Response<NetworkUserInfoSecretResponse>

    @GET(value = "getUser")
    suspend fun getUser(
        @Body userRequestModel: UserRequestModel
    ): Response<NetworkUserInfoResponse>

    @GET(value = "getRecords")
    suspend fun getRecords(
        @Body getRecordsRequestModel: GetRecordsRequestModel
    ): Response<NetworkRecordResponse>

    @POST(value = "insertRecord")
    suspend fun insertRecord(
        @Body insertRecordRequestModel: InsertRecordRequestModel
    ): Response<Unit>

    @PATCH(value = "updateRecord")
    suspend fun updateRecord(
        @Body updateRecordRequestModel: UpdateRecordRequestModel
    ): Response<Unit>

}