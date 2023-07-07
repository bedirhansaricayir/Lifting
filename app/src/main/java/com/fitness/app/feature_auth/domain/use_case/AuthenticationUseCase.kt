package com.fitness.app.feature_auth.domain.use_case

import com.fitness.app.data.local.datastore.DataStoreRepository
import javax.inject.Inject

class AuthenticationUseCase @Inject constructor(
    private val repository: DataStoreRepository
) {

}