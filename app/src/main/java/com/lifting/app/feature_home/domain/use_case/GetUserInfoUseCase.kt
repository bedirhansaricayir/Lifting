package com.lifting.app.feature_home.domain.use_case

import com.lifting.app.common.util.Resource
import com.lifting.app.feature_home.domain.model.UserInfo
import com.lifting.app.feature_home.domain.repository.FirebaseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserInfoUseCase @Inject constructor(
    private val firebaseRepository: FirebaseRepository
) {
    suspend operator fun invoke(): Flow<Resource<UserInfo?>> = firebaseRepository.getUserInfo()

}