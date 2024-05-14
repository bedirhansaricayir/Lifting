package com.lifting.app.core.network.source

import com.lifting.app.core.common.response.Response
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

interface RemoteDataSource {
    suspend fun signIn(signInRequestModel: SignInRequestModel): Response<NetworkAuthResponse>

    suspend fun signUp(signUpRequestModel: SignUpRequestModel): Response<Unit>

    suspend fun getAuthenticate(): Response<Unit>

    suspend fun getSecret(): Response<NetworkUserInfoSecretResponse>

    suspend fun getUser(userRequestModel: UserRequestModel): Response<NetworkUserInfoResponse>

    suspend fun getRecords(getRecordsRequestModel: GetRecordsRequestModel): Response<NetworkRecordResponse>

    suspend fun insertRecord(insertRecordRequestModel: InsertRecordRequestModel): Response<Unit>

    suspend fun updateRecord(updateRecordRequestModel: UpdateRecordRequestModel): Response<Unit>
}