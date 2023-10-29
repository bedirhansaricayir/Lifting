package com.lifting.app.feature_home.domain.model


data class UserInfo(
    val displayName: String? = null,
    val email: String? = null,
    val photoUrl: String? = null,
    @field:JvmField
    val isPremium: Boolean? = false,
    val createdAt: String? = null
)
