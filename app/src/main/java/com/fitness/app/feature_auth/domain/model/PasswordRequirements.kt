package com.fitness.app.feature_auth.domain.model

import androidx.annotation.StringRes
import com.fitness.app.R

enum class PasswordRequirements(
    @StringRes val label: Int
) {
    CAPITAL_LETTER(R.string.password_requirement_capital),
    NUMBER(R.string.password_requirement_digit),
    EIGHT_CHARACTERS(R.string.password_requirement_characters)
}