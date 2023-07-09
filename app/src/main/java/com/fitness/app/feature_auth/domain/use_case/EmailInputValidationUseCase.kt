package com.fitness.app.feature_auth.domain.use_case

import android.util.Patterns
import com.fitness.app.data.local.datastore.DataStoreRepository
import com.fitness.app.feature_auth.domain.model.InputValidationType
import javax.inject.Inject

class EmailInputValidationUseCase @Inject constructor(
    private val repository: DataStoreRepository
){
    operator fun invoke(
        email:String,
    ): InputValidationType {
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return InputValidationType.NoEmail
        }
        return InputValidationType.Valid
    }
}