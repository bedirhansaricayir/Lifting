package com.lifting.app.feature_home.domain.use_case

import com.lifting.app.common.util.Resource
import com.lifting.app.feature_home.domain.model.UserInfo
import com.lifting.app.feature_home.domain.repository.FirebaseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetUserInfoUseCase @Inject constructor(
    private val firebaseRepository: FirebaseRepository
) {
    suspend operator fun invoke(userId: String): Flow<Resource<UserInfo>> {
        return flow {
            emit(Resource.Loading)
            val data = firebaseRepository.getUserInfo(userId)
            emit(Resource.Success(data))
        }.catch { throwable ->
            emit(Resource.Error(throwable.message))
        }
    }
}