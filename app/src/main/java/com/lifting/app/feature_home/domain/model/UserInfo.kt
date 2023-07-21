package com.lifting.app.feature_home.domain.model


data class UserInfo(
    val displayName: String,
    val email: String,
    val photoUrl: String? = null,
)
