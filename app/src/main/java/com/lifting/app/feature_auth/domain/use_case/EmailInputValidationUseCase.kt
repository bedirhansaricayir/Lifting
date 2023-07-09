package com.lifting.app.feature_auth.domain.use_case

import android.util.Patterns
import com.lifting.app.feature_auth.domain.model.InputValidationType

class EmailInputValidationUseCase {
    operator fun invoke(
        email:String,
    ): InputValidationType {
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return InputValidationType.NoEmail
        }
        return InputValidationType.Valid
    }
}