package com.lifting.app.core.network

import com.lifting.app.core.common.response.Response
import com.lifting.app.core.network.api.LiftingApi
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
import com.lifting.app.core.network.source.RemoteDataSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class LiftingRemoteDataSource @Inject constructor(
    private val api: LiftingApi,
) : RemoteDataSource {
    override suspend fun signIn(signInRequestModel: SignInRequestModel): Response<NetworkAuthResponse> =
        api.signIn(signInRequestModel)

    override suspend fun signUp(signUpRequestModel: SignUpRequestModel): Response<Unit> =
        api.signUp(signUpRequestModel)

    override suspend fun getAuthenticate(): Response<Unit> =
        api.getAuthenticate()

    override suspend fun getSecret(): Response<NetworkUserInfoSecretResponse> =
        api.getSecret()

    override suspend fun getUser(userRequestModel: UserRequestModel): Response<NetworkUserInfoResponse> =
        api.getUser(userRequestModel)

    override suspend fun getRecords(getRecordsRequestModel: GetRecordsRequestModel): Response<NetworkRecordResponse> =
        api.getRecords(getRecordsRequestModel)

    override suspend fun insertRecord(insertRecordRequestModel: InsertRecordRequestModel): Response<Unit> =
        api.insertRecord(insertRecordRequestModel)

    override suspend fun updateRecord(updateRecordRequestModel: UpdateRecordRequestModel): Response<Unit> =
        api.updateRecord(updateRecordRequestModel)
}