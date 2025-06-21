package com.lifting.app.core.model

import java.time.LocalDateTime

/**
 * Created by bedirhansaricayir on 28.05.2025
 */

data class Plate(
    val id: String,
    val weight: Double? = null,
    val forWeightUnit: String? = null,
    val isActive: Boolean? = null,
    val color: String? = null,
    val colorValueType: String? = null,
    val height: Float? = null,
    val width: Float? = null,
    val createdAt: LocalDateTime? = null,
    val updatedAt: LocalDateTime? = null,
)