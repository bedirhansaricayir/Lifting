package com.lifting.app.feature_profile.domain.use_case

import com.lifting.app.common.util.Resource
import com.lifting.app.feature_home.data.repository.FirebaseRepositoryImpl
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DeleteAccountUseCase @Inject constructor(
    private val firebaseRepositoryImpl: FirebaseRepositoryImpl
) {

    suspend operator fun invoke(): Flow<Resource<Boolean>> = flow {
        emit(Resource.Loading)
        val delete =  firebaseRepositoryImpl.revokeAccess()
        emit(Resource.Success(delete))
    }.catch { throwable ->
        emit(Resource.Error(throwable.message.toString()))
    }
}